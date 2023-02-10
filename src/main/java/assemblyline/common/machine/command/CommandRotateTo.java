package assemblyline.common.machine.command;

import assemblyline.common.machine.command.Command;
import net.minecraft.nbt.NBTTagCompound;

public class CommandRotateTo extends Command {
    float targetRotationYaw = 0.0f;
    float targetRotationPitch = 0.0f;
    int totalTicks = 0;

    @Override
    public void onTaskStart() {
        super.onTaskStart();
        this.ticks = 0;
        this.totalTicks = 0;
        this.targetRotationYaw
            = this.getArg(0) != null ? this.getFloatArg(0).floatValue() : 0.0f;
        this.targetRotationPitch
            = this.getArg(1) != null ? this.getFloatArg(1).floatValue() : 0.0f;
        while (this.targetRotationYaw < 0.0f) {
            this.targetRotationYaw += 360.0f;
        }
        while (this.targetRotationYaw > 360.0f) {
            this.targetRotationYaw -= 360.0f;
        }
        while (this.targetRotationPitch < -60.0f) {
            this.targetRotationPitch += 60.0f;
        }
        while (this.targetRotationPitch > 60.0f) {
            this.targetRotationPitch -= 60.0f;
        }
        float f = Math.abs(this.targetRotationYaw - this.tileEntity.renderYaw);
        this.tileEntity.getClass();
        int totalTicksYaw = (int) (f / 1.3f);
        float f2 = Math.abs(this.targetRotationPitch - this.tileEntity.renderPitch);
        this.tileEntity.getClass();
        int totalTicksPitch = (int) (f2 / 1.3f);
        this.totalTicks = Math.max(totalTicksYaw, totalTicksPitch);
    }

    @Override
    protected boolean doTask() {
        super.doTask();
        this.tileEntity.rotationYaw = this.targetRotationYaw;
        this.tileEntity.rotationPitch = this.targetRotationPitch;
        if (Math.abs(this.tileEntity.renderPitch - this.tileEntity.rotationPitch)
            > 0.001f) {
            return true;
        }
        return Math.abs(this.tileEntity.renderYaw - this.tileEntity.rotationYaw) > 0.001f;
    }

    @Override
    public String toString() {
        return "ROTATETO " + Float.toString(this.targetRotationYaw) + " "
            + Float.toString(this.targetRotationPitch);
    }

    @Override
    public void readFromNBT(NBTTagCompound taskCompound) {
        super.readFromNBT(taskCompound);
        this.targetRotationPitch = taskCompound.getFloat("rotPitch");
        this.targetRotationYaw = taskCompound.getFloat("rotYaw");
    }

    @Override
    public void writeToNBT(NBTTagCompound taskCompound) {
        super.writeToNBT(taskCompound);
        taskCompound.setFloat("rotPitch", this.targetRotationPitch);
        taskCompound.setFloat("rotYaw", this.targetRotationYaw);
    }
}
