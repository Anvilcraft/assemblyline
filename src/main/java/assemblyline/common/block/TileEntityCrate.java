package assemblyline.common.block;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import universalelectricity.prefab.implement.ITier;
import universalelectricity.prefab.tile.TileEntityAdvanced;

public class TileEntityCrate
extends TileEntityAdvanced
implements ITier,
IInventory {
    private ItemStack[] containingItems = new ItemStack[1];
    public long prevClickTime = -1000L;

    public int getMaxLimit() {
        return TileEntityCrate.getMaxLimit(this.getTier());
    }

    public static int getMaxLimit(int tier) {
        if (tier >= 2) {
            return 16384;
        }
        if (tier >= 1) {
            return 4096;
        }
        return 2048;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    /*@Override
    public void handlePacketData(INetworkManager network, int packetType, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) {
        if (this.field_70331_k.isRemote) {
            try {
                if (dataStream.readBoolean()) {
                    if (this.containingItems[0] == null) {
                        this.containingItems[0] = new ItemStack(dataStream.readInt(), dataStream.readInt(), dataStream.readInt());
                    } else {
                        this.containingItems[0].field_77993_c = dataStream.readInt();
                        this.containingItems[0].stackSize = dataStream.readInt();
                        this.containingItems[0].setItemDamage(dataStream.readInt());
                    }
                } else {
                    this.containingItems[0] = null;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (this.worldObj.isRemote) {
            NBTTagCompound nbt = pkt.func_148857_g();
            if (nbt.getBoolean("containsItems")) {
                if (containingItems[0] == null) {
                    containingItems[0] = new ItemStack(Item.getItemById(nbt.getShort("id")), nbt.getByte("Count"), nbt.getShort("Damage"));
                } else {
                    containingItems[0].readFromNBT(nbt);
                }
            } else {
                containingItems[0] = null;
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        if (this.containingItems[0] != null) {
            nbt.setBoolean("containsItems", true);
            containingItems[0].writeToNBT(nbt);
            //return PacketManager.getPacket("AssemblyLine", this, new Object[]{true, this.containingItems[0].field_77993_c, this.containingItems[0].stackSize, this.containingItems[0].getItemDamage()});
        } else {
            nbt.setBoolean("containsItems", false);
        }
        //return PacketManager.getPacket("AssemblyLine", this, new Object[]{false});
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, getBlockMetadata(), nbt);
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.containingItems[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.containingItems[par1] != null) {
            if (this.containingItems[par1].stackSize <= par2) {
                ItemStack var3 = this.containingItems[par1];
                this.setInventorySlotContents(par1, null);
                return var3;
            }
            ItemStack var3 = this.containingItems[par1].splitStack(par2);
            if (this.containingItems[par1].stackSize == 0) {
                this.containingItems[par1] = null;
            }
            this.setInventorySlotContents(par1, this.containingItems[par1]);
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.containingItems[par1] != null) {
            ItemStack var2 = this.containingItems[par1];
            this.containingItems[par1] = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (stack != null) {
            if (stack.isStackable()) {
                this.containingItems[slot] = stack;
                if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
                    stack.stackSize = this.getInventoryStackLimit();
                }
            }
        } else {
            this.containingItems[slot] = null;
        }
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5) <= 64.0;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList var2 = nbt.getTagList("Items", 10);
        this.containingItems = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 < 0 || var5 >= this.containingItems.length) continue;
            this.containingItems[var5] = ItemStack.loadItemStackFromNBT((NBTTagCompound)var4);
        }
        if (this.containingItems[0] != null) {
            this.containingItems[0].stackSize = nbt.getInteger("Count");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.containingItems.length; ++var3) {
            if (this.containingItems[var3] == null) continue;
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)var3);
            this.containingItems[var3].writeToNBT(var4);
            var2.appendTag((NBTBase)var4);
        }
        nbt.setTag("Items", (NBTBase)var2);
        if (this.containingItems[0] != null) {
            nbt.setInteger("Count", this.containingItems[0].stackSize);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return this.getMaxLimit();
    }

    @Override
    public int getSizeInventory() {
        return this.containingItems.length;
    }

    @Override
    public String getInventoryName() {
        return "Crate";
    }

    @Override
    public int getTier() {
        return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public void setTier(int tier) {
        this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, tier, 3);
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }
}

