package assemblyline.common.machine.encoder;

import assemblyline.common.machine.command.Command;
import assemblyline.common.machine.encoder.IInventoryWatcher;
import assemblyline.common.machine.encoder.ItemDisk;
import com.google.common.io.ByteArrayDataInput;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.prefab.tile.TileEntityAdvanced;

public class TileEntityEncoder
extends TileEntityAdvanced
implements ISidedInventory {
    private ItemStack disk;
    private IInventoryWatcher watcher;

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot == 0) {
            return this.disk;
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot == 0 && amount >= 1) {
            ItemStack ret = this.disk.copy();
            this.disk = null;
            return ret;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot == 0) {
            if (stack != null && stack.stackSize > 1) {
                stack.stackSize = 1;
            }
            this.disk = stack;
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (this.watcher != null) {
            this.watcher.inventoryChanged();
        }
    }

    @Override
    public String getInventoryName() {
        return "Encoder";
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5) <= 64.0;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    public void setWatcher(IInventoryWatcher watcher) {
        this.watcher = watcher;
    }

    public IInventoryWatcher getWatcher() {
        return this.watcher;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (this.disk != null) {
            NBTTagCompound diskNBT = new NBTTagCompound();
            this.disk.writeToNBT(diskNBT);
            nbt.setTag("disk", diskNBT);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagCompound diskNBT = nbt.getCompoundTag("disk");
        if (diskNBT != null) {
            this.disk = ItemStack.loadItemStackFromNBT((NBTTagCompound)diskNBT);
        }
    }

    public void handleMessage(NBTTagCompound nbt) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && this.disk != null) {
            ArrayList<String> tempCmds = ItemDisk.getCommands(this.disk);
            if (nbt.getBoolean("create")) {
                String newCommand = nbt.getString("newCommand");
                String commandName = newCommand.split(" ")[0];
                if (Command.getCommand(commandName) != null) {
                    tempCmds.add(newCommand);
                }
            } else {
                int commandToRemove = nbt.getInteger("commandToRemove");
                tempCmds.remove(commandToRemove);
            }
            ItemDisk.setCommands(this.disk, tempCmds);
        }
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean canExtractItem(int arg0, ItemStack arg1, int arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canInsertItem(int arg0, ItemStack arg1, int arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == ForgeDirection.UP.ordinal() ? new int[] {0} : new int[0];
    }
}

