package assemblyline.common.machine.command;

import assemblyline.common.machine.command.Command;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;

public class CommandPlace
extends Command {
    int PLACE_TIME = 30;

    @Override
    public void onTaskStart() {
        super.onTaskStart();
    }

    @Override
    protected boolean doTask() {
        super.doTask();
        Vector3 serachPosition = this.tileEntity.getHandPosition();
        Block block = serachPosition.getBlock((IBlockAccess)this.world);
        if (block == null && this.ticks >= this.PLACE_TIME) {
            for (Entity entity : this.tileEntity.getGrabbedEntities()) {
                ItemStack itemStack;
                if (!(entity instanceof EntityItem) || (itemStack = ((EntityItem)entity).getEntityItem()) == null) continue;
                if (itemStack.getItem() instanceof ItemBlock) {
                    ((ItemBlock)itemStack.getItem()).placeBlockAt(itemStack, null, this.world, serachPosition.intX(), serachPosition.intY(), serachPosition.intZ(), 0, 0.5f, 0.5f, 0.5f, itemStack.getItemDamage());
                    this.tileEntity.dropEntity(entity);
                    return false;
                }
                if (!(itemStack.getItem() instanceof IPlantable)) continue;
                IPlantable plantable = (IPlantable)itemStack.getItem();
                Block blockBelow = Vector3.add(serachPosition, new Vector3(0.0, -1.0, 0.0)).getBlock((IBlockAccess)this.world);
                if (blockBelow == null || !blockBelow.canSustainPlant(this.world, serachPosition.intX(), serachPosition.intY(), serachPosition.intZ(), ForgeDirection.UP, plantable)) continue;
                Block plantBlock = plantable.getPlant(this.world, serachPosition.intX(), serachPosition.intY(), serachPosition.intZ());
                int blockMetadata = plantable.getPlantMetadata(this.world, serachPosition.intX(), serachPosition.intY(), serachPosition.intZ());
                if (!this.world.setBlock(serachPosition.intX(), serachPosition.intY(), serachPosition.intZ(), plantBlock, blockMetadata, 3) || this.world.getBlock(serachPosition.intX(), serachPosition.intY(), serachPosition.intZ()) != plantBlock) continue;
                plantBlock.onBlockPlacedBy(this.world, serachPosition.intX(), serachPosition.intY(), serachPosition.intZ(), null, itemStack);
                plantBlock.onPostBlockPlaced(this.world, serachPosition.intX(), serachPosition.intY(), serachPosition.intZ(), blockMetadata);
                this.tileEntity.dropEntity(entity);
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "PLACE";
    }
}

