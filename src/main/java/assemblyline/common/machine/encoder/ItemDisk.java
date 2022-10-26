package assemblyline.common.machine.encoder;

import assemblyline.common.TabAssemblyLine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemDisk
extends Item {
    public ItemDisk() {
        super();
        this.setUnlocalizedName("disk");
        this.setCreativeTab(TabAssemblyLine.INSTANCE);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("assemblyline:disk");
    }

    @Override
    public int getItemStackLimit() {
        return 1;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        ArrayList commands = ItemDisk.getCommands(itemStack);
        if (commands.size() > 0) {
            if (commands.size() == 1) {
                list.add(commands.size() + " command");
            } else {
                list.add(commands.size() + " commands");
            }
        } else {
            list.add("No commands");
        }
    }

    public static void setCommands(ItemStack itemStack, ArrayList commands) {
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagList nbt = new NBTTagList();
        for (int i = 0; i < commands.size(); ++i) {
            if (commands.get(i) == null) continue;
            NBTTagCompound newCompound = new NBTTagCompound();
            newCompound.setString("command", (String)commands.get(i));
            nbt.appendTag((NBTBase)newCompound);
        }
        itemStack.getTagCompound().setTag("Commands", (NBTBase)nbt);
    }

    public static ArrayList getCommands(ItemStack itemStack) {
        ArrayList<String> commands = new ArrayList<String>();
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbt = itemStack.getTagCompound();
        NBTTagList tagList = nbt.getTagList("Commands", 10);
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound curTag = tagList.getCompoundTagAt(i);
            String cmd = curTag.getString("command");
            commands.add(cmd);
        }
        return commands;
    }
}

