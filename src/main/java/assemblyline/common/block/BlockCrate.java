package assemblyline.common.block;

import java.util.List;

import assemblyline.common.AssemblyLine;
import assemblyline.common.PathfinderCrate;
import assemblyline.common.TabAssemblyLine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import universalelectricity.core.UniversalElectricity;

public class BlockCrate extends BlockALMachine {
    IIcon crate_icon;

    public BlockCrate() {
        super(UniversalElectricity.machine);
        this.setBlockName("crate");
        this.setCreativeTab(TabAssemblyLine.INSTANCE);
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg) {
        this.crate_icon = iconReg.registerIcon("assemblyline:crate");
    }

    @Override
    public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
        return this.crate_icon;
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return this.crate_icon;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityCrate) {
            TileEntityCrate tileEntity = (TileEntityCrate) world.getTileEntity(x, y, z);
            boolean allMode = world.getWorldTime() - tileEntity.prevClickTime < 10L;
            tileEntity.prevClickTime = world.getWorldTime();
            this.tryEject(tileEntity, player, allMode);
        }
    }

    @Override
    public boolean onBlockActivated(
        World world,
        int x,
        int y,
        int z,
        EntityPlayer entityPlayer,
        int side,
        float hitX,
        float hitY,
        float hitZ
    ) {
        if (super.onBlockActivated(
                world, x, y, z, entityPlayer, side, hitX, hitY, hitZ
            )) {
            return true;
        }
        if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityCrate) {
            TileEntityCrate tileEntity = (TileEntityCrate) world.getTileEntity(x, y, z);
            boolean allMode = world.getWorldTime() - tileEntity.prevClickTime < 10L;
            tileEntity.prevClickTime = world.getWorldTime();
            ItemStack current = entityPlayer.inventory.getCurrentItem();
            if (side == 1 || side > 1 && (double) hitY > 0.5
                || !entityPlayer.capabilities.isCreativeMode) {
                if (current != null
                    && (current.getMaxStackSize() > 1
                        || current.getItem() == Item.getItemFromBlock(this))) {
                    this.tryInsert(tileEntity, entityPlayer, allMode);
                }
            } else if (side == 0 || side > 1 && (double) hitY <= 0.5) {
                this.tryEject(tileEntity, entityPlayer, allMode);
            }
        }
        return true;
    }

    public void tryInsert(
        TileEntityCrate tileEntity, EntityPlayer player, boolean allMode, boolean doSearch
    ) {
        boolean success = allMode ? this.insertAllItems(tileEntity, player)
                                  : this.insertCurrentItem(tileEntity, player);
        if (!success && doSearch) {
            PathfinderCrate pathfinder = new PathfinderCrate().init(tileEntity);
            for (TileEntity checkTile : pathfinder.iteratedNodes) {
                if (!(checkTile instanceof TileEntityCrate))
                    continue;
                AssemblyLine.blockCrate.tryInsert(
                    (TileEntityCrate) checkTile, player, allMode, false
                );
            }
        }
    }

    public void
    tryInsert(TileEntityCrate tileEntity, EntityPlayer player, boolean allMode) {
        this.tryInsert(tileEntity, player, allMode, true);
    }

    public void
    tryEject(TileEntityCrate tileEntity, EntityPlayer player, boolean allMode) {
        if (allMode && !player.isSneaking()) {
            this.ejectItems(tileEntity, player, tileEntity.getMaxLimit());
        } else if (player.isSneaking()) {
            this.ejectItems(tileEntity, player, 1);
        } else {
            ItemStack stack = tileEntity.getStackInSlot(0);
            if (stack != null) {
                this.ejectItems(tileEntity, player, stack.getMaxStackSize());
            }
        }
    }

    public boolean insertCurrentItem(TileEntityCrate tileEntity, EntityPlayer player) {
        ItemStack currentStack = player.getCurrentEquippedItem();
        if (currentStack != null) {
            if (currentStack.isStackable()) {
                if (tileEntity.getStackInSlot(0) != null
                    && !tileEntity.getStackInSlot(0).isItemEqual(currentStack)) {
                    return false;
                }
                player.inventory.setInventorySlotContents(
                    player.inventory.currentItem,
                    BlockCrate.putIn(tileEntity, currentStack)
                );
                return true;
            }
            if (currentStack.getItem()
                == Item.getItemFromBlock(AssemblyLine.blockCrate)) {
                ItemStack containedStack
                    = ItemBlockCrate.getContainingItemStack(currentStack);
                ItemStack crateStack = tileEntity.getStackInSlot(0);
                if (containedStack != null
                    && (crateStack == null
                        || crateStack != null
                            && containedStack.getItem() == crateStack.getItem()
                            && containedStack.getItemDamage()
                                == crateStack.getItemDamage())) {
                    ItemStack returned = BlockCrate.putIn(tileEntity, containedStack);
                    ItemBlockCrate.setContainingItemStack(currentStack, returned);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean insertAllItems(TileEntityCrate tileEntity, EntityPlayer player) {
        ItemStack requestStack = null;
        if (tileEntity.getStackInSlot(0) != null) {
            requestStack = tileEntity.getStackInSlot(0).copy();
        }
        if (requestStack == null) {
            requestStack = player.getCurrentEquippedItem();
        }
        if (requestStack != null && requestStack.isStackable()) {
            boolean success = false;
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack currentStack = player.inventory.getStackInSlot(i);
                if (currentStack == null || !requestStack.isItemEqual(currentStack))
                    continue;
                player.inventory.setInventorySlotContents(
                    i, BlockCrate.putIn(tileEntity, currentStack)
                );
                if (player instanceof EntityPlayerMP) {
                    ((EntityPlayerMP) player)
                        .sendContainerToPlayer(player.inventoryContainer);
                }
                success = true;
            }
            return success;
        }
        return false;
    }

    public boolean
    ejectItems(TileEntityCrate tileEntity, EntityPlayer player, int requestSize) {
        World world = tileEntity.getWorldObj();
        ItemStack containingStack = tileEntity.getStackInSlot(0);
        if (containingStack != null) {
            if (containingStack.stackSize > 0 && requestSize > 0) {
                int amountToTake = Math.min(containingStack.stackSize, requestSize);
                ItemStack dropStack = containingStack.copy();
                dropStack.stackSize = amountToTake;
                if (!world.isRemote) {
                    EntityItem entityItem = new EntityItem(
                        world, player.posX, player.posY, player.posZ, dropStack
                    );
                    entityItem.delayBeforeCanPickup = 0;
                    world.spawnEntityInWorld((Entity) entityItem);
                }
                containingStack.stackSize -= amountToTake;
            }
            if (containingStack.stackSize <= 0) {
                containingStack = null;
            }
            tileEntity.setInventorySlotContents(0, containingStack);
            return true;
        }
        return false;
    }

    public static ItemStack putIn(TileEntityCrate tileEntity, ItemStack itemStack) {
        ItemStack containingStack = tileEntity.getStackInSlot(0);
        if (containingStack != null) {
            if (containingStack.isStackable() && containingStack.isItemEqual(itemStack)) {
                int newStackSize = containingStack.stackSize + itemStack.stackSize;
                int overFlowAmount = newStackSize - tileEntity.getInventoryStackLimit();
                itemStack.stackSize = overFlowAmount > 0 ? overFlowAmount : 0;
                containingStack.stackSize = newStackSize;
                tileEntity.setInventorySlotContents(0, containingStack);
            }
        } else {
            tileEntity.setInventorySlotContents(0, itemStack.copy());
            itemStack.stackSize = 0;
        }
        if (itemStack.stackSize <= 0) {
            return null;
        }
        return itemStack;
    }

    @Override
    public boolean onUseWrench(
        World world,
        int x,
        int y,
        int z,
        EntityPlayer par5EntityPlayer,
        int side,
        float hitX,
        float hitY,
        float hitZ
    ) {
        TileEntityCrate tileEntity;
        ItemStack containingStack;
        if (!world.isRemote && world.getTileEntity(x, y, z) != null
            && (containingStack
                = (tileEntity = (TileEntityCrate) world.getTileEntity(x, y, z))
                      .getStackInSlot(0))
                != null
            && containingStack.stackSize > 0) {
            float var6 = 0.7f;
            double var7
                = (double) (world.rand.nextFloat() * var6) + (double) (1.0f - var6) * 0.5;
            double var9
                = (double) (world.rand.nextFloat() * var6) + (double) (1.0f - var6) * 0.5;
            double var11
                = (double) (world.rand.nextFloat() * var6) + (double) (1.0f - var6) * 0.5;
            ItemStack dropStack = new ItemStack((Block) this, 1, tileEntity.getTier());
            ItemBlockCrate.setContainingItemStack(dropStack, containingStack);
            EntityItem var13 = new EntityItem(
                world, (double) x + var7, (double) y + var9, (double) z + var11, dropStack
            );
            var13.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld((Entity) var13);
            tileEntity.setInventorySlotContents(0, null);
            world.setBlock(x, y, z, Blocks.air, 0, 3);
            return true;
        }
        return false;
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TileEntityCrate();
    }

    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < 3; ++i) {
            list.add(new ItemStack((Block) this, 1, i));
        }
    }
}
