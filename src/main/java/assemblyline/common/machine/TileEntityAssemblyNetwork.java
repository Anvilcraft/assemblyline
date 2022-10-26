package assemblyline.common.machine;

import assemblyline.common.AssemblyLine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.prefab.tile.TileEntityElectricityRunnable;

public abstract class TileEntityAssemblyNetwork
extends TileEntityElectricityRunnable {
    public int powerTransferRange = 0;

    public boolean isRunning() {
        return AssemblyLine.REQUIRE_NO_POWER || this.powerTransferRange > 0 || this.wattsReceived > this.getRequest().getWatts();
    }

    public void updatePowerTransferRange() {
        int maximumTransferRange = 0;
        for (int i = 0; i < 6; ++i) {
            ForgeDirection direction = ForgeDirection.getOrientation((int)i);
            TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);
            if (tileEntity == null || !(tileEntity instanceof TileEntityAssemblyNetwork)) continue;
            TileEntityAssemblyNetwork assemblyNetwork = (TileEntityAssemblyNetwork)tileEntity;
            if (assemblyNetwork.powerTransferRange <= maximumTransferRange) continue;
            maximumTransferRange = assemblyNetwork.powerTransferRange;
        }
        this.powerTransferRange = Math.max(maximumTransferRange - 1, 0);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        this.onUpdate();
        if (this.ticks % 10L == 0L) {
            if (this.wattsReceived >= this.getRequest().getWatts()) {
                this.wattsReceived -= this.getRequest().getWatts();
                this.powerTransferRange = this.getMaxTransferRange();
            } else {
                this.powerTransferRange = 0;
                this.updatePowerTransferRange();
            }
            if (!this.worldObj.isRemote) {
                this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    protected void onUpdate() {
    }

    @Override
    public ElectricityPack getRequest() {
        return new ElectricityPack(1.0, this.getVoltage());
    }

    protected int getMaxTransferRange() {
        return 20;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setDouble("wattsReceived", this.wattsReceived);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.wattsReceived = nbt.getDouble("wattsReceived");
    }
}

