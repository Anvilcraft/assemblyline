package assemblyline.common.machine.command;

import assemblyline.common.machine.command.Command;

public class CommandDrop extends Command {
    @Override
    protected boolean doTask() {
        super.doTask();
        this.tileEntity.dropAll();
        this.world.playSound(
            (double) this.tileEntity.xCoord,
            (double) this.tileEntity.yCoord,
            (double) this.tileEntity.zCoord,
            "random.pop",
            0.2f,
            ((this.tileEntity.getWorldObj().rand.nextFloat()
              - this.tileEntity.getWorldObj().rand.nextFloat())
                 * 0.7f
             + 1.0f)
                * 1.0f,
            true
        );
        return false;
    }

    @Override
    public String toString() {
        return "DROP";
    }
}
