package assemblyline.common.machine.crane;

import assemblyline.client.render.BlockRenderingHandler;
import assemblyline.common.block.BlockALMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.UniversalElectricity;

public class BlockCraneFrame extends BlockALMachine {
    public BlockCraneFrame() {
        super(UniversalElectricity.machine);
        this.setBlockName("craneFrame");
        this.setBlockBounds(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
    }

    @Override
    public AxisAlignedBB
    getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        TileEntity tE = world.getTileEntity(x, y, z);
        if (tE != null && tE instanceof TileEntityCraneRail) {
            AxisAlignedBB middle = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.25,
                (double) 0.25,
                (double) 0.75,
                (double) 0.75,
                (double) 0.75
            );
            AxisAlignedBB up = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.75,
                (double) 0.25,
                (double) 0.75,
                (double) 1.0,
                (double) 0.75
            );
            AxisAlignedBB down = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.0,
                (double) 0.25,
                (double) 0.75,
                (double) 0.25,
                (double) 0.75
            );
            AxisAlignedBB left = AxisAlignedBB.getBoundingBox(
                (double) 0.0,
                (double) 0.25,
                (double) 0.25,
                (double) 0.25,
                (double) 0.75,
                (double) 0.75
            );
            AxisAlignedBB right = AxisAlignedBB.getBoundingBox(
                (double) 0.75,
                (double) 0.25,
                (double) 0.25,
                (double) 1.0,
                (double) 0.75,
                (double) 0.75
            );
            AxisAlignedBB front = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.25,
                (double) 0.0,
                (double) 0.75,
                (double) 0.75,
                (double) 0.25
            );
            AxisAlignedBB back = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.25,
                (double) 0.75,
                (double) 0.75,
                (double) 0.75,
                (double) 1.0
            );
            boolean connectUp
                = CraneHelper.canFrameConnectTo(tE, x, y + 1, z, ForgeDirection.DOWN);
            boolean connectDown
                = CraneHelper.canFrameConnectTo(tE, x, y - 1, z, ForgeDirection.UP);
            boolean connectLeft
                = CraneHelper.canFrameConnectTo(tE, x - 1, y, z, ForgeDirection.EAST);
            boolean connectRight
                = CraneHelper.canFrameConnectTo(tE, x + 1, y, z, ForgeDirection.WEST);
            boolean connectFront
                = CraneHelper.canFrameConnectTo(tE, x, y, z - 1, ForgeDirection.SOUTH);
            boolean connectBack
                = CraneHelper.canFrameConnectTo(tE, x, y, z + 1, ForgeDirection.NORTH);
            if (connectUp) {
                middle.maxY = up.maxY;
            }
            if (connectDown) {
                middle.minY = down.minY;
            }
            if (connectLeft) {
                middle.minX = left.minX;
            }
            if (connectRight) {
                middle.maxX = right.maxX;
            }
            if (connectFront) {
                middle.minZ = front.minZ;
            }
            if (connectBack) {
                middle.maxZ = back.maxZ;
            }
            this.setBlockBounds(
                (float) middle.minX,
                (float) middle.minY,
                (float) middle.minZ,
                (float) middle.maxX,
                (float) middle.maxY,
                (float) middle.maxZ
            );
            middle.offset((double) x, (double) y, (double) z);
            return middle;
        }
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        TileEntity tE = world.getTileEntity(x, y, z);
        if (tE != null && tE instanceof TileEntityCraneRail) {
            AxisAlignedBB middle = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.25,
                (double) 0.25,
                (double) 0.75,
                (double) 0.75,
                (double) 0.75
            );
            AxisAlignedBB up = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.75,
                (double) 0.25,
                (double) 0.75,
                (double) 1.0,
                (double) 0.75
            );
            AxisAlignedBB down = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.0,
                (double) 0.25,
                (double) 0.75,
                (double) 0.25,
                (double) 0.75
            );
            AxisAlignedBB left = AxisAlignedBB.getBoundingBox(
                (double) 0.0,
                (double) 0.25,
                (double) 0.25,
                (double) 0.25,
                (double) 0.75,
                (double) 0.75
            );
            AxisAlignedBB right = AxisAlignedBB.getBoundingBox(
                (double) 0.75,
                (double) 0.25,
                (double) 0.25,
                (double) 1.0,
                (double) 0.75,
                (double) 0.75
            );
            AxisAlignedBB front = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.25,
                (double) 0.0,
                (double) 0.75,
                (double) 0.75,
                (double) 0.25
            );
            AxisAlignedBB back = AxisAlignedBB.getBoundingBox(
                (double) 0.25,
                (double) 0.25,
                (double) 0.75,
                (double) 0.75,
                (double) 0.75,
                (double) 1.0
            );
            boolean connectUp
                = CraneHelper.canFrameConnectTo(tE, x, y + 1, z, ForgeDirection.DOWN);
            boolean connectDown
                = CraneHelper.canFrameConnectTo(tE, x, y - 1, z, ForgeDirection.UP);
            boolean connectLeft
                = CraneHelper.canFrameConnectTo(tE, x - 1, y, z, ForgeDirection.EAST);
            boolean connectRight
                = CraneHelper.canFrameConnectTo(tE, x + 1, y, z, ForgeDirection.WEST);
            boolean connectFront
                = CraneHelper.canFrameConnectTo(tE, x, y, z - 1, ForgeDirection.SOUTH);
            boolean connectBack
                = CraneHelper.canFrameConnectTo(tE, x, y, z + 1, ForgeDirection.NORTH);
            if (connectUp) {
                middle.maxY = up.maxY;
            }
            if (connectDown) {
                middle.minY = down.minY;
            }
            if (connectLeft) {
                middle.minX = left.minX;
            }
            if (connectRight) {
                middle.maxX = right.maxX;
            }
            if (connectFront) {
                middle.minZ = front.minZ;
            }
            if (connectBack) {
                middle.maxZ = back.maxZ;
            }
            this.setBlockBounds(
                (float) middle.minX,
                (float) middle.minY,
                (float) middle.minZ,
                (float) middle.maxX,
                (float) middle.maxY,
                (float) middle.maxZ
            );
            return;
        }
        this.setBlockBounds(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCraneRail();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public int getRenderType() {
        return BlockRenderingHandler.BLOCK_RENDER_ID;
    }
}
