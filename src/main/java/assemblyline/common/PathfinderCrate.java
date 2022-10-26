package assemblyline.common;

import assemblyline.common.block.TileEntityCrate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;

public class PathfinderCrate {
    public IPathCallBack callBackCheck = new IPathCallBack(){

        @Override
        public boolean isValidNode(PathfinderCrate finder, ForgeDirection direction, TileEntity provider, TileEntity node) {
            return node instanceof TileEntityCrate;
        }

        @Override
        public boolean onSearch(PathfinderCrate finder, TileEntity provider) {
            return false;
        }
    };
    public List<TileEntity> iteratedNodes;
    public List results;

    public PathfinderCrate() {
        this.clear();
    }

    public boolean findNodes(TileEntity provider) {
        if (provider != null) {
            this.iteratedNodes.add(provider);
            if (this.callBackCheck.onSearch(this, provider)) {
                return false;
            }
            for (int i = 0; i < 6; ++i) {
                TileEntity connectedTile = VectorHelper.getTileEntityFromSide(provider.getWorldObj(), new Vector3(provider), ForgeDirection.getOrientation((int)i));
                if (this.iteratedNodes.contains((Object)connectedTile) || !this.callBackCheck.isValidNode(this, ForgeDirection.getOrientation((int)i), provider, connectedTile) || this.findNodes(connectedTile)) continue;
                return false;
            }
        }
        return true;
    }

    public PathfinderCrate init(TileEntity provider) {
        this.findNodes(provider);
        return this;
    }

    public PathfinderCrate clear() {
        this.iteratedNodes = new ArrayList<>();
        this.results = new ArrayList();
        return this;
    }

    public static interface IPathCallBack {
        public boolean isValidNode(PathfinderCrate var1, ForgeDirection var2, TileEntity var3, TileEntity var4);

        public boolean onSearch(PathfinderCrate var1, TileEntity var2);
    }
}

