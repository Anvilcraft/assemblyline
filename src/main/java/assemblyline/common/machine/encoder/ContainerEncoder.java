package assemblyline.common.machine.encoder;

import assemblyline.common.AssemblyLine;
import assemblyline.common.machine.imprinter.SlotCustom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEncoder extends Container {
    public static final int Y_OFFSET = 0;
    private ItemStack[] containingItems = new ItemStack[1];
    private InventoryPlayer inventoryPlayer;
    private TileEntityEncoder tileEntity;

    public ContainerEncoder(InventoryPlayer inventoryPlayer, TileEntityEncoder encoder) {
        int var3;
        this.inventoryPlayer = inventoryPlayer;
        this.tileEntity = encoder;
        this.addSlotToContainer(new SlotCustom(
            (IInventory) encoder, 0, 80, 24, new ItemStack(AssemblyLine.itemDisk)
        ));
        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(
                    (IInventory) inventoryPlayer,
                    var4 + var3 * 9 + 9,
                    8 + var4 * 18,
                    155 + var3 * 18 + 0
                ));
            }
        }
        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(
                new Slot((IInventory) inventoryPlayer, var3, 8 + var3 * 18, 213)
            );
        }
    }

    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack copyStack = null;
        Slot slotObj = (Slot) this.inventorySlots.get(slot);
        if (slotObj != null && slotObj.getHasStack()) {
            ItemStack slotStack = slotObj.getStack();
            copyStack = slotStack.copy();
            if (slot >= 1 ? this.getSlot(0).isItemValid(slotStack)
                        && !this.mergeItemStack(slotStack, 0, 1, false)
                          : !this.mergeItemStack(
                              slotStack, this.containingItems.length, 37, false
                          )) {
                return null;
            }
            if (slotStack.stackSize == 0) {
                slotObj.putStack((ItemStack) null);
            } else {
                slotObj.onSlotChanged();
            }
            if (slotStack.stackSize == copyStack.stackSize) {
                return null;
            }
            slotObj.onPickupFromSlot(player, slotStack);
        }
        return copyStack;
    }
}
