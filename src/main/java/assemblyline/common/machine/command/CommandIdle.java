package assemblyline.common.machine.command;

import assemblyline.common.machine.command.Command;
import net.minecraft.nbt.NBTTagCompound;

public class CommandIdle extends Command {
    public int idleTime = 80;
    private int totalIdleTime = 80;

    @Override
    public void onTaskStart() {
        super.onTaskStart();
        if (this.getIntArg(0) > 0) {
            this.totalIdleTime = this.idleTime = this.getIntArg(0);
        }
    }

    @Override
    protected boolean doTask() {
        if (this.idleTime > 0) {
            --this.idleTime;
            return true;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound taskCompound) {
        super.readFromNBT(taskCompound);
        this.idleTime = taskCompound.getInteger("idleTime");
        this.totalIdleTime = taskCompound.getInteger("idleTotal");
    }

    @Override
    public void writeToNBT(NBTTagCompound taskCompound) {
        super.writeToNBT(taskCompound);
        taskCompound.setInteger("idleTime", this.idleTime);
        taskCompound.setInteger("idleTotal", this.totalIdleTime);
    }

    @Override
    public String toString() {
        return "IDLE " + Integer.toString(this.totalIdleTime);
    }
}
