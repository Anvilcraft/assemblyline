package assemblyline.common.machine.imprinter;

import assemblyline.common.machine.imprinter.ContainerImprinter;
import assemblyline.common.machine.imprinter.WatchedSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotCraftingResult extends WatchedSlot {
    private ContainerImprinter container;

    public SlotCraftingResult(
        ContainerImprinter container, IInventory inventory, int par2, int par3, int par4
    ) {
        super(inventory, par2, par3, par4, container);
        this.container = container;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    public boolean canTakeStack(EntityPlayer player) {
        return true;
    }

    public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack) {
        this.container.tileEntity.onPickUpFromResult(entityPlayer, itemStack);
        super.onPickupFromSlot(entityPlayer, itemStack);
    }
}
