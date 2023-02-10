package assemblyline.common.machine.detector;

import java.util.ArrayList;

import assemblyline.common.AssemblyLine;
import assemblyline.common.machine.imprinter.TileEntityFilterable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDetector extends TileEntityFilterable {
    private boolean powering = false;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote && this.ticks % 10L == 0L) {
            int metadata
                = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
            AxisAlignedBB testArea = AxisAlignedBB.getBoundingBox(
                (double) this.xCoord,
                (double) this.yCoord,
                (double) this.zCoord,
                (double) (this.xCoord + 1),
                (double) (this.yCoord + 1),
                (double) (this.zCoord + 1)
            );
            ForgeDirection dir = ForgeDirection.getOrientation((int) metadata);
            testArea.offset(
                (double) dir.offsetX, (double) dir.offsetY, (double) dir.offsetZ
            );
            ArrayList entities = (ArrayList
            ) this.worldObj.getEntitiesWithinAABB(EntityItem.class, testArea);
            boolean powerCheck = false;
            if (entities.size() > 0) {
                if (this.getFilter() != null) {
                    for (int i = 0; i < entities.size(); ++i) {
                        EntityItem e = (EntityItem) entities.get(i);
                        ItemStack itemStack = e.getEntityItem();
                        powerCheck = this.isFiltering(itemStack);
                    }
                } else {
                    powerCheck = true;
                }
            } else {
                powerCheck = false;
            }
            if (powerCheck != this.powering) {
                this.powering = powerCheck;
                this.worldObj.notifyBlocksOfNeighborChange(
                    this.xCoord, this.yCoord, this.zCoord, AssemblyLine.blockDetector
                );
                this.worldObj.notifyBlocksOfNeighborChange(
                    this.xCoord, this.yCoord + 1, this.zCoord, AssemblyLine.blockDetector
                );
                for (int x = this.xCoord - 1; x <= this.xCoord + 1; ++x) {
                    for (int z = this.zCoord - 1; z <= this.zCoord + 1; ++z) {
                        this.worldObj.notifyBlocksOfNeighborChange(
                            x, this.yCoord + 1, z, AssemblyLine.blockDetector
                        );
                    }
                }
                this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    @Override
    public void invalidate() {
        this.worldObj.notifyBlocksOfNeighborChange(
            this.xCoord, this.yCoord, this.zCoord, AssemblyLine.blockDetector
        );
        this.worldObj.notifyBlocksOfNeighborChange(
            this.xCoord, this.yCoord + 1, this.zCoord, AssemblyLine.blockDetector
        );
        super.invalidate();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.powering = tag.getBoolean("powering");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setBoolean("powering", this.powering);
    }

    public int isPoweringTo(ForgeDirection side) {
        return this.powering && this.getDirection() != side.getOpposite() ? 15 : 0;
    }

    public boolean isIndirectlyPoweringTo(ForgeDirection side) {
        return this.isPoweringTo(side) > 0;
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return direction != this.getDirection();
    }
}
