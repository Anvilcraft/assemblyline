package assemblyline.common.machine.imprinter;

import assemblyline.api.IFilterable;
import assemblyline.common.machine.TileEntityAssemblyNetwork;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.prefab.implement.IRotatable;

public abstract class TileEntityFilterable
extends TileEntityAssemblyNetwork
implements IRotatable,
IFilterable {
    private ItemStack filterItem;
    private boolean inverted;

    public boolean isFiltering(ItemStack itemStack) {
        ArrayList checkStacks;
        if (this.getFilter() != null && itemStack != null && (checkStacks = ItemImprinter.getFilters(this.getFilter())) != null) {
            for (int i = 0; i < checkStacks.size(); ++i) {
                if (checkStacks.get(i) == null || !((ItemStack)checkStacks.get(i)).isItemEqual(itemStack)) continue;
                return !this.inverted;
            }
        }
        return this.inverted;
    }

    @Override
    public void setFilter(ItemStack filter) {
        this.filterItem = filter;
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public ItemStack getFilter() {
        return this.filterItem;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public boolean isInverted() {
        return this.inverted;
    }

    public void toggleInversion() {
        this.setInverted(!this.isInverted());
    }

    @Override
    public ForgeDirection getDirection(IBlockAccess world, int x, int y, int z) {
        return ForgeDirection.getOrientation((int)this.getBlockMetadata());
    }

    @Override
    public void setDirection(World world, int x, int y, int z, ForgeDirection facingDirection) {
        this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, facingDirection.ordinal(), 3);
    }

    public void setDirection(ForgeDirection facingDirection) {
        this.setDirection(this.worldObj, this.xCoord, this.yCoord, this.zCoord, facingDirection);
    }

    public ForgeDirection getDirection() {
        return this.getDirection((IBlockAccess)this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, getBlockMetadata(), tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (this.worldObj.isRemote) {
            this.readFromNBT(pkt.func_148857_g());
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagCompound filter = new NBTTagCompound();
        if (this.getFilter() != null) {
            this.getFilter().writeToNBT(filter);
        }
        nbt.setTag("filter", (NBTBase)filter);
        nbt.setBoolean("inverted", this.inverted);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.inverted = nbt.getBoolean("inverted");
        NBTTagCompound filter = nbt.getCompoundTag("filter");
        this.filterItem = ItemStack.loadItemStackFromNBT((NBTTagCompound)filter);
    }
}

