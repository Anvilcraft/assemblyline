package assemblyline.common.machine.imprinter;

import java.util.Random;

import assemblyline.common.AssemblyLine;
import assemblyline.common.TabAssemblyLine;
import assemblyline.common.block.BlockALMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockImprinter extends BlockALMachine {
    IIcon imprinter_side;
    IIcon imprinter_top;
    IIcon imprinter_bottom;

    public BlockImprinter() {
        super(Material.wood);
        this.setBlockName("imprinter");
        this.setCreativeTab(TabAssemblyLine.INSTANCE);
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg) {
        this.imprinter_side = iconReg.registerIcon("assemblyline:imprinter_side");
        this.imprinter_top = iconReg.registerIcon("assemblyline:imprinter_top");
        this.imprinter_bottom = iconReg.registerIcon("assemblyline:imprinter_bottom");
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return this.getIcon(side, 0);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 1) {
            return this.imprinter_top;
        }
        if (side == 0) {
            return this.imprinter_bottom;
        }
        return this.imprinter_side;
    }

    @Override
    public boolean onMachineActivated(
        World world,
        int x,
        int y,
        int z,
        EntityPlayer entityPlayer,
        int par6,
        float par7,
        float par8,
        float par9
    ) {
        if (!world.isRemote) {
            entityPlayer.openGui((Object) AssemblyLine.instance, 1, world, x, y, z);
        }
        return true;
    }

    @Override
    public void
    dropEntireInventory(World par1World, int x, int y, int z, Block par5, int par6) {
        TileEntity tileEntity = par1World.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileEntityImprinter) {
            TileEntityImprinter inventory = (TileEntityImprinter) tileEntity;
            for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = inventory.getStackInSlot(i);
                if (itemStack == null)
                    continue;
                Random random = new Random();
                float var8 = random.nextFloat() * 0.8f + 0.1f;
                float var9 = random.nextFloat() * 0.8f + 0.1f;
                float var10 = random.nextFloat() * 0.8f + 0.1f;
                while (itemStack.stackSize > 0) {
                    int var11 = random.nextInt(21) + 10;
                    if (var11 > itemStack.stackSize) {
                        var11 = itemStack.stackSize;
                    }
                    itemStack.stackSize -= var11;
                    if (i == inventory.imprinterMatrix.length + 9 - 1)
                        continue;
                    EntityItem entityItem = new EntityItem(
                        par1World,
                        (double) ((float) x + var8),
                        (double) ((float) y + var9),
                        (double) ((float) z + var10),
                        new ItemStack(
                            itemStack.getItem(), var11, itemStack.getItemDamage()
                        )
                    );
                    if (itemStack.hasTagCompound()) {
                        entityItem.getEntityItem().setTagCompound(
                            (NBTTagCompound) itemStack.getTagCompound().copy()
                        );
                    }
                    float var13 = 0.05f;
                    entityItem.motionX = (float) random.nextGaussian() * var13;
                    entityItem.motionY = (float) random.nextGaussian() * var13 + 0.2f;
                    entityItem.motionZ = (float) random.nextGaussian() * var13;
                    par1World.spawnEntityInWorld((Entity) entityItem);
                }
            }
        }
    }

    @Override
    public boolean onUseWrench(
        World par1World,
        int x,
        int y,
        int z,
        EntityPlayer par5EntityPlayer,
        int side,
        float hitX,
        float hitY,
        float hitZ
    ) {
        TileEntity tileEntity = par1World.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityImprinter) {
            ((TileEntityImprinter) tileEntity).searchInventories
                = !((TileEntityImprinter) tileEntity).searchInventories;
            par1World.markBlockForUpdate(x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TileEntityImprinter();
    }
}
