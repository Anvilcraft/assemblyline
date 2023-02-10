package assemblyline.common.machine.command;

import java.util.ArrayList;

import assemblyline.common.machine.command.Command;
import assemblyline.common.machine.command.CommandRotateTo;
import dark.library.helpers.ItemFindingHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import universalelectricity.core.vector.Vector3;

public class CommandBreak extends Command {
    private CommandRotateTo rotateToCommand;
    int BREAK_TIME = 30;
    boolean keep = false;

    @Override
    protected boolean doTask() {
        super.doTask();
        Vector3 serachPosition = this.tileEntity.getHandPosition();
        Block block = serachPosition.getBlock(this.world);
        if (block != null && this.BREAK_TIME <= this.ticks) {
            ArrayList items = block.getDrops(
                this.world,
                serachPosition.intX(),
                serachPosition.intY(),
                serachPosition.intZ(),
                serachPosition.getBlockMetadata((IBlockAccess) this.world),
                0
            );
            if (!this.keep || items.size() > 1) {
                ItemFindingHelper.dropBlockAsItem(
                    this.world,
                    serachPosition.intX(),
                    serachPosition.intY(),
                    serachPosition.intZ()
                );
            } else {
                this.tileEntity.grabEntity((Entity) new EntityItem(
                    this.world,
                    (double) serachPosition.intX() + 0.5,
                    (double) serachPosition.intY() + 0.5,
                    (double) serachPosition.intZ() + 0.5,
                    (ItemStack) items.get(0)
                ));
            }
            this.world.setBlock(
                serachPosition.intX(),
                serachPosition.intY(),
                serachPosition.intZ(),
                Blocks.air,
                0,
                3
            );
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BREAK";
    }
}
