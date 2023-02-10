package assemblyline.common.machine.imprinter;

import assemblyline.common.AssemblyLine;
import assemblyline.common.machine.imprinter.ISlotWatcher;
import assemblyline.common.machine.imprinter.SlotCraftingResult;
import assemblyline.common.machine.imprinter.SlotCustom;
import assemblyline.common.machine.imprinter.TileEntityImprinter;
import assemblyline.common.machine.imprinter.WatchedSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerImprinter extends Container implements ISlotWatcher {
    public InventoryPlayer inventoryPlayer;
    public TileEntityImprinter tileEntity;

    public ContainerImprinter(
        InventoryPlayer inventoryPlayer, TileEntityImprinter tileEntity
    ) {
        int var3;
        this.tileEntity = tileEntity;
        this.tileEntity.container = this;
        this.inventoryPlayer = inventoryPlayer;
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                this.addSlotToContainer(new WatchedSlot(
                    (IInventory) this.tileEntity, y + x * 3, 9 + y * 18, 16 + x * 18, this
                ));
            }
        }
        this.addSlotToContainer(new SlotCustom(
            (IInventory) this.tileEntity,
            9,
            68,
            34,
            new ItemStack(AssemblyLine.itemImprint)
        ));
        this.addSlotToContainer(
            new WatchedSlot((IInventory) this.tileEntity, 10, 92, 34, this)
        );
        this.addSlotToContainer(
            new SlotCraftingResult(this, (IInventory) this.tileEntity, 11, 148, 34)
        );
        for (int ii = 0; ii < 2; ++ii) {
            for (int i = 0; i < 9; ++i) {
                this.addSlotToContainer(new WatchedSlot(
                    (IInventory) this.tileEntity,
                    i + ii * 9 + 12,
                    8 + i * 18,
                    80 + ii * 18,
                    this
                ));
            }
        }
        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new WatchedSlot(
                    (IInventory) inventoryPlayer,
                    var4 + var3 * 9 + 9,
                    8 + var4 * 18,
                    120 + var3 * 18,
                    this
                ));
            }
        }
        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new WatchedSlot(
                (IInventory) inventoryPlayer, var3, 8 + var3 * 18, 178, this
            ));
        }
        this.tileEntity.openInventory();
    }

    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        this.tileEntity.closeInventory();
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
            if (slot == 12 - 1) {
                this.tileEntity.setInventorySlotContents(12 - 1, null);
            }
            if (slot > this.tileEntity.getSizeInventory() - 1) {
                if (this.getSlot(9).isItemValid(slotStack)) {
                    if (!this.mergeItemStack(slotStack, 9, 9 + 1, true)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(
                               slotStack, 12, this.tileEntity.getSizeInventory(), false
                           )) {
                    return null;
                }
            } else if (!this.mergeItemStack(
                           slotStack,
                           this.tileEntity.getSizeInventory(),
                           this.tileEntity.getSizeInventory() + 36,
                           false
                       )) {
                return null;
            }
            if (slotStack.stackSize == 0) {
                slotObj.putStack(null);
            } else {
                slotObj.onSlotChanged();
            }
            if (slotStack.stackSize == copyStack.stackSize) {
                return null;
            }
            slotObj.onPickupFromSlot(player, slotStack);
        }
        this.slotContentsChanged();
        return copyStack;
    }

    @Override
    public void slotContentsChanged() {
        this.tileEntity.markDirty();
        this.detectAndSendChanges();
    }
}
