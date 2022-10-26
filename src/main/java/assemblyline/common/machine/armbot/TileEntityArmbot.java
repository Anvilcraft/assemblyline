package assemblyline.common.machine.armbot;

import assemblyline.api.IArmbot;
import assemblyline.common.AssemblyLine;
import assemblyline.common.machine.TileEntityAssemblyNetwork;
import assemblyline.common.machine.command.Command;
import assemblyline.common.machine.command.CommandDrop;
import assemblyline.common.machine.command.CommandFire;
import assemblyline.common.machine.command.CommandGrab;
import assemblyline.common.machine.command.CommandManager;
import assemblyline.common.machine.command.CommandReturn;
import assemblyline.common.machine.command.CommandRotateBy;
import assemblyline.common.machine.command.CommandRotateTo;
import assemblyline.common.machine.command.CommandUse;
import assemblyline.common.machine.encoder.ItemDisk;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dark.library.helpers.ItemFindingHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.block.IElectricityStorage;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.TranslationHelper;
import universalelectricity.prefab.multiblock.IMultiBlock;

public class TileEntityArmbot
extends TileEntityAssemblyNetwork
implements IMultiBlock,
IInventory,
IElectricityStorage,
IArmbot,
IPeripheral {
    private final CommandManager commandManager = new CommandManager();
    private static final int PACKET_COMMANDS = 128;
    protected ItemStack disk = null;
    public final double WATT_REQUEST = 20.0;
    public double wattsReceived = 0.0;
    private int playerUsing = 0;
    private int computersAttached = 0;
    private List connectedComputers = new ArrayList();
    public float rotationPitch = 0.0f;
    public float rotationYaw = 0.0f;
    public float renderPitch = 0.0f;
    public float renderYaw = 0.0f;
    private int ticksSincePower = 0;
    public final float ROTATION_SPEED = 1.3f;
    private String displayText = "";
    public boolean isProvidingPower = false;
    private final List<Entity> grabbedEntities = new ArrayList<>();
    private final List<ItemStack> grabbedItems = new ArrayList();
    public EntityItem renderEntityItem = null;

    @Override
    public void onUpdate() {
        Vector3 handPosition = this.getHandPosition();
        for (Entity entity : this.grabbedEntities) {
            if (entity == null) continue;
            entity.setPosition(handPosition.x, handPosition.y, handPosition.z);
            entity.motionX = 0.0;
            entity.motionY = 0.0;
            entity.motionZ = 0.0;
            if (!(entity instanceof EntityItem)) continue;
            ((EntityItem)entity).delayBeforeCanPickup = 20;
            ((EntityItem)entity).age = 0;
        }
        if (this.isRunning()) {
            if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && this.disk == null && this.computersAttached == 0) {
                this.commandManager.clear();
                if (this.grabbedEntities.size() > 0 || this.grabbedItems.size() > 0) {
                    this.addCommand(CommandDrop.class);
                } else if (!this.commandManager.hasTasks() && ((double)Math.abs(this.rotationYaw - 0.0f) > 0.01 || (double)Math.abs(this.rotationPitch - 0.0f) > 0.01)) {
                    this.addCommand(CommandReturn.class);
                }
                this.commandManager.setCurrentTask(0);
            }
            if (!this.worldObj.isRemote) {
                this.commandManager.onUpdate();
            }
            this.ticksSincePower = 0;
        } else {
            ++this.ticksSincePower;
        }
        if (!this.worldObj.isRemote) {
            if (!this.commandManager.hasTasks()) {
                this.displayText = "";
            } else {
                try {
                    Command curCommand = (Command)this.commandManager.getCommands().get(this.commandManager.getCurrentTask());
                    if (curCommand != null) {
                        this.displayText = curCommand.toString();
                    }
                }
                catch (Exception ex) {
                    // empty catch block
                }
            }
        }
        if (Math.abs(this.renderYaw - this.rotationYaw) > 0.001f) {
            float speedYaw;
            if (this.renderYaw > this.rotationYaw) {
                if (Math.abs(this.renderYaw - this.rotationYaw) >= 180.0f) {
                    this.getClass();
                    speedYaw = 1.3f;
                } else {
                    this.getClass();
                    speedYaw = -1.3f;
                }
            } else if (Math.abs(this.renderYaw - this.rotationYaw) >= 180.0f) {
                this.getClass();
                speedYaw = -1.3f;
            } else {
                this.getClass();
                speedYaw = 1.3f;
            }
            this.renderYaw += speedYaw;
            while (this.renderYaw < 0.0f) {
                this.renderYaw += 360.0f;
            }
            while (this.renderYaw > 360.0f) {
                this.renderYaw -= 360.0f;
            }
            if (this.ticks % 5L == 0L && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                this.worldObj.playSound((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, "assemblyline:conveyor", 0.8f, 1.7f, true);
            }
            float f = Math.abs(this.renderYaw - this.rotationYaw);
            this.getClass();
            if (f < 1.3f + 0.1f) {
                this.renderYaw = this.rotationYaw;
            }
            for (Entity e : (List<Entity>)this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)(this.yCoord + 2), (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 3), (double)(this.zCoord + 1)))) {
                e.rotationYaw = this.renderYaw;
            }
        }
        if (Math.abs(this.renderPitch - this.rotationPitch) > 0.001f) {
            float speedPitch;
            if (this.renderPitch > this.rotationPitch) {
                this.getClass();
                speedPitch = -1.3f;
            } else {
                this.getClass();
                speedPitch = 1.3f;
            }
            this.renderPitch += speedPitch;
            while (this.renderPitch < 0.0f) {
                this.renderPitch += 60.0f;
            }
            while (this.renderPitch > 60.0f) {
                this.renderPitch -= 60.0f;
            }
            if (this.ticks % 4L == 0L && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                this.worldObj.playSound((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, "assemblyline:conveyor", 2.0f, 2.5f, true);
            }
            float f = Math.abs(this.renderPitch - this.rotationPitch);
            this.getClass();
            if (f < 1.3f + 0.1f) {
                this.renderPitch = this.rotationPitch;
            }
            for (Entity e : (List<Entity>)this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)(this.yCoord + 2), (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 3), (double)(this.zCoord + 1)))) {
                e.rotationPitch = this.renderPitch;
            }
        }
        while (this.rotationYaw < 0.0f) {
            this.rotationYaw += 360.0f;
        }
        while (this.rotationYaw > 360.0f) {
            this.rotationYaw -= 360.0f;
        }
        while (this.rotationPitch < 0.0f) {
            this.rotationPitch += 60.0f;
        }
        while (this.rotationPitch > 60.0f) {
            this.rotationPitch -= 60.0f;
        }
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && this.ticks % 20L == 0L) {
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public Command getCurrentCommand() {
        if (this.commandManager.hasTasks() && this.commandManager.getCurrentTask() >= 0 && this.commandManager.getCurrentTask() < this.commandManager.getCommands().size()) {
            return (Command)this.commandManager.getCommands().get(this.commandManager.getCurrentTask());
        }
        return null;
    }

    public Vector3 getHandPosition() {
        Vector3 position = new Vector3(this);
        position.add(0.5);
        position.add(this.getDeltaHandPosition());
        return position;
    }

    public Vector3 getDeltaHandPosition() {
        double distance = 1.0;
        Vector3 delta = new Vector3();
        delta.y = Math.sin(Math.toRadians(this.renderPitch)) * distance * 2.0;
        double dH = Math.cos(Math.toRadians(this.renderPitch)) * distance;
        delta.x = Math.sin(Math.toRadians(-this.renderYaw)) * dH;
        delta.z = Math.cos(Math.toRadians(-this.renderYaw)) * dH;
        return delta;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("data", data);
        nbt.setInteger("transferRange", this.powerTransferRange);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, getBlockMetadata(), nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (this.worldObj.isRemote) {
            NBTTagCompound nbt = pkt.func_148857_g();
            this.powerTransferRange = nbt.getInteger("transferRange");
            NBTTagCompound data = nbt.getCompoundTag("data");
            this.readFromNBT(data);
        }
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public String getInventoryName() {
        return TranslationHelper.getLocal("tile.armbot.name");
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.disk;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.disk != null) {
            if (this.disk.stackSize <= par2) {
                ItemStack var3 = this.disk;
                this.disk = null;
                return var3;
            }
            ItemStack var3 = this.disk.splitStack(par2);
            if (this.disk.stackSize == 0) {
                this.disk = null;
            }
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.disk != null) {
            ItemStack var2 = this.disk;
            this.disk = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.disk = par2ItemStack;
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5) <= 64.0;
    }

    @Override
    public void openInventory() {
        ++this.playerUsing;
    }

    @Override
    public void closeInventory() {
        --this.playerUsing;
    }

    public String getCommandDisplayText() {
        return this.displayText;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagCompound diskNBT = nbt.getCompoundTag("disk");
        this.disk = diskNBT != null ? ItemStack.loadItemStackFromNBT((NBTTagCompound)diskNBT) : null;
        this.rotationYaw = nbt.getFloat("yaw");
        this.rotationPitch = nbt.getFloat("pitch");
        if (this.worldObj != null && this.worldObj.isRemote) {
            this.displayText = nbt.getString("cmdText");
        }
        this.commandManager.setCurrentTask(nbt.getInteger("curTask"));
        NBTTagList entities = nbt.getTagList("entities", 10);
        this.grabbedEntities.clear();
        for (int i = 0; i < entities.tagCount(); ++i) {
            NBTTagCompound entityTag = (NBTTagCompound)entities.getCompoundTagAt(i);
            if (entityTag == null) continue;
            Entity entity = EntityList.createEntityFromNBT((NBTTagCompound)entityTag, (World)this.worldObj);
            this.grabbedEntities.add(entity);
        }
        NBTTagList items = nbt.getTagList("items", 10);
        this.grabbedItems.clear();
        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound itemTag = (NBTTagCompound)items.getCompoundTagAt(i);
            if (itemTag == null) continue;
            ItemStack item = ItemStack.loadItemStackFromNBT((NBTTagCompound)itemTag);
            this.grabbedItems.add(item);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagCompound diskNBT = new NBTTagCompound();
        if (this.disk != null) {
            this.disk.writeToNBT(diskNBT);
        }
        nbt.setTag("disk", (NBTBase)diskNBT);
        nbt.setFloat("yaw", this.rotationYaw);
        nbt.setFloat("pitch", this.rotationPitch);
        nbt.setString("cmdText", this.displayText);
        nbt.setInteger("curTask", this.commandManager.getCurrentTask());
        NBTTagList entities = new NBTTagList();
        for (Entity entity : this.grabbedEntities) {
            if (entity == null) continue;
            NBTTagCompound entityNBT = new NBTTagCompound();
            entity.writeToNBT(entityNBT);
            entity.writeToNBTOptional(entityNBT);
            entities.appendTag((NBTBase)entityNBT);
        }
        nbt.setTag("entities", (NBTBase)entities);
        NBTTagList items = new NBTTagList();
        for (ItemStack itemStack : this.grabbedItems) {
            if (itemStack == null) continue;
            NBTTagCompound entityNBT = new NBTTagCompound();
            itemStack.writeToNBT(entityNBT);
            items.appendTag((NBTBase)entityNBT);
        }
        nbt.setTag("items", (NBTBase)items);
    }

    @Override
    public double getJoules() {
        return this.wattsReceived;
    }

    @Override
    public void setJoules(double joules) {
        this.wattsReceived = joules;
    }

    @Override
    public double getMaxJoules() {
        return 1000.0;
    }

    @Override
    public boolean onActivated(EntityPlayer player) {
        ItemStack containingStack = this.getStackInSlot(0);
        if (containingStack != null) {
            if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
                EntityItem dropStack = new EntityItem(this.worldObj, player.posX, player.posY, player.posZ, containingStack);
                dropStack.delayBeforeCanPickup = 0;
                this.worldObj.spawnEntityInWorld((Entity)dropStack);
            }
            this.setInventorySlotContents(0, null);
            return true;
        }
        if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemDisk) {
            this.setInventorySlotContents(0, player.getCurrentEquippedItem());
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            return true;
        }
        return false;
    }

    public void markDirty() {
        this.commandManager.clear();
        if (this.disk != null) {
            List<String> commands = ItemDisk.getCommands(this.disk);
            for (String commandString : commands) {
                String commandName = commandString.split(" ")[0];
                Class command = Command.getCommand(commandName);
                if (command == null) continue;
                ArrayList<String> commandParameters = new ArrayList<String>();
                for (String param : commandString.split(" ")) {
                    if (param.equals(commandName)) continue;
                    commandParameters.add(param);
                }
                this.addCommand(command, commandParameters.toArray(new String[0]));
            }
        } else {
            this.addCommand(Command.getCommand("DROP"));
            this.addCommand(Command.getCommand("RETURN"));
        }
    }

    public void addCommand(Class command) {
        this.commandManager.addCommand(this, command);
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void addCommand(Class command, String[] parameters) {
        this.commandManager.addCommand(this, command, parameters);
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void onCreate(Vector3 placedPosition) {
        AssemblyLine.blockMulti.makeFakeBlock(this.worldObj, Vector3.add(placedPosition, new Vector3(0.0, 1.0, 0.0)), placedPosition);
    }

    @Override
    public void onDestroy(TileEntity callingBlock) {
        this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air, 0, 3);
        this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, Blocks.air, 0, 3);
    }

    @Override
    public String getType() {
        return "ArmBot";
    }

    @Override
    public String[] getMethodNames() {
        return new String[]{"rotateBy", "rotateTo", "grab", "drop", "reset", "isWorking", "touchingEntity", "use", "fire", "return", "clear", "isHolding"};
    }

    @Override
    public Object[] callMethod( IComputerAccess computer, ILuaContext context, int method, Object[] arguments ) throws LuaException, InterruptedException {
        switch (method) {
            case 0: {
                if (arguments.length > 0) {
                    try {
                        double yaw = (Double)arguments[0];
                        double pitch = (Double)arguments[1];
                        this.addCommand(CommandRotateBy.class, new String[]{Double.toString(yaw), Double.toString(pitch)});
                        break;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        throw new IllegalArgumentException("expected number");
                    }
                }
                throw new IllegalArgumentException("expected number");
            }
            case 1: {
                if (arguments.length > 0) {
                    try {
                        double yaw = (Double)arguments[0];
                        double pitch = (Double)arguments[1];
                        this.addCommand(CommandRotateTo.class, new String[]{Double.toString(yaw), Double.toString(pitch)});
                        break;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        throw new IllegalArgumentException("expected number");
                    }
                }
                throw new IllegalArgumentException("expected number");
            }
            case 2: {
                this.addCommand(CommandGrab.class);
                break;
            }
            case 3: {
                this.addCommand(CommandDrop.class);
                break;
            }
            case 4: {
                this.commandManager.clear();
                this.addCommand(CommandReturn.class);
                break;
            }
            case 5: {
                return new Object[]{this.commandManager.hasTasks()};
            }
            case 6: {
                Vector3 serachPosition = this.getHandPosition();
                List found = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox((double)(serachPosition.x - 0.5), (double)(serachPosition.y - 0.5), (double)(serachPosition.z - 0.5), (double)(serachPosition.x + 0.5), (double)(serachPosition.y + 0.5), (double)(serachPosition.z + 0.5)));
                if (found != null && found.size() > 0) {
                    for (int i = 0; i < found.size(); ++i) {
                        if (found.get(i) == null || found.get(i) instanceof EntityPlayer || ((Entity)found.get((int)i)).ridingEntity != null) continue;
                        return new Object[]{true};
                    }
                }
                return new Object[]{false};
            }
            case 7: {
                if (arguments.length > 0) {
                    try {
                        int times = (Integer)arguments[0];
                        this.addCommand(CommandUse.class, new String[]{Integer.toString(times)});
                        break;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        throw new IllegalArgumentException("expected number");
                    }
                }
                this.addCommand(CommandUse.class);
                break;
            }
            case 8: {
                if (arguments.length > 0) {
                    try {
                        float strength = (float)((Double)arguments[0]).doubleValue();
                        this.addCommand(CommandFire.class, new String[]{Float.toString(strength)});
                        break;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        throw new IllegalArgumentException("expected number");
                    }
                }
                this.addCommand(CommandFire.class);
                break;
            }
            case 9: {
                this.addCommand(CommandReturn.class);
                break;
            }
            case 10: {
                this.commandManager.clear();
                break;
            }
            case 11: {
                return new Object[]{this.grabbedEntities.size() > 0};
            }
        }
        return null;
    }

    //TODO: WTF
    //@Override
    public boolean canAttachToSide(int side) {
        return side != ForgeDirection.UP.ordinal();
    }

    @Override
    public void attach(IComputerAccess computer) {
        ++this.computersAttached;
        List list = this.connectedComputers;
        synchronized (list) {
            this.connectedComputers.add(computer);
        }
    }

    @Override
    public void detach(IComputerAccess computer) {
        --this.computersAttached;
        List list = this.connectedComputers;
        synchronized (list) {
            this.connectedComputers.remove(computer);
        }
    }

    @Override
    public List<Entity> getGrabbedEntities() {
        return this.grabbedEntities;
    }

    @Override
    public List<ItemStack> getGrabbedItems() {
        return this.grabbedItems;
    }

    @Override
    public void grabEntity(Entity entity) {
        if (entity instanceof EntityItem) {
            this.grabItem(((EntityItem)entity).getEntityItem());
            entity.setDead();
        } else {
            this.grabbedEntities.add(entity);
        }
    }

    @Override
    public void grabItem(ItemStack itemStack) {
        this.grabbedItems.add(itemStack);
    }

    @Override
    public void dropEntity(Entity entity) {
        this.grabbedEntities.remove((Object)entity);
    }

    @Override
    public void dropItem(ItemStack itemStack) {
        Vector3 handPosition = this.getHandPosition();
        this.worldObj.spawnEntityInWorld((Entity)new EntityItem(this.worldObj, handPosition.x, handPosition.y, handPosition.z, itemStack));
        this.grabbedItems.remove((Object)itemStack);
    }

    @Override
    public void dropAll() {
        Vector3 handPosition = this.getHandPosition();
        Iterator it = this.grabbedItems.iterator();
        while (it.hasNext()) {
            ItemFindingHelper.dropItemStackExact(this.worldObj, handPosition.x, handPosition.y, handPosition.z, (ItemStack)it.next());
        }
        this.grabbedEntities.clear();
        this.grabbedItems.clear();
    }

    public boolean isProvidingPowerSide(ForgeDirection dir) {
        return this.isProvidingPower && dir.getOpposite() == this.getFacingDirectionFromAngle();
    }

    public ForgeDirection getFacingDirectionFromAngle() {
        float angle = MathHelper.wrapAngleTo180_float((float)this.rotationYaw);
        if (angle >= -45.0f && angle <= 45.0f) {
            return ForgeDirection.SOUTH;
        }
        if (angle >= 45.0f && angle <= 135.0f) {
            return ForgeDirection.WEST;
        }
        if (angle >= 135.0f && angle <= -135.0f) {
            return ForgeDirection.NORTH;
        }
        return ForgeDirection.EAST;
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return direction == ForgeDirection.DOWN;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean equals(IPeripheral other) {
        return super.equals(other);
    }
}

