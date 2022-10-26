package assemblyline.common.machine.command;

import assemblyline.api.IArmbotUseable;
import assemblyline.common.machine.command.Command;
import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class CommandUse
extends Command {
    private int times;
    private int curTimes;

    @Override
    public void onTaskStart() {
        this.times = 0;
        this.curTimes = 0;
        if (this.getArgs().length > 0) {
            this.times = this.getIntArg(0);
        }
        if (this.times <= 0) {
            this.times = 1;
        }
    }

    @Override
    protected boolean doTask() {
        Block block = this.world.getBlock(this.tileEntity.getHandPosition().intX(), this.tileEntity.getHandPosition().intY(), this.tileEntity.getHandPosition().intZ());
        TileEntity targetTile = this.tileEntity.getHandPosition().getTileEntity((IBlockAccess)this.world);
        if (targetTile != null) {
            if (targetTile instanceof IArmbotUseable) {
                ((IArmbotUseable)targetTile).onUse(this.tileEntity, this.getArgs());
            } else if (targetTile instanceof ISidedInventory && this.tileEntity.getGrabbedEntities().size() > 0) {
                // empty if block
            }
        } else if (block != null) {
            try {
                boolean f = block.onBlockActivated(this.world, this.tileEntity.getHandPosition().intX(), this.tileEntity.getHandPosition().intY(), this.tileEntity.getHandPosition().intZ(), null, 0, 0.0f, 0.0f, 0.0f);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ++this.curTimes;
        return this.curTimes < this.times;
    }

    @Override
    public String toString() {
        return "USE " + Integer.toString(this.times);
    }

    @Override
    public void readFromNBT(NBTTagCompound taskCompound) {
        super.readFromNBT(taskCompound);
        this.times = taskCompound.getInteger("useTimes");
        this.curTimes = taskCompound.getInteger("useCurTimes");
    }

    @Override
    public void writeToNBT(NBTTagCompound taskCompound) {
        super.writeToNBT(taskCompound);
        taskCompound.setInteger("useTimes", this.times);
        taskCompound.setInteger("useCurTimes", this.curTimes);
    }
}

