package assemblyline.common.machine.crane;

import assemblyline.api.ICraneConnectable;
import assemblyline.common.machine.TileEntityAssemblyNetwork;
import assemblyline.common.machine.crane.CraneHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCraneController
    extends TileEntityAssemblyNetwork implements ICraneConnectable {
    int width = 0;
    int height = 0;
    int depth = 0;
    boolean isCraneValid = false;
    long ticks = 0L;

    @Override
    public void updateEntity() {
        ++this.ticks;
        if (this.ticks % 20L == 0L) {
            this.validateCrane();
        }
    }

    public boolean isCraneValid() {
        return this.isCraneValid;
    }

    private void validateCrane() {
        this.isCraneValid = false;
        this.depth = 0;
        this.height = 0;
        this.width = 0;
        this.findCraneHeight();
        this.findCraneWidth();
        this.findCraneDepth();
        if (Math.abs(this.height) > 1 && Math.abs(this.width) > 1
            && Math.abs(this.depth) > 1) {
            this.isCraneValid = this.isFrameValid();
        }
    }

    private boolean isFrameValid() {
        int z;
        int x;
        for (x = Math.min(0, this.width); x <= Math.max(0, this.width); ++x) {
            if (CraneHelper.isCraneStructureBlock(
                    this.worldObj, this.xCoord + x, this.yCoord + this.height, this.zCoord
                ))
                continue;
            return false;
        }
        for (x = Math.min(0, this.width); x <= Math.max(0, this.width); ++x) {
            if (CraneHelper.isCraneStructureBlock(
                    this.worldObj,
                    this.xCoord + x,
                    this.yCoord + this.height,
                    this.zCoord + this.depth
                ))
                continue;
            return false;
        }
        for (z = Math.min(0, this.depth); z <= Math.max(0, this.depth); ++z) {
            if (CraneHelper.isCraneStructureBlock(
                    this.worldObj, this.xCoord, this.yCoord + this.height, this.zCoord + z
                ))
                continue;
            return false;
        }
        for (z = Math.min(0, this.depth); z <= Math.max(0, this.depth); ++z) {
            if (CraneHelper.isCraneStructureBlock(
                    this.worldObj,
                    this.xCoord + this.width,
                    this.yCoord + this.height,
                    this.zCoord + z
                ))
                continue;
            return false;
        }
        for (x = Math.min(this.width + 1, 1); x <= Math.max(-1, this.width - 1); ++x) {
            for (int z2 = Math.min(this.depth + 1, 1); z2 <= Math.max(-1, this.depth - 1);
                 ++z2) {
                if (this.worldObj.isAirBlock(
                        this.xCoord + x, this.yCoord + this.height, this.zCoord + z2
                    ))
                    continue;
                return false;
            }
        }
        return true;
    }

    private void findCraneWidth() {
        if (this.height == 0) {
            this.width = 0;
            return;
        }
        int x = 0;
        ForgeDirection facing = ForgeDirection.getOrientation((int
        ) this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
        while (Math.abs(x) <= 64
               && CraneHelper.isCraneStructureBlock(
                   this.worldObj, this.xCoord + x, this.yCoord + this.height, this.zCoord
               )) {
            if (facing == ForgeDirection.NORTH || facing == ForgeDirection.EAST) {
                ++x;
                continue;
            }
            --x;
        }
        this.width = x;
        if (this.width < 0) {
            ++this.width;
        }
        if (this.width > 0) {
            --this.width;
        }
    }

    private void findCraneHeight() {
        int y;
        for (y = 1; this.yCoord + y < 256 && y <= 64
             && CraneHelper.isCraneStructureBlock(
                 this.worldObj, this.xCoord, this.yCoord + y, this.zCoord
             );
             ++y) {}
        this.height = y - 1;
    }

    private void findCraneDepth() {
        if (this.height == 0) {
            this.width = 0;
            return;
        }
        int z = 0;
        ForgeDirection facing = ForgeDirection.getOrientation((int
        ) this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
        while (Math.abs(z) <= 64
               && CraneHelper.isCraneStructureBlock(
                   this.worldObj, this.xCoord, this.yCoord + this.height, this.zCoord + z
               )) {
            if (facing == ForgeDirection.SOUTH || facing == ForgeDirection.EAST) {
                ++z;
                continue;
            }
            --z;
        }
        this.depth = z;
        if (this.depth < 0) {
            ++this.depth;
        }
        if (this.depth > 0) {
            --this.depth;
        }
    }

    @Override
    public boolean canFrameConnectTo(ForgeDirection side) {
        ForgeDirection facing = ForgeDirection.getOrientation((int
        ) this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
        if (side == facing) {
            return true;
        }
        if (side == CraneHelper.rotateClockwise(facing)) {
            return true;
        }
        return side == ForgeDirection.UP;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("width", this.width);
        nbt.setInteger("height", this.height);
        nbt.setInteger("depth", this.depth);
        nbt.setBoolean("isValid", this.isCraneValid);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.width = nbt.getInteger("width");
        this.height = nbt.getInteger("height");
        this.depth = nbt.getInteger("depth");
        this.isCraneValid = nbt.getBoolean("isValid");
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return true;
    }
}
