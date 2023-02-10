package assemblyline.common.machine;

import java.util.List;

import assemblyline.api.IManipulator;
import assemblyline.common.block.BlockCrate;
import assemblyline.common.block.TileEntityCrate;
import assemblyline.common.machine.imprinter.TileEntityFilterable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.implement.IRedstoneReceptor;
import universalelectricity.prefab.implement.IRotatable;
import universalelectricity.prefab.multiblock.TileEntityMulti;

public class TileEntityManipulator
    extends TileEntityFilterable implements IRotatable, IRedstoneReceptor, IManipulator {
    public boolean selfPulse = false;
    private boolean isOutput = false;
    private boolean isRedstonePowered = false;

    public boolean isOutput() {
        return this.isOutput;
    }

    public void setOutput(boolean isOutput) {
        this.isOutput = isOutput;
        if (!this.worldObj.isRemote) {
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void toggleOutput() {
        this.setOutput(!this.isOutput());
    }

    @Override
    protected void onUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.ticks % 20L == 0L) {
                this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
            if (!this.isDisabled() && this.isRunning()) {
                if (!this.isOutput) {
                    this.inject();
                } else {
                    if (this.selfPulse && this.ticks % 10L == 0L) {
                        this.isRedstonePowered = true;
                    }
                    if (this.isRedstonePowered) {
                        this.eject();
                    }
                }
            }
        }
    }

    @Override
    public void inject() {
        Vector3 inputPosition = new Vector3(this);
        Vector3 outputUp = new Vector3(this);
        outputUp.modifyPositionFromSide(ForgeDirection.UP);
        Vector3 outputDown = new Vector3(this);
        outputDown.modifyPositionFromSide(ForgeDirection.DOWN);
        Vector3 outputPosition = new Vector3(this);
        outputPosition.modifyPositionFromSide(this.getDirection().getOpposite());
        if (outputPosition.getTileEntity((IBlockAccess) this.worldObj)
                    instanceof TileEntityManipulator
            && ((TileEntityManipulator
                ) outputPosition.getTileEntity((IBlockAccess) this.worldObj))
                    .getDirection()
                == this.getDirection().getOpposite()) {
            return;
        }
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(
            (double) inputPosition.x,
            (double) inputPosition.y,
            (double) inputPosition.z,
            (double) (inputPosition.x + 1.0),
            (double) (inputPosition.y + 1.0),
            (double) (inputPosition.z + 1.0)
        );
        List<EntityItem> itemsInBound
            = this.worldObj.getEntitiesWithinAABB(EntityItem.class, bounds);
        for (EntityItem entity : itemsInBound) {
            if (entity.isDead)
                continue;
            ItemStack remainingStack = entity.getEntityItem().copy();
            if (this.getFilter() != null && !this.isFiltering(remainingStack))
                continue;
            if ((remainingStack
                 = this.tryPlaceInPosition(remainingStack, outputUp, ForgeDirection.DOWN))
                != null) {
                remainingStack = this.tryPlaceInPosition(
                    remainingStack, outputDown, ForgeDirection.UP
                );
            }
            if (remainingStack != null) {
                remainingStack = this.tryPlaceInPosition(
                    remainingStack, outputPosition, this.getDirection().getOpposite()
                );
            }
            if (remainingStack != null && remainingStack.stackSize > 0) {
                this.throwItem(outputPosition, remainingStack);
            }
            entity.setDead();
        }
    }

    @Override
    public void eject() {
        this.onPowerOff();
        Vector3 inputUp = new Vector3(this);
        inputUp.modifyPositionFromSide(ForgeDirection.UP);
        Vector3 inputDown = new Vector3(this);
        inputDown.modifyPositionFromSide(ForgeDirection.DOWN);
        Vector3 inputPosition = new Vector3(this);
        inputPosition.modifyPositionFromSide(this.getDirection().getOpposite());
        Vector3 outputPosition = new Vector3(this);
        outputPosition.modifyPositionFromSide(this.getDirection());
        ItemStack itemStack = this.tryGrabFromPosition(inputUp, ForgeDirection.DOWN);
        if (itemStack == null) {
            itemStack = this.tryGrabFromPosition(inputDown, ForgeDirection.UP);
        }
        if (itemStack == null) {
            itemStack = this.tryGrabFromPosition(
                inputPosition, this.getDirection().getOpposite()
            );
        }
        if (itemStack != null && itemStack.stackSize > 0) {
            this.throwItem(outputPosition, itemStack);
        }
    }

    public void throwItem(Vector3 outputPosition, ItemStack items) {
        if (!this.worldObj.isRemote) {
            EntityItem entityItem = new EntityItem(
                this.worldObj,
                outputPosition.x + 0.5,
                outputPosition.y + 0.8,
                outputPosition.z + 0.5,
                items
            );
            entityItem.motionX = 0.0;
            entityItem.motionZ = 0.0;
            entityItem.motionY /= 5.0;
            entityItem.delayBeforeCanPickup = 20;
            this.worldObj.spawnEntityInWorld((Entity) entityItem);
        }
    }

    private ItemStack
    tryPlaceInPosition(ItemStack itemStack, Vector3 position, ForgeDirection direction) {
        TileEntity tileEntity = position.getTileEntity((IBlockAccess) this.worldObj);
        if (tileEntity != null && itemStack != null) {
            if (tileEntity instanceof TileEntityMulti) {
                Vector3 mainBlockPosition
                    = ((TileEntityMulti) tileEntity).mainBlockPosition;
                if (mainBlockPosition != null
                    && !(
                        mainBlockPosition.getTileEntity((IBlockAccess) this.worldObj)
                            instanceof TileEntityMulti
                    )) {
                    return this.tryPlaceInPosition(
                        itemStack, mainBlockPosition, direction
                    );
                }
            } else if (tileEntity instanceof TileEntityChest) {
                TileEntityChest[] chests
                    = new TileEntityChest[] { (TileEntityChest) tileEntity, null };
                for (int i = 2; i < 6; ++i) {
                    ForgeDirection searchDirection
                        = ForgeDirection.getOrientation((int) i);
                    Vector3 searchPosition = position.clone();
                    searchPosition.modifyPositionFromSide(searchDirection);
                    if (searchPosition.getTileEntity((IBlockAccess) this.worldObj) == null
                        || searchPosition.getTileEntity((IBlockAccess) this.worldObj)
                                .getClass()
                            != chests[0].getClass())
                        continue;
                    chests[1] = (TileEntityChest
                    ) searchPosition.getTileEntity((IBlockAccess) this.worldObj);
                    break;
                }
                for (TileEntityChest chest : chests) {
                    if (chest == null)
                        continue;
                    for (int i = 0; i < chest.getSizeInventory(); ++i) {
                        if ((itemStack
                             = this.addStackToInventory(i, (IInventory) chest, itemStack))
                            != null)
                            continue;
                        return null;
                    }
                }
            } else {
                if (tileEntity instanceof TileEntityCrate) {
                    return BlockCrate.putIn((TileEntityCrate) tileEntity, itemStack);
                }
                if (tileEntity instanceof ISidedInventory) {
                    int startIndex;
                    ISidedInventory inventory = (ISidedInventory) tileEntity;
                    for (int i :
                         inventory.getAccessibleSlotsFromSide(direction.ordinal())) {
                        if ((itemStack = this.addStackToInventory(
                                 i, (IInventory) inventory, itemStack
                             ))
                            != null)
                            continue;
                        return null;
                    }
                } else if (tileEntity instanceof IInventory) {
                    IInventory inventory = (IInventory) tileEntity;
                    for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                        if ((itemStack
                             = this.addStackToInventory(i, inventory, itemStack))
                            != null)
                            continue;
                        return null;
                    }
                }
            }
        }
        if (itemStack.stackSize <= 0) {
            return null;
        }
        return itemStack;
    }

    public ItemStack
    addStackToInventory(int slotIndex, IInventory inventory, ItemStack itemStack) {
        if (inventory.getSizeInventory() > slotIndex) {
            ItemStack stackInInventory = inventory.getStackInSlot(slotIndex);
            if (stackInInventory == null) {
                inventory.setInventorySlotContents(slotIndex, itemStack);
                if (inventory.getStackInSlot(slotIndex) == null) {
                    return itemStack;
                }
                return null;
            }
            if (stackInInventory.isItemEqual(itemStack)
                && stackInInventory.isStackable()) {
                stackInInventory = stackInInventory.copy();
                int stackLim = Math.min(
                    inventory.getInventoryStackLimit(), itemStack.getMaxStackSize()
                );
                int rejectedAmount = Math.max(
                    stackInInventory.stackSize + itemStack.stackSize - stackLim, 0
                );
                stackInInventory.stackSize = Math.min(
                    Math.max(
                        stackInInventory.stackSize + itemStack.stackSize - rejectedAmount,
                        0
                    ),
                    inventory.getInventoryStackLimit()
                );
                itemStack.stackSize = rejectedAmount;
                inventory.setInventorySlotContents(slotIndex, stackInInventory);
            }
        }
        if (itemStack.stackSize <= 0) {
            return null;
        }
        return itemStack;
    }

    private ItemStack tryGrabFromPosition(Vector3 position, ForgeDirection direction) {
        ItemStack returnStack;
    block6: {
        TileEntity tileEntity;
    block9: {
        int startIndex;
    block8: {
    block7: {
        returnStack = null;
        tileEntity = position.getTileEntity((IBlockAccess) this.worldObj);
        if (tileEntity == null)
            break block6;
        if (!(tileEntity instanceof TileEntityMulti))
            break block7;
        Vector3 mainBlockPosition = ((TileEntityMulti) tileEntity).mainBlockPosition;
        if (mainBlockPosition != null
            && !(
                mainBlockPosition.getTileEntity((IBlockAccess) this.worldObj)
                    instanceof TileEntityMulti
            )) {
            return this.tryGrabFromPosition(mainBlockPosition, direction);
        }
        break block6;
    }
        if (!(tileEntity instanceof TileEntityChest))
            break block8;
        TileEntityChest[] chests
            = new TileEntityChest[] { (TileEntityChest) tileEntity, null };
        for (int i = 2; i < 6; ++i) {
            ForgeDirection searchDirection = ForgeDirection.getOrientation((int) i);
            Vector3 searchPosition = position.clone();
            searchPosition.modifyPositionFromSide(searchDirection);
            if (searchPosition.getTileEntity((IBlockAccess) this.worldObj) == null
                || searchPosition.getTileEntity((IBlockAccess) this.worldObj).getClass()
                    != chests[0].getClass())
                continue;
            chests[1] = (TileEntityChest
            ) searchPosition.getTileEntity((IBlockAccess) this.worldObj);
            break;
        }
        for (TileEntityChest chest : chests) {
            if (chest == null)
                continue;
            for (int i = 0; i < chest.getSizeInventory(); ++i) {
                ItemStack itemStack
                    = this.removeStackFromInventory(i, (IInventory) chest);
                if (itemStack == null)
                    continue;
                returnStack = itemStack;
                break block6;
            }
        }
        break block6;
    }
        if (!(tileEntity instanceof ISidedInventory))
            break block9;
        ISidedInventory inventory = (ISidedInventory) tileEntity;
        for (int i : inventory.getAccessibleSlotsFromSide(direction.ordinal())) {
            ItemStack itemStack
                = this.removeStackFromInventory(i, (IInventory) inventory);
            if (itemStack == null)
                continue;
            returnStack = itemStack;
            break block6;
        }
        break block6;
    }
        if (!(tileEntity instanceof IInventory))
            break block6;
        IInventory inventory = (IInventory) tileEntity;
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack itemStack = this.removeStackFromInventory(i, inventory);
            if (itemStack == null)
                continue;
            returnStack = itemStack;
            break;
        }
    }
        return returnStack;
    }

    public ItemStack removeStackFromInventory(int slotIndex, IInventory inventory) {
        if (inventory.getStackInSlot(slotIndex) != null) {
            ItemStack itemStack = inventory.getStackInSlot(slotIndex).copy();
            if (this.getFilter() == null || this.isFiltering(itemStack)) {
                itemStack.stackSize = 1;
                inventory.decrStackSize(slotIndex, 1);
                return itemStack;
            }
        }
        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.isOutput = nbt.getBoolean("isOutput");
        this.selfPulse = nbt.getBoolean("selfpulse");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("isOutput", this.isOutput);
        nbt.setBoolean("selfpulse", this.selfPulse);
    }

    @Override
    public void onPowerOn() {
        this.isRedstonePowered = true;
    }

    @Override
    public void onPowerOff() {
        this.isRedstonePowered = false;
    }

    @Override
    public boolean canConnect(ForgeDirection dir) {
        return dir != this.getDirection();
    }
}
