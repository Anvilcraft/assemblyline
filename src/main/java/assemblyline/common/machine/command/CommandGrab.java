package assemblyline.common.machine.command;

import assemblyline.common.machine.belt.TileEntityConveyorBelt;
import assemblyline.common.machine.command.Command;
import assemblyline.common.machine.command.GrabDictionary;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import universalelectricity.core.vector.Vector3;

public class CommandGrab
extends Command {
    public static final float radius = 0.5f;
    public boolean child = false;
    private TileEntityConveyorBelt belt;
    private Class entityToInclude;

    @Override
    public void onTaskStart() {
        super.onTaskStart();
        this.entityToInclude = Entity.class;
        if (this.getArgs() != null && this.getArgs().length > 0 && this.getArgs()[0] != null) {
            if (this.getArg(0).equalsIgnoreCase("baby") || this.getArg(0).equalsIgnoreCase("child")) {
                this.child = true;
                if (this.getArgs().length > 1 && this.getArgs()[1] != null) {
                    this.entityToInclude = GrabDictionary.get(this.getArg(1)).getEntityClass();
                }
            } else {
                this.entityToInclude = GrabDictionary.get(this.getArg(0)).getEntityClass();
                if (this.getArgs().length > 1 && this.getArgs()[1] != null && (this.getArg(1).equalsIgnoreCase("baby") || this.getArg(0).equalsIgnoreCase("child"))) {
                    this.child = true;
                }
            }
        }
    }

    @Override
    protected boolean doTask() {
        super.doTask();
        if (this.tileEntity.getGrabbedEntities().size() > 0) {
            return false;
        }
        Vector3 serachPosition = this.tileEntity.getHandPosition();
        List found = this.world.getEntitiesWithinAABB(this.entityToInclude, AxisAlignedBB.getBoundingBox((double)(serachPosition.x - 0.5), (double)(serachPosition.y - 0.5), (double)(serachPosition.z - 0.5), (double)(serachPosition.x + 0.5), (double)(serachPosition.y + 0.5), (double)(serachPosition.z + 0.5)));
        TileEntity ent = serachPosition.getTileEntity((IBlockAccess)this.world);
        Vector3 searchPostion2 = Vector3.add(serachPosition, new Vector3(0.0, -1.0, 0.0));
        TileEntity ent2 = searchPostion2.getTileEntity((IBlockAccess)this.world);
        if (ent instanceof TileEntityConveyorBelt) {
            this.belt = (TileEntityConveyorBelt)ent;
        } else if (ent2 instanceof TileEntityConveyorBelt) {
            this.belt = (TileEntityConveyorBelt)ent2;
        }
        if (found != null && found.size() > 0) {
            for (int i = 0; i < found.size(); ++i) {
                if (found.get(i) == null || found.get(i) instanceof EntityArrow || found.get(i) instanceof EntityPlayer || ((Entity)found.get((int)i)).ridingEntity != null || found.get(i) instanceof EntityAgeable && (!(found.get(i) instanceof EntityAgeable) || this.child != ((EntityAgeable)found.get(i)).isChild())) continue;
                this.tileEntity.grabEntity((Entity)found.get(i));
                this.world.playSound((double)this.tileEntity.xCoord, (double)this.tileEntity.yCoord, (double)this.tileEntity.zCoord, "random.pop", 0.2f, ((this.tileEntity.getWorldObj().rand.nextFloat() - this.tileEntity.getWorldObj().rand.nextFloat()) * 0.7f + 1.0f) * 1.0f, true);
                if (this.belt != null) {
                    this.belt.IgnoreEntity((Entity)found.get(i));
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound taskCompound) {
        super.readFromNBT(taskCompound);
        this.child = taskCompound.getBoolean("child");
        this.entityToInclude = GrabDictionary.get(taskCompound.getString("name")).getEntityClass();
    }

    @Override
    public void writeToNBT(NBTTagCompound taskCompound) {
        super.writeToNBT(taskCompound);
        taskCompound.setBoolean("child", this.child);
        taskCompound.setString("name", this.entityToInclude != null ? GrabDictionary.get(this.entityToInclude).getName() : "");
    }

    @Override
    public String toString() {
        String baby = "";
        String entity = "";
        if (this.entityToInclude != null) {
            entity = GrabDictionary.get(this.entityToInclude).getName();
            if (this.child) {
                baby = "baby ";
            }
        }
        return "GRAB " + baby + entity;
    }
}

