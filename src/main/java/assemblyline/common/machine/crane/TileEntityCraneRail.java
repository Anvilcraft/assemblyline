package assemblyline.common.machine.crane;

import assemblyline.api.ICraneStructure;
import assemblyline.common.machine.TileEntityAssemblyNetwork;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCraneRail
    extends TileEntityAssemblyNetwork implements ICraneStructure {
    @Override
    public boolean canFrameConnectTo(ForgeDirection side) {
        return true;
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return false;
    }
}
