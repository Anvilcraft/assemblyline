package assemblyline.common.machine.belt;

import java.util.ArrayList;
import java.util.List;

import assemblyline.api.IBelt;
import assemblyline.common.AssemblyLine;
import assemblyline.common.machine.TileEntityAssemblyNetwork;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.prefab.implement.IRotatable;

public class TileEntityConveyorBelt
    extends TileEntityAssemblyNetwork implements IBelt, IRotatable {
    public static final int MAX_FRAME = 13;
    public static final int MAX_SLANT_FRAME = 23;
    public final float acceleration = 0.01f;
    public final float maxSpeed = 0.1f;
    public float wheelRotation = 0.0f;
    private int animFrame = 0;
    private SlantType slantType = SlantType.NONE;
    public List<Entity> IgnoreList = new ArrayList<>();

    @Override
    public void updatePowerTransferRange() {
        ForgeDirection direction;
        int maximumTransferRange = 0;
        for (int i = 0; i < 6; ++i) {
            direction = ForgeDirection.getOrientation((int) i);
            TileEntity tileEntity = this.worldObj.getTileEntity(
                this.xCoord + direction.offsetX,
                this.yCoord + direction.offsetY,
                this.zCoord + direction.offsetZ
            );
            if (tileEntity == null || !(tileEntity instanceof TileEntityAssemblyNetwork))
                continue;
            TileEntityAssemblyNetwork assemblyNetwork
                = (TileEntityAssemblyNetwork) tileEntity;
            if (assemblyNetwork.powerTransferRange <= maximumTransferRange)
                continue;
            maximumTransferRange = assemblyNetwork.powerTransferRange;
        }
        for (int d = 0; d <= 1; ++d) {
            direction = this.getDirection();
            if (d == 1) {
                direction = direction.getOpposite();
            }
            for (int i = -1; i <= 1; ++i) {
                TileEntity tileEntity = this.worldObj.getTileEntity(
                    this.xCoord + direction.offsetX,
                    this.yCoord + i,
                    this.zCoord + direction.offsetZ
                );
                if (tileEntity == null
                    || !(tileEntity instanceof TileEntityAssemblyNetwork))
                    continue;
                TileEntityAssemblyNetwork assemblyNetwork
                    = (TileEntityAssemblyNetwork) tileEntity;
                if (assemblyNetwork.powerTransferRange <= maximumTransferRange)
                    continue;
                maximumTransferRange = assemblyNetwork.powerTransferRange;
            }
        }
        this.powerTransferRange = Math.max(maximumTransferRange - 1, 0);
    }

    @Override
    public void onUpdate() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER
            && this.ticks % 10L == 0L) {
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        ArrayList<Entity> newList = new ArrayList<Entity>();
        for (Entity ent : this.IgnoreList) {
            if (!this.getAffectedEntities().contains((Object) ent))
                continue;
            newList.add(ent);
        }
        this.IgnoreList = newList;
        if (this.isRunning()
            && !this.worldObj.isBlockIndirectlyGettingPowered(
                this.xCoord, this.yCoord, this.zCoord
            )) {
            if (this.ticks % 10L == 0L && this.worldObj.isRemote
                && this.worldObj.getBlock(this.xCoord - 1, this.yCoord, this.zCoord)
                    != AssemblyLine.blockConveyorBelt
                && this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord - 1)
                    != AssemblyLine.blockConveyorBelt) {
                this.worldObj.playSound(
                    (double) this.xCoord,
                    (double) this.yCoord,
                    (double) this.zCoord,
                    "assemblyline:conveyor",
                    0.5f,
                    0.7f,
                    true
                );
            }
            this.wheelRotation += 40.0f;
            if (this.wheelRotation > 360.0f) {
                this.wheelRotation = 0.0f;
            }
            float wheelRotPct = this.wheelRotation / 360.0f;
            if (this.getSlant() == SlantType.NONE || this.getSlant() == SlantType.TOP) {
                this.animFrame = (int) (wheelRotPct * 13.0f);
                if (this.animFrame < 0) {
                    this.animFrame = 0;
                }
                if (this.animFrame > 13) {
                    this.animFrame = 13;
                }
            } else {
                this.animFrame = (int) (wheelRotPct * 23.0f);
                if (this.animFrame < 0) {
                    this.animFrame = 0;
                }
                if (this.animFrame > 23) {
                    this.animFrame = 23;
                }
            }
        }
    }

    @Override
    protected int getMaxTransferRange() {
        return 20;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setDouble("wattsReceived", this.wattsReceived);
        nbt.setInteger("slantType", this.slantType.ordinal());
        return new S35PacketUpdateTileEntity(
            xCoord, yCoord, zCoord, getBlockMetadata(), nbt
        );
    }

    public SlantType getSlant() {
        return this.slantType;
    }

    public void setSlant(SlantType slantType) {
        if (slantType == null) {
            slantType = SlantType.NONE;
        }
        this.slantType = slantType;
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public boolean getIsFirstBelt() {
        ForgeDirection front = this.getDirection();
        ForgeDirection back = this.getDirection().getOpposite();
        TileEntity fBelt = this.worldObj.getTileEntity(
            this.xCoord + front.offsetX,
            this.yCoord + front.offsetY,
            this.zCoord + front.offsetZ
        );
        TileEntity BBelt = this.worldObj.getTileEntity(
            this.xCoord + back.offsetX,
            this.yCoord + back.offsetY,
            this.zCoord + back.offsetZ
        );
        if (fBelt instanceof TileEntityConveyorBelt) {
            ForgeDirection TD;
            ForgeDirection fD = ((TileEntityConveyorBelt) fBelt).getDirection();
            return fD == (TD = this.getDirection());
        }
        return false;
    }

    public boolean getIsMiddleBelt() {
        ForgeDirection front = this.getDirection();
        ForgeDirection back = this.getDirection().getOpposite();
        TileEntity fBelt = this.worldObj.getTileEntity(
            this.xCoord + front.offsetX,
            this.yCoord + front.offsetY,
            this.zCoord + front.offsetZ
        );
        TileEntity BBelt = this.worldObj.getTileEntity(
            this.xCoord + back.offsetX,
            this.yCoord + back.offsetY,
            this.zCoord + back.offsetZ
        );
        if (fBelt instanceof TileEntityConveyorBelt
            && BBelt instanceof TileEntityConveyorBelt) {
            ForgeDirection fD = ((TileEntityConveyorBelt) fBelt).getDirection();
            ForgeDirection BD = ((TileEntityConveyorBelt) BBelt).getDirection();
            ForgeDirection TD = this.getDirection();
            return fD == TD && BD == TD;
        }
        return false;
    }

    public boolean getIsLastBelt() {
        ForgeDirection front = this.getDirection();
        ForgeDirection back = this.getDirection().getOpposite();
        TileEntity fBelt = this.worldObj.getTileEntity(
            this.xCoord + front.offsetX,
            this.yCoord + front.offsetY,
            this.zCoord + front.offsetZ
        );
        TileEntity BBelt = this.worldObj.getTileEntity(
            this.xCoord + back.offsetX,
            this.yCoord + back.offsetY,
            this.zCoord + back.offsetZ
        );
        if (BBelt instanceof TileEntityConveyorBelt) {
            ForgeDirection TD;
            ForgeDirection BD = ((TileEntityConveyorBelt) BBelt).getDirection();
            return BD == (TD = this.getDirection());
        }
        return false;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (this.worldObj.isRemote) {
            NBTTagCompound nbt = pkt.func_148857_g();
            this.wattsReceived = nbt.getDouble("wattsReceived");
            this.slantType = SlantType.values()[nbt.getInteger("slantType")];
        }
    }

    @Override
    public void
    setDirection(World world, int x, int y, int z, ForgeDirection facingDirection) {
        this.worldObj.setBlockMetadataWithNotify(
            this.xCoord, this.yCoord, this.zCoord, facingDirection.ordinal(), 3
        );
    }

    @Override
    public ForgeDirection getDirection(IBlockAccess world, int x, int y, int z) {
        return ForgeDirection.getOrientation((int) this.getBlockMetadata());
    }

    public ForgeDirection getDirection() {
        return this.getDirection(
            (IBlockAccess) this.worldObj, this.xCoord, this.yCoord, this.zCoord
        );
    }

    public void setDirection(ForgeDirection facingDirection) {
        this.setDirection(
            this.worldObj, this.xCoord, this.yCoord, this.zCoord, facingDirection
        );
    }

    @Override
    public List getAffectedEntities() {
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(
            (double) this.xCoord,
            (double) this.yCoord,
            (double) this.zCoord,
            (double) (this.xCoord + 1),
            (double) (this.yCoord + 1),
            (double) (this.zCoord + 1)
        );
        return this.worldObj.getEntitiesWithinAABB(Entity.class, bounds);
    }

    public int getAnimationFrame() {
        if (!this.worldObj.isBlockIndirectlyGettingPowered(
                this.xCoord, this.yCoord, this.zCoord
            )) {
            TileEntity te = null;
            te = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
            if (te != null && te instanceof TileEntityConveyorBelt
                && ((TileEntityConveyorBelt) te).getSlant() == this.slantType) {
                return ((TileEntityConveyorBelt) te).getAnimationFrame();
            }
            te = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
            if (te != null && te instanceof TileEntityConveyorBelt
                && ((TileEntityConveyorBelt) te).getSlant() == this.slantType) {
                return ((TileEntityConveyorBelt) te).getAnimationFrame();
            }
        }
        return this.animFrame;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.slantType = SlantType.values()[nbt.getByte("slant")];
        if (this.worldObj != null) {
            this.worldObj.setBlockMetadataWithNotify(
                this.xCoord, this.yCoord, this.zCoord, nbt.getInteger("rotation"), 3
            );
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setByte("slant", (byte) this.slantType.ordinal());
        if (this.worldObj != null) {
            nbt.setInteger(
                "rotation",
                this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)
            );
        }
    }

    @Override
    public void IgnoreEntity(Entity entity) {
        if (!this.IgnoreList.contains((Object) entity)) {
            this.IgnoreList.add(entity);
        }
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return direction == ForgeDirection.DOWN;
    }

    public static enum SlantType {
        NONE,
        UP,
        DOWN,
        TOP;
    }
}
