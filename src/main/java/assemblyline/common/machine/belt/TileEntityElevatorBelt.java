package assemblyline.common.machine.belt;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;

public class TileEntityElevatorBelt extends TileEntityConveyorBelt {
    public List conveyList = new ArrayList();

    public void doBeltAction() {
        this.conveyItemsVertical(true, false);
    }

    public boolean isBellowABelt() {
        TileEntity ent
            = this.worldObj.getTileEntity(this.xCoord, this.xCoord - 1, this.zCoord);
        return ent instanceof TileEntityElevatorBelt;
    }

    public void conveyItemsVertical(boolean extendLife, boolean preventPickUp) {}
}
