package assemblyline.common.machine.command;

import java.util.ArrayList;

import assemblyline.common.machine.command.Command;
import dark.library.helpers.ItemFindingHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;

public class CommandPowerTo extends Command {
    private int duration;
    private int ticksRan;

    @Override
    public void onTaskStart() {
        this.duration = 0;
        this.ticksRan = 0;
        if (this.getArgs().length > 0) {
            this.duration = this.getIntArg(0);
        }
        if (this.duration <= 30) {
            this.duration = 30;
        }
    }

    @Override
    protected boolean doTask() {
        super.doTask();
        if (this.tileEntity.isProvidingPower && this.ticksRan >= this.duration) {
            this.powerBlock(false);
            return false;
        }
        if (this.tileEntity.isProvidingPower) {
            Vector3 loc = this.tileEntity.getHandPosition();
            this.world.spawnParticle("smoke", loc.x, loc.y, loc.z, 0.0, 0.0, 0.0);
            this.world.spawnParticle("flame", loc.x, loc.y, loc.z, 0.0, 0.0, 0.0);
        }
        Block block = this.world.getBlock(
            this.tileEntity.getHandPosition().intX(),
            this.tileEntity.getHandPosition().intY(),
            this.tileEntity.getHandPosition().intZ()
        );
        TileEntity targetTile
            = this.tileEntity.getHandPosition().getTileEntity((IBlockAccess) this.world);
        if (this.tileEntity.getGrabbedItems().size() > 0) {
            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
            stacks.add(new ItemStack(Blocks.redstone_torch, 1, 0));
            stacks.add(new ItemStack(Blocks.unlit_redstone_torch, 1, 0));
            if (ItemFindingHelper.filterItems(this.tileEntity.getGrabbedItems(), stacks)
                    .size()
                > 0) {
                this.powerBlock(true);
            }
        }
        ++this.ticksRan;
        return true;
    }

    public void powerBlock(boolean on) {
        this.tileEntity.isProvidingPower = on;
        Block block = this.tileEntity.getWorldObj().getBlock(
            this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord
        );
        for (int i = 2; i < 6; ++i) {
            ForgeDirection dir = ForgeDirection.getOrientation((int) i);
            this.world.notifyBlockOfNeighborChange(
                this.tileEntity.xCoord + dir.offsetX,
                this.tileEntity.yCoord + dir.offsetY,
                this.tileEntity.zCoord + dir.offsetZ,
                block
            );
        }
    }

    @Override
    public String toString() {
        return "POWERTO " + Integer.toString(this.duration);
    }

    @Override
    public void readFromNBT(NBTTagCompound taskCompound) {
        super.readFromNBT(taskCompound);
        this.duration = taskCompound.getInteger("useTimes");
        this.ticksRan = taskCompound.getInteger("useCurTimes");
    }

    @Override
    public void writeToNBT(NBTTagCompound taskCompound) {
        super.writeToNBT(taskCompound);
        taskCompound.setInteger("useTimes", this.duration);
        taskCompound.setInteger("useCurTimes", this.ticksRan);
    }
}
