package assemblyline.common.machine.command;

import assemblyline.common.machine.command.Command;
import assemblyline.common.machine.command.CommandRotateTo;

public class CommandReturn
extends Command {
    public static final float IDLE_ROTATION_PITCH = 0.0f;
    public static final float IDLE_ROTATION_YAW = 0.0f;
    private CommandRotateTo rotateToCommand;

    @Override
    public void onTaskStart() {
        this.rotateToCommand = (CommandRotateTo)this.commandManager.getNewCommand(this.tileEntity, CommandRotateTo.class, new String[]{"0", "0"});
        this.rotateToCommand.onTaskStart();
    }

    @Override
    protected boolean doTask() {
        if (this.rotateToCommand == null) {
            this.onTaskStart();
        }
        return this.rotateToCommand.doTask();
    }

    @Override
    public void onTaskEnd() {
        this.rotateToCommand.onTaskEnd();
    }

    @Override
    public String toString() {
        return "RETURN";
    }
}

