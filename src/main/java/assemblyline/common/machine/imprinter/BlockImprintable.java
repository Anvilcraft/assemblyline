package assemblyline.common.machine.imprinter;

import assemblyline.api.IFilterable;
import assemblyline.common.block.BlockALMachine;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import universalelectricity.prefab.implement.IRedstoneReceptor;

public abstract class BlockImprintable
extends BlockALMachine {
    public BlockImprintable(String name, Material material, CreativeTabs creativeTab) {
        super(material);
        this.setBlockName(name);
        this.setCreativeTab(creativeTab);
    }

    @Override
    public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof IFilterable) {
            ItemStack containingStack = ((IFilterable)tileEntity).getFilter();
            if (containingStack != null) {
                if (!world.isRemote) {
                    EntityItem dropStack = new EntityItem(world, player.posX, player.posY, player.posZ, containingStack);
                    dropStack.delayBeforeCanPickup = 0;
                    world.spawnEntityInWorld((Entity)dropStack);
                }
                ((IFilterable)tileEntity).setFilter(null);
                return true;
            }
            if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemImprinter) {
                ((IFilterable)tileEntity).setFilter(player.getCurrentEquippedItem());
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onSneakUseWrench(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileEntityFilterable) {
            ((TileEntityFilterable)tileEntity).toggleInversion();
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            world.markBlockForUpdate(x, y, z);
        }
        return true;
    }

    @Override
    public boolean onSneakMachineActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return this.onMachineActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    public void onNeighborBlockChange(World par1World, int x, int y, int z, Block side) {
        super.onNeighborBlockChange(par1World, x, y, z, side);
        TileEntity tileEntity = par1World.getTileEntity(x, y, z);
        if (tileEntity instanceof IRedstoneReceptor && par1World.isBlockIndirectlyGettingPowered(x, y, z)) {
            ((IRedstoneReceptor)par1World.getTileEntity(x, y, z)).onPowerOn();
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack stack) {
        int angle = MathHelper.floor_double((double)((double)(par5EntityLiving.rotationYaw * 4.0f / 360.0f) + 0.5)) & 3;
        int change = 2;
        switch (angle) {
            case 0: {
                change = 2;
                break;
            }
            case 1: {
                change = 5;
                break;
            }
            case 2: {
                change = 3;
                break;
            }
            case 3: {
                change = 4;
            }
        }
        world.setBlockMetadataWithNotify(x, y, z, change, 3);
    }

    @Override
    public boolean onUseWrench(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        int original = world.getBlockMetadata(x, y, z);
        int change = 2;
        switch (original) {
            case 2: {
                change = 4;
                break;
            }
            case 3: {
                change = 5;
                break;
            }
            case 4: {
                change = 3;
                break;
            }
            case 5: {
                change = 2;
            }
        }
        world.setBlockMetadataWithNotify(x, y, z, change, 3);
        return true;
    }
}

