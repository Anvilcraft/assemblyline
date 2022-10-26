package assemblyline.common.machine;

import assemblyline.client.render.BlockRenderingHandler;
import assemblyline.common.TabAssemblyLine;
import assemblyline.common.machine.imprinter.BlockImprintable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import universalelectricity.core.UniversalElectricity;

public class BlockManipulator
extends BlockImprintable {
    public BlockManipulator() {
        super("manipulator", UniversalElectricity.machine, TabAssemblyLine.INSTANCE);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.29f, 1.0f);
    }

    public AxisAlignedBB func_71911_a_(World par1World, int par2, int par3, int par4) {
        return AxisAlignedBB.getBoundingBox((double)par2, (double)par3, (double)par4, (double)par2 + 1.0, (double)par3 + 1.0, (double)par4 + 1.0);
    }

    @Override
    public boolean onSneakMachineActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityManipulator) {
            ((TileEntityManipulator)tileEntity).selfPulse = !((TileEntityManipulator)tileEntity).selfPulse;
        }
        return true;
    }

    @Override
    public boolean onSneakUseWrench(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityManipulator) {
            TileEntityManipulator manip = (TileEntityManipulator)tileEntity;
            boolean manipMode = manip.isOutput();
            boolean inverted = manip.isInverted();
            if (manipMode && !inverted) {
                manip.toggleInversion();
            } else if (manipMode && inverted) {
                manip.toggleOutput();
                manip.toggleInversion();
            } else if (!manipMode && !inverted) {
                manip.toggleInversion();
            } else {
                manip.toggleOutput();
                manip.toggleInversion();
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int metadata) {
        return new TileEntityManipulator();
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public int getRenderType() {
        return BlockRenderingHandler.BLOCK_RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int damageDropped(int par1) {
        return 0;
    }
}

