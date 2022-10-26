package assemblyline.common.machine;

import assemblyline.common.machine.imprinter.TileEntityFilterable;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;

public class TileEntityRejector
extends TileEntityFilterable {
    public boolean firePiston = false;

    @Override
    protected int getMaxTransferRange() {
        return 20;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticks % 5L == 0L && !this.isDisabled()) {
            int metadata = this.getBlockMetadata();
            this.firePiston = false;
            Vector3 searchPosition = new Vector3(this);
            searchPosition.modifyPositionFromSide(this.getDirection());
            TileEntity tileEntity = searchPosition.getTileEntity((IBlockAccess)this.worldObj);
            try {
                boolean flag = false;
                if (this.isRunning()) {
                    AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox((double)searchPosition.x, (double)searchPosition.y, (double)searchPosition.z, (double)(searchPosition.x + 1.0), (double)(searchPosition.y + 1.0), (double)(searchPosition.z + 1.0));
                    List<Entity> entitiesInFront = this.worldObj.getEntitiesWithinAABB(Entity.class, bounds);
                    for (Entity entity : entitiesInFront) {
                        if (!this.canEntityBeThrow(entity)) continue;
                        this.throwItem(this.getDirection(), entity);
                        flag = true;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void throwItem(ForgeDirection side, Entity entity) {
        this.firePiston = true;
        entity.motionX = (double)side.offsetX * 0.2;
        entity.motionY += 0.10000000298023223;
        entity.motionZ = (double)side.offsetZ * 0.2;
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public boolean canEntityBeThrow(Entity entity) {
        if (entity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem)entity;
            ItemStack itemStack = entityItem.getEntityItem();
            return this.isFiltering(itemStack);
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.firePiston = nbt.getBoolean("piston");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("piston", this.firePiston);
    }

    @Override
    public boolean canConnect(ForgeDirection dir) {
        return dir != this.getDirection();
    }
}

