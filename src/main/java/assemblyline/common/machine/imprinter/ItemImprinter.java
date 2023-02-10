package assemblyline.common.machine.imprinter;

import java.util.ArrayList;
import java.util.List;

import assemblyline.common.TabAssemblyLine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;

public class ItemImprinter extends Item {
    public ItemImprinter() {
        super();
        this.setUnlocalizedName("imprint");
        this.setCreativeTab(TabAssemblyLine.INSTANCE);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("assemblyline:imprint");
    }

    @Override
    public int getItemStackLimit() {
        return 1;
    }

    @Override
    public boolean
    onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity != null && !(entity instanceof IProjectile)
            && !(entity instanceof EntityPlayer)) {
            String stringName = EntityList.getEntityString((Entity) entity);
            player.addChatMessage(new ChatComponentText("Target: " + stringName));
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(
        ItemStack itemStack, EntityPlayer par2EntityPlayer, List list, boolean par4
    ) {
        List<ItemStack> filterItems = ItemImprinter.getFilters(itemStack);
        if (filterItems.size() > 0) {
            for (ItemStack filterItem : filterItems) {
                list.add(filterItem.getDisplayName());
            }
        } else {
            list.add("No filters");
        }
    }

    public static void setFilters(ItemStack itemStack, ArrayList filterStacks) {
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagList nbt = new NBTTagList();
        for (int i = 0; i < filterStacks.size(); ++i) {
            if (filterStacks.get(i) == null)
                continue;
            NBTTagCompound newCompound = new NBTTagCompound();
            newCompound.setByte("Slot", (byte) i);
            ((ItemStack) filterStacks.get(i)).writeToNBT(newCompound);
            nbt.appendTag((NBTBase) newCompound);
        }
        itemStack.getTagCompound().setTag("Items", (NBTBase) nbt);
    }

    public static ArrayList getFilters(ItemStack itemStack) {
        ArrayList<ItemStack> filterStacks = new ArrayList<ItemStack>();
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbt = itemStack.getTagCompound();
        NBTTagList tagList = nbt.getTagList("Items", 10);
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound var4 = (NBTTagCompound) tagList.getCompoundTagAt(i);
            byte var5 = var4.getByte("Slot");
            filterStacks.add(ItemStack.loadItemStackFromNBT((NBTTagCompound) var4));
        }
        return filterStacks;
    }
}
