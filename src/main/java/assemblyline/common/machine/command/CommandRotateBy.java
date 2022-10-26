package assemblyline.common.machine.command;

import assemblyline.common.machine.command.Command;
import net.minecraft.nbt.NBTTagCompound;

public class CommandRotateBy
extends Command {
    float targetRotationYaw = 0.0f;
    float targetRotationPitch = 0.0f;
    float deltaPitch = 0.0f;
    float deltaYaw = 90.0f;
    float totalTicks = 0.0f;

    @Override
    public void onTaskStart() {
        super.onTaskStart();
        this.ticks = 0;
        if (this.getArg(0) != null) {
            this.targetRotationYaw = this.tileEntity.rotationYaw + this.getFloatArg(0).floatValue();
            this.deltaYaw = this.getFloatArg(0).floatValue();
        } else {
            this.targetRotationYaw = this.tileEntity.rotationYaw + 90.0f;
        }
        if (this.getArg(1) != null) {
            this.targetRotationPitch = this.tileEntity.rotationPitch + this.getFloatArg(1).floatValue();
            this.deltaPitch = this.getFloatArg(1).floatValue();
        } else {
            this.targetRotationPitch = this.tileEntity.rotationPitch;
        }
        while (this.targetRotationYaw < 0.0f) {
            this.targetRotationYaw += 360.0f;
        }
        while (this.targetRotationYaw > 360.0f) {
            this.targetRotationYaw -= 360.0f;
        }
        while (this.targetRotationPitch < 0.0f) {
            this.targetRotationPitch += 60.0f;
        }
        while (this.targetRotationPitch > 60.0f) {
            this.targetRotationPitch -= 60.0f;
        }
        float f = Math.abs(this.targetRotationYaw - this.tileEntity.rotationYaw);
        this.tileEntity.getClass();
        float totalTicksYaw = f / 1.3f;
        float f2 = Math.abs(this.targetRotationPitch - this.tileEntity.rotationPitch);
        this.tileEntity.getClass();
        float totalTicksPitch = f2 / 1.3f;
        this.totalTicks = Math.max(totalTicksYaw, totalTicksPitch);
    }

    @Override
    protected boolean doTask() {
        super.doTask();
        if (Math.abs(this.tileEntity.rotationYaw - this.targetRotationYaw) > 0.001f) {
            this.tileEntity.rotationYaw = this.targetRotationYaw;
        }
        if (Math.abs(this.tileEntity.rotationPitch - this.targetRotationPitch) > 0.001f) {
            this.tileEntity.rotationPitch = this.targetRotationPitch;
        }
        if (Math.abs(this.tileEntity.renderPitch - this.tileEntity.rotationPitch) > 0.001f) {
            return true;
        }
        return Math.abs(this.tileEntity.renderYaw - this.tileEntity.rotationYaw) > 0.001f;
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

    @Override
    public String toString() {
        return "ROTATE " + Float.toString(this.deltaYaw) + " " + Float.toString(this.deltaPitch);
    }
}

