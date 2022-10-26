package assemblyline.common.machine.detector;

import assemblyline.common.TabAssemblyLine;
import assemblyline.common.machine.detector.TileEntityDetector;
import assemblyline.common.machine.imprinter.BlockImprintable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.UniversalElectricity;

public class BlockDetector
extends BlockImprintable {
    IIcon eye_red;
    IIcon eye_green;

    public BlockDetector() {
        super("detector", UniversalElectricity.machine, TabAssemblyLine.INSTANCE);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int angle = MathHelper.floor_double((double)((double)(entity.rotationYaw * 4.0f / 360.0f) + 0.5)) & 3;
        int change = 2;
        switch (angle) {
            case 0: {
                change = ForgeDirection.NORTH.ordinal();
                break;
            }
            case 1: {
                change = ForgeDirection.EAST.ordinal();
                break;
            }
            case 2: {
                change = ForgeDirection.SOUTH.ordinal();
                break;
            }
            case 3: {
                change = ForgeDirection.WEST.ordinal();
            }
        }
        if (entity.rotationPitch < -70.0f) {
            change = ForgeDirection.DOWN.ordinal();
        }
        if (entity.rotationPitch > 70.0f) {
            change = ForgeDirection.UP.ordinal();
        }
        world.setBlockMetadataWithNotify(x, y, z, change, 3);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg) {
        super.registerBlockIcons(iconReg);
        this.eye_green = iconReg.registerIcon("assemblyline:detector_green");
        this.eye_red = iconReg.registerIcon("assemblyline:detector_red");
    }

    @Override
    public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
        TileEntity tileEntity = iBlockAccess.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityDetector && side == ForgeDirection.getOrientation((int)iBlockAccess.getBlockMetadata(x, y, z)).ordinal()) {
            if (((TileEntityDetector)tileEntity).isInverted()) {
                return this.eye_red;
            }
            return this.eye_green;
        }
        return this.machine_icon;
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        if (side == ForgeDirection.SOUTH.ordinal()) {
            return this.eye_green;
        }
        return this.machine_icon;
    }

    @Override
    public boolean onUseWrench(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        world.setBlockMetadataWithNotify(x, y, z, side, 3);
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block blockID) {
        if (!this.canBlockStay(world, x, y, z)) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlock(x, y, z, Blocks.air, 0, 3);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isBlockNormalCube(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
        return false;
    }

    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int direction) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityDetector) {
            return ((TileEntityDetector)tileEntity).isPoweringTo(ForgeDirection.getOrientation((int)direction));
        }
        return 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int direction) {
        return this.isProvidingStrongPower(world, x, y, z, direction);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityDetector();
    }
}

