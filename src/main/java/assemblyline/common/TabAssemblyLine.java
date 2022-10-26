package assemblyline.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabAssemblyLine
extends CreativeTabs {
    public static final TabAssemblyLine INSTANCE = new TabAssemblyLine("assemblyline");
    public static ItemStack itemStack;

    public TabAssemblyLine(String par2Str) {
        super(par2Str);
    }

    public ItemStack getIconItemStack() {
        return itemStack;
    }

    @Override
    public Item getTabIconItem() {
        return itemStack.getItem();
    }
}

