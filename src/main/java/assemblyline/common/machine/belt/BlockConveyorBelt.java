package assemblyline.common.machine.belt;

import assemblyline.client.render.BlockRenderingHandler;
import assemblyline.common.TabAssemblyLine;
import assemblyline.common.block.BlockALMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.UniversalElectricity;

public class BlockConveyorBelt
extends BlockALMachine {
    public BlockConveyorBelt() {
        super(UniversalElectricity.machine);
        this.setBlockName("conveyorBelt");
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.3f, 1.0f);
        this.setCreativeTab(TabAssemblyLine.INSTANCE);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityConveyorBelt) {
            TileEntityConveyorBelt tileEntity = (TileEntityConveyorBelt)world.getTileEntity(x, y, z);
            if (tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.UP || tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.DOWN) {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.96f, 1.0f);
                return;
            }
            if (tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.TOP) {
                this.setBlockBounds(0.0f, 0.68f, 0.0f, 1.0f, 0.96f, 1.0f);
                return;
            }
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.3f, 1.0f);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        TileEntity t = world.getTileEntity(x, y, z);
        if (t != null && t instanceof TileEntityConveyorBelt) {
            TileEntityConveyorBelt tileEntity = (TileEntityConveyorBelt)t;
            if (tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.UP || tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.DOWN) {
                return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + 1.0, (double)y + 1.0, (double)z + 1.0);
            }
            if (tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.TOP) {
                return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + (double)0.68f, (double)z + this.minZ, (double)x + this.maxX, (double)y + (double)0.98f, (double)z + this.maxZ);
            }
        }
        return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
        AxisAlignedBB newBounds;
        TileEntity t = world.getTileEntity(x, y, z);
        if (t != null && t instanceof TileEntityConveyorBelt) {
            TileEntityConveyorBelt tileEntity = (TileEntityConveyorBelt)t;
            if (tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.UP || tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.DOWN) {
                AxisAlignedBB boundBottom = AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)y + 0.3, (double)(z + 1));
                AxisAlignedBB boundTop = null;
                ForgeDirection direction = tileEntity.getDirection();
                if (tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.UP) {
                    if (direction.offsetX > 0) {
                        boundTop = AxisAlignedBB.getBoundingBox((double)((float)x + (float)direction.offsetX / 2.0f), (double)y, (double)z, (double)(x + 1), (double)y + 0.8, (double)(z + 1));
                    } else if (direction.offsetX < 0) {
                        boundTop = AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)((float)x + (float)direction.offsetX / -2.0f), (double)y + 0.8, (double)(z + 1));
                    } else if (direction.offsetZ > 0) {
                        boundTop = AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)((float)z + (float)direction.offsetZ / 2.0f), (double)(x + 1), (double)y + 0.8, (double)(z + 1));
                    } else if (direction.offsetZ < 0) {
                        boundTop = AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)y + 0.8, (double)((float)z + (float)direction.offsetZ / -2.0f));
                    }
                } else if (tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.DOWN) {
                    if (direction.offsetX > 0) {
                        boundTop = AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)((float)x + (float)direction.offsetX / 2.0f), (double)y + 0.8, (double)(z + 1));
                    } else if (direction.offsetX < 0) {
                        boundTop = AxisAlignedBB.getBoundingBox((double)((float)x + (float)direction.offsetX / -2.0f), (double)y, (double)z, (double)(x + 1), (double)y + 0.8, (double)(z + 1));
                    } else if (direction.offsetZ > 0) {
                        boundTop = AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)y + 0.8, (double)((float)z + (float)direction.offsetZ / 2.0f));
                    } else if (direction.offsetZ < 0) {
                        boundTop = AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)((float)z + (float)direction.offsetZ / -2.0f), (double)(x + 1), (double)y + 0.8, (double)(z + 1));
                    }
                }
                if (par5AxisAlignedBB.intersectsWith(boundBottom)) {
                    par6List.add(boundBottom);
                }
                if (boundTop != null && par5AxisAlignedBB.intersectsWith(boundTop)) {
                    par6List.add(boundTop);
                }
                return;
            }
            if (tileEntity.getSlant() == TileEntityConveyorBelt.SlantType.TOP) {
                AxisAlignedBB newBounds2 = AxisAlignedBB.getBoundingBox((double)x, (double)y + 0.68, (double)z, (double)(x + 1), (double)y + 0.98, (double)(z + 1));
                if (newBounds2 != null && par5AxisAlignedBB.intersectsWith(newBounds2)) {
                    par6List.add(newBounds2);
                }
                return;
            }
        }
        if ((newBounds = AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)y + 0.3, (double)(z + 1))) != null && par5AxisAlignedBB.intersectsWith(newBounds)) {
            par6List.add(newBounds);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack stack) {
        int angle = MathHelper.floor_double((double)((double)(par5EntityLiving.rotationYaw * 4.0f / 360.0f) + 0.5)) & 3;
        int change = 2;
        switch (angle) {
            case 0: {
                change = 3;
                break;
            }
            case 1: {
                change = 4;
                break;
            }
            case 2: {
                change = 2;
                break;
            }
            case 3: {
                change = 5;
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

    @Override
    public boolean onSneakUseWrench(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        TileEntityConveyorBelt tileEntity = (TileEntityConveyorBelt)world.getTileEntity(x, y, z);
        int slantOrdinal = tileEntity.getSlant().ordinal() + 1;
        if (slantOrdinal >= TileEntityConveyorBelt.SlantType.values().length) {
            slantOrdinal = 0;
        }
        tileEntity.setSlant(TileEntityConveyorBelt.SlantType.values()[slantOrdinal]);
        return true;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        TileEntityConveyorBelt tileEntity = (TileEntityConveyorBelt)world.getTileEntity(x, y, z);
        tileEntity.updatePowerTransferRange();
        if (tileEntity.IgnoreList.contains((Object)entity)) {
            return;
        }
        if (tileEntity.isRunning() && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
            tileEntity.getClass();
            float acceleration = 0.01f;
            tileEntity.getClass();
            float maxSpeed = 0.1f;
            TileEntityConveyorBelt.SlantType slantType = tileEntity.getSlant();
            ForgeDirection direction = tileEntity.getDirection();
            if (entity instanceof EntityLiving) {
                acceleration *= 5.0f;
                maxSpeed *= 10.0f;
            }
            entity.addVelocity((double)((float)direction.offsetX * acceleration), 0.0, (double)((float)direction.offsetZ * acceleration));
            if (direction.offsetX != 0 && Math.abs(entity.motionX) > (double)maxSpeed) {
                entity.motionX = (float)direction.offsetX * maxSpeed;
                entity.motionZ = 0.0;
            }
            if (direction.offsetZ != 0 && Math.abs(entity.motionZ) > (double)maxSpeed) {
                entity.motionZ = (float)direction.offsetZ * maxSpeed;
                entity.motionX = 0.0;
            }
            entity.motionY += (double)0.0125f;
            if (entity instanceof EntityItem) {
                double difference;
                if (direction.offsetX != 0) {
                    difference = (double)z + 0.5 - entity.posZ;
                    entity.motionZ += difference * 0.1;
                } else if (direction.offsetZ != 0) {
                    difference = (double)x + 0.5 - entity.posX;
                    entity.motionX += difference * 0.1;
                }
                ++((EntityItem)entity).age;
                boolean foundSneaking = false;
                for (EntityPlayer player : (List<EntityPlayer>) world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)(x - 1), (double)(y - 1), (double)(z - 1), (double)(x + 1), (double)(y + 1), (double)(z + 1)))) {
                    if (!player.isSneaking()) continue;
                    foundSneaking = true;
                }
                ((EntityItem)entity).delayBeforeCanPickup = foundSneaking ? 0 : 20;
                entity.onGround = false;
            }
            if (slantType == TileEntityConveyorBelt.SlantType.UP) {
                if (entity.motionY < 0.4) {
                    entity.addVelocity(0.0, 0.4, 0.0);
                }
            } else if (slantType == TileEntityConveyorBelt.SlantType.DOWN && entity.motionY > -0.1) {
                entity.addVelocity(0.0, -0.1, 0.0);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TileEntityConveyorBelt();
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

