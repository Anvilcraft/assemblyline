package assemblyline.common.block;

import java.util.Random;

import assemblyline.common.TabAssemblyLine;
import assemblyline.common.block.BlockALMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.implement.IRotatable;

public class BlockTurntable extends BlockALMachine {
    private IIcon top;

    public BlockTurntable() {
        super(Material.piston);
        this.setBlockName("turntable");
        this.setCreativeTab(TabAssemblyLine.INSTANCE);
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg) {
        super.registerBlockIcons(iconReg);
        this.top = iconReg.registerIcon("assemblyline:turntable");
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random par5Random) {
        this.updateTurntableState(world, x, y, z);
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        if (side == ForgeDirection.UP.ordinal()) {
            return this.top;
        }
        return this.machine_icon;
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == ForgeDirection.UP.ordinal()) {
            return this.top;
        }
        return this.machine_icon;
    }

    public static int
    determineOrientation(World world, int x, int y, int z, EntityPlayer entityPlayer) {
        int var7;
        if (MathHelper.abs((float) ((float) entityPlayer.posX - (float) x)) < 2.0f
            && MathHelper.abs((float) ((float) entityPlayer.posZ - (float) z)) < 2.0f) {
            double var5 = entityPlayer.posY + 1.82 - (double) entityPlayer.yOffset;
            if (var5 - (double) y > 2.0) {
                return 1;
            }
            if ((double) y - var5 > 0.0) {
                return 0;
            }
        }
        return (var7 = MathHelper.floor_double((double
                       ) ((double) (entityPlayer.rotationYaw * 4.0f / 360.0f) + 0.5))
                    & 3)
                == 0
            ? 2
            : (var7 == 1 ? 5 : (var7 == 2 ? 3 : (var7 == 3 ? 4 : 0)));
    }

    @Override
    public void onBlockPlacedBy(
        World world,
        int x,
        int y,
        int z,
        EntityLivingBase par5EntityLiving,
        ItemStack stack
    ) {
        int metadata = BlockTurntable.determineOrientation(
            world, x, y, z, (EntityPlayer) par5EntityLiving
        );
        world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
        world.scheduleBlockUpdate(x, y, z, this, 20);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        world.scheduleBlockUpdate(x, y, z, this, 20);
    }

    private void updateTurntableState(World world, int x, int y, int z) {
        if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
            try {
                Vector3 position = new Vector3(x, y, z);
                position.modifyPositionFromSide(ForgeDirection.UP);
                IRotatable rotatable = null;
                TileEntity tileEntity = position.getTileEntity((IBlockAccess) world);
                Block block = position.getBlock((IBlockAccess) world);
                if (tileEntity instanceof IRotatable) {
                    rotatable = (IRotatable) tileEntity;
                } else if (block instanceof IRotatable) {
                    rotatable = (IRotatable) block;
                }
                if (rotatable != null) {
                    ForgeDirection newDir
                        = ((IRotatable) tileEntity)
                              .getDirection((IBlockAccess) world, x, y, z);
                    switch (newDir) {
                        case EAST:
                            newDir = ForgeDirection.SOUTH;
                            break;
                        case NORTH:
                            newDir = ForgeDirection.EAST;
                            break;
                        case SOUTH:
                            newDir = ForgeDirection.WEST;
                            break;
                        case WEST:
                            newDir = ForgeDirection.NORTH;
                            break;
                        default:
                            break;
                    }
                    rotatable.setDirection(world, x, y, z, newDir);
                    world.markBlockForUpdate(
                        position.intX(), position.intY(), position.intZ()
                    );
                    world.playSoundEffect(
                        (double) x + 0.5,
                        (double) y + 0.5,
                        (double) z + 0.5,
                        "tile.piston.in",
                        0.5f,
                        world.rand.nextFloat() * 0.15f + 0.6f
                    );
                }
            } catch (Exception e) {
                System.out.println("Failed to rotate:");
                e.printStackTrace();
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World arg0, int arg1) {
        return null;
    }
}
