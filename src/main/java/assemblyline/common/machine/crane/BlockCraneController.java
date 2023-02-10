package assemblyline.common.machine.crane;

import assemblyline.client.render.BlockRenderingHandler;
import assemblyline.common.block.BlockALMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.UniversalElectricity;

public class BlockCraneController extends BlockALMachine {
    public BlockCraneController() {
        super(UniversalElectricity.machine);
        this.setBlockName("craneController");
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
    public void onBlockPlacedBy(
        World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack
    ) {
        int rot = (int) Math.min((entity.rotationYaw + 315.0f) % 360.0f / 90.0f, 3.0f);
        switch (rot) {
            case 0: {
                world.setBlockMetadataWithNotify(
                    x, y, z, ForgeDirection.WEST.ordinal(), 3
                );
                break;
            }
            case 1: {
                world.setBlockMetadataWithNotify(
                    x, y, z, ForgeDirection.NORTH.ordinal(), 3
                );
                break;
            }
            case 2: {
                world.setBlockMetadataWithNotify(
                    x, y, z, ForgeDirection.EAST.ordinal(), 3
                );
                break;
            }
            default: {
                world.setBlockMetadataWithNotify(
                    x, y, z, ForgeDirection.SOUTH.ordinal(), 3
                );
            }
        }
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public int getRenderType() {
        return BlockRenderingHandler.BLOCK_RENDER_ID;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCraneController();
    }
}
