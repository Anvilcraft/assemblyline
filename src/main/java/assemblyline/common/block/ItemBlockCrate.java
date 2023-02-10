package assemblyline.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBlockCrate extends ItemBlock {
    public ItemBlockCrate(Block block) {
        super(block);
        this.setMaxStackSize(1);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return this.getUnlocalizedName() + "." + itemStack.getItemDamage();
    }

    @Override
    public void addInformation(
        ItemStack itemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4
    ) {
        ItemStack containingStack = ItemBlockCrate.getContainingItemStack(itemStack);
        if (containingStack != null) {
            par3List.add(containingStack.getDisplayName());
            par3List.add("Amount: " + containingStack.stackSize);
        } else {
            par3List.add("Empty");
        }
    }

    @Override
    public void onUpdate(
        ItemStack itemStack, World par2World, Entity entity, int par4, boolean par5
    ) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            ItemStack containingStack = ItemBlockCrate.getContainingItemStack(itemStack);
            if (containingStack != null) {
                player.addPotionEffect(new PotionEffect(
                    Potion.moveSlowdown.id,
                    5,
                    (int
                    ) ((float) containingStack.stackSize
                       / (float) TileEntityCrate.getMaxLimit(itemStack.getItemDamage()))
                        * 5
                ));
            }
        }
    }

    public static void
    setContainingItemStack(ItemStack itemStack, ItemStack containingStack) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        if (containingStack != null) {
            NBTTagCompound itemTagCompound = new NBTTagCompound();
            containingStack.stackSize = Math.abs(containingStack.stackSize);
            containingStack.writeToNBT(itemTagCompound);
            itemStack.getTagCompound().setTag("Item", (NBTBase) itemTagCompound);
            itemStack.getTagCompound().setInteger("Count", containingStack.stackSize);
        } else {
            itemStack.getTagCompound().setTag("Item", (NBTBase) new NBTTagCompound());
            itemStack.getTagCompound().setInteger("Count", 0);
        }
    }

    public static ItemStack getContainingItemStack(ItemStack itemStack) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound(new NBTTagCompound());
            return null;
        }
        NBTTagCompound itemTagCompound
            = itemStack.getTagCompound().getCompoundTag("Item");
        ItemStack containingStack
            = ItemStack.loadItemStackFromNBT((NBTTagCompound) itemTagCompound);
        if (containingStack != null) {
            containingStack.stackSize = itemStack.getTagCompound().getInteger("Count");
        }
        return containingStack;
    }

    @Override
    public int getMetadata(int metadata) {
        return metadata;
    }

    @Override
    public boolean placeBlockAt(
        ItemStack stack,
        EntityPlayer player,
        World world,
        int x,
        int y,
        int z,
        int side,
        float hitX,
        float hitY,
        float hitZ,
        int metadata
    ) {
        if (super.placeBlockAt(
                stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata
            )) {
            ItemStack containingItem = ItemBlockCrate.getContainingItemStack(stack);
            if (world.getTileEntity(x, y, z) != null && containingItem != null
                && containingItem.stackSize > 0) {
                TileEntityCrate tileEntity
                    = (TileEntityCrate) world.getTileEntity(x, y, z);
                tileEntity.setInventorySlotContents(0, containingItem);
            }
        }
        return true;
    }
}
