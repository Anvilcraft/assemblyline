package assemblyline.common.machine.command;

import assemblyline.common.machine.command.CommandBreak;
import assemblyline.common.machine.command.CommandRotateTo;

public class CommandHarvest extends CommandBreak {
    private CommandRotateTo rotateToCommand;

    @Override
    public void onTaskStart() {
        this.keep = true;
    }

    @Override
    public String toString() {
        return "HARVEST";
    }
}
