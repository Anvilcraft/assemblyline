package assemblyline.common.machine.command;

import assemblyline.common.machine.armbot.TileEntityArmbot;
import assemblyline.common.machine.command.Command;
import assemblyline.common.machine.command.CommandRepeat;
import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class CommandManager {
    private final List tasks = new ArrayList();
    private int ticks = 0;
    private int currentTask = 0;
    private int lastTask = -1;

    public void onUpdate() {
        try {
            if (this.tasks.size() > 0) {
                if (this.currentTask < this.tasks.size()) {
                    if (this.currentTask < 0) {
                        this.currentTask = 0;
                        this.lastTask = -1;
                    }
                    Command task = (Command)this.tasks.get(this.currentTask);
                    if (this.currentTask != this.lastTask) {
                        this.lastTask = this.currentTask;
                        task.onTaskStart();
                    }
                    if (!task.doTask()) {
                        int tempCurrentTask = this.currentTask++;
                        task.onTaskEnd();
                        if (!(task instanceof CommandRepeat)) {
                            this.tasks.set(tempCurrentTask, this.getNewCommand(task.tileEntity, task.getClass(), task.getArgs()));
                        }
                    }
                } else {
                    this.clear();
                }
            }
        }
        catch (Exception e) {
            FMLLog.severe((String)"Failed to execute task in Assembly Line.", (Object[])new Object[0]);
            e.printStackTrace();
        }
        ++this.ticks;
    }

    public Command getNewCommand(TileEntityArmbot tileEntity, Class commandClass, String[] parameters) {
        try {
            Command newCommand = (Command)commandClass.newInstance();
            newCommand.world = tileEntity.getWorldObj();
            newCommand.tileEntity = tileEntity;
            newCommand.commandManager = this;
            newCommand.setParameters(parameters);
            return newCommand;
        }
        catch (Exception e) {
            FMLLog.severe((String)"Failed to add command", (Object[])new Object[0]);
            e.printStackTrace();
            return null;
        }
    }

    public void addCommand(TileEntityArmbot tileEntity, Class commandClass, String[] parameters) {
        Command newCommand = this.getNewCommand(tileEntity, commandClass, parameters);
        if (newCommand != null) {
            this.tasks.add(newCommand);
        }
    }

    public void addCommand(TileEntityArmbot tileEntity, Class task) {
        this.addCommand(tileEntity, task, new String[0]);
    }

    public boolean hasTasks() {
        return this.tasks.size() > 0;
    }

    public List getCommands() {
        return this.tasks;
    }

    public void clear() {
        this.tasks.clear();
        this.currentTask = 0;
        this.lastTask = -1;
        this.ticks = 0;
    }

    public void setCurrentTask(int i) {
        this.currentTask = Math.min(i, this.tasks.size());
    }

    public int getCurrentTask() {
        return this.currentTask;
    }

    public void readFromNBT(TileEntityArmbot tileEntity, NBTTagCompound nbt) {
        this.currentTask = nbt.getInteger("curTasks");
        this.lastTask = nbt.getInteger("lastTask");
        this.ticks = nbt.getInteger("ticks");
        if (nbt.getInteger("numTasks") > 0) {
            NBTTagList taskList = nbt.getTagList("commands", 10);
            for (int i = 0; i < taskList.tagCount(); ++i) {
                NBTTagCompound cmdTag = (NBTTagCompound)taskList.getCompoundTagAt(i);
                try {
                    Class<?> cmdClass = Class.forName(cmdTag.getString("commandClass"));
                    ArrayList<String> pars = new ArrayList<String>();
                    if (cmdTag.getInteger("numParameters") > 0) {
                        NBTTagList parameters = cmdTag.getTagList("parameters", 8);
                        for (int ii = 0; ii < parameters.tagCount(); ++ii) {
                            pars.add(parameters.getStringTagAt((int)ii));
                        }
                    }
                    Command cmd = this.getNewCommand(tileEntity, cmdClass, pars.toArray(new String[0]));
                    cmd.readFromNBT((NBTTagCompound)cmdTag.getTag("customData"));
                    continue;
                }
                catch (ClassNotFoundException e) {
                    System.out.println("Error loading CommandManger: ");
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("numTasks", this.tasks.size());
        if (this.tasks.size() > 0) {
            NBTTagList taskList = new NBTTagList();
            for (int i = 0; i < this.tasks.size(); ++i) {
                NBTTagCompound taskCompound = new NBTTagCompound();
                String cmdName = ((Command)this.tasks.get(i)).getClass().getName();
                if (cmdName != null && !cmdName.isEmpty()) {
                    taskCompound.setString("commandClass", cmdName);
                }
                if (((Command)this.tasks.get(i)).getArgs().length > 0) {
                    NBTTagList parameters = new NBTTagList();
                    for (String par : ((Command)this.tasks.get(i)).getArgs()) {
                        if (par == null || par.isEmpty()) continue;
                        parameters.appendTag((NBTBase)new NBTTagString(par));
                    }
                    taskCompound.setTag("parameters", (NBTBase)parameters);
                }
                taskCompound.setInteger("numParameters", ((Command)this.tasks.get(i)).getArgs().length);
                NBTTagCompound customData = new NBTTagCompound();
                ((Command)this.tasks.get(i)).writeToNBT(customData);
                taskCompound.setTag("customData", customData);
                taskList.appendTag((NBTBase)taskCompound);
            }
            nbt.setTag("commands", (NBTBase)taskList);
        }
        nbt.setInteger("curTask", this.currentTask);
        nbt.setInteger("lastTask", this.lastTask);
        nbt.setInteger("ticks", this.ticks);
    }
}

