package assemblyline.common.machine.command;

import assemblyline.common.machine.armbot.TileEntityArmbot;
import assemblyline.common.machine.command.CommandBreak;
import assemblyline.common.machine.command.CommandDrop;
import assemblyline.common.machine.command.CommandFire;
import assemblyline.common.machine.command.CommandGrab;
import assemblyline.common.machine.command.CommandHarvest;
import assemblyline.common.machine.command.CommandIdle;
import assemblyline.common.machine.command.CommandManager;
import assemblyline.common.machine.command.CommandPlace;
import assemblyline.common.machine.command.CommandPowerTo;
import assemblyline.common.machine.command.CommandRepeat;
import assemblyline.common.machine.command.CommandReturn;
import assemblyline.common.machine.command.CommandRotateBy;
import assemblyline.common.machine.command.CommandRotateTo;
import assemblyline.common.machine.command.CommandUse;
import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class Command {
    private static final HashMap COMMANDS = new HashMap();
    private static final HashMap REVERSE_LOOKUP = new HashMap();
    protected int ticks = 0;
    public World world;
    public TileEntityArmbot tileEntity;
    public CommandManager commandManager;
    private String[] parameters;

    public static void registerCommand(String command, Class commandClass) {
        COMMANDS.put(command, commandClass);
        REVERSE_LOOKUP.put(commandClass, command);
    }

    public static Class getCommand(String command) {
        return (Class)COMMANDS.get(command.toLowerCase());
    }

    public static String getCommandName(Class command) {
        return (String)REVERSE_LOOKUP.get(command);
    }

    protected boolean doTask() {
        ++this.ticks;
        return false;
    }

    public void onTaskStart() {
    }

    public void onTaskEnd() {
    }

    public int getTickInterval() {
        return 1;
    }

    public void setParameters(String[] strings) {
        this.parameters = strings;
    }

    public String[] getArgs() {
        return this.parameters;
    }

    protected String getArg(int i) {
        if (i >= 0 && i < this.parameters.length) {
            return this.parameters[i];
        }
        return null;
    }

    protected int getIntArg(int i) {
        if (this.getArg(i) != null) {
            try {
                return Integer.parseInt(this.getArg(i));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return 0;
    }

    protected Double getDoubleArg(int i) {
        if (this.getArg(i) != null) {
            try {
                return Double.parseDouble(this.getArg(i));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return 0.0;
    }

    protected Float getFloatArg(int i) {
        if (this.getArg(i) != null) {
            try {
                return Float.valueOf(Float.parseFloat(this.getArg(i)));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return Float.valueOf(0.0f);
    }

    public void readFromNBT(NBTTagCompound taskCompound) {
        this.ticks = taskCompound.getInteger("ticks");
    }

    public void writeToNBT(NBTTagCompound taskCompound) {
        taskCompound.setInteger("ticks", this.ticks);
    }

    public String toString() {
        return "COMMAND";
    }

    static {
        Command.registerCommand("idle", CommandIdle.class);
        Command.registerCommand("grab", CommandGrab.class);
        Command.registerCommand("drop", CommandDrop.class);
        Command.registerCommand("rotate", CommandRotateBy.class);
        Command.registerCommand("rotateto", CommandRotateTo.class);
        Command.registerCommand("return", CommandReturn.class);
        Command.registerCommand("repeat", CommandRepeat.class);
        Command.registerCommand("use", CommandUse.class);
        Command.registerCommand("powerto", CommandPowerTo.class);
        Command.registerCommand("fire", CommandFire.class);
        Command.registerCommand("break", CommandBreak.class);
        Command.registerCommand("place", CommandPlace.class);
        Command.registerCommand("harvest", CommandHarvest.class);
    }
}

