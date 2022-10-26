package assemblyline.common.machine;

import assemblyline.common.TabAssemblyLine;
import assemblyline.common.block.BlockALMachine;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBeltSorter
extends BlockALMachine {
    public BlockBeltSorter() {
        super(Material.iron);
        this.setCreativeTab(TabAssemblyLine.INSTANCE);
        this.setBlockName("BeltSorter");
    }

    @Override
    public TileEntity createNewTileEntity(World arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }
}

