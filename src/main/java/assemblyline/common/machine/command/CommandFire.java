package assemblyline.common.machine.command;

import assemblyline.common.machine.command.Command;
import assemblyline.common.machine.command.CommandDrop;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import universalelectricity.core.vector.Vector3;

public class CommandFire
extends Command {
    private static final float MIN_ACTUAL_PITCH = -80.0f;
    private static final float MAX_ACTUAL_PITCH = 80.0f;
    private float actualYaw;
    private float actualPitch;
    private float velocity;
    private Vector3 finalVelocity;

    @Override
    public void onTaskStart() {
        super.onTaskStart();
        this.velocity = this.getFloatArg(0).floatValue();
        if (this.velocity > 2.5f) {
            this.velocity = 2.5f;
        }
        if (this.velocity < 0.125f) {
            this.velocity = 1.0f;
        }
        this.actualYaw = this.tileEntity.rotationYaw;
        this.actualPitch = 160.0f * (this.tileEntity.rotationPitch / 60.0f) + -80.0f;
        double yaw = Math.toRadians(this.actualYaw);
        double pitch = Math.toRadians(this.actualPitch);
        double x = -Math.sin(yaw) * Math.cos(pitch);
        double y = Math.sin(pitch);
        double z = Math.cos(yaw) * Math.cos(pitch);
        this.finalVelocity = new Vector3(x, y, z);
        Random random = new Random(System.currentTimeMillis());
        this.finalVelocity.x *= (double)(0.995f + random.nextFloat() * 0.01f);
        this.finalVelocity.y *= (double)(0.995f + random.nextFloat() * 0.01f);
        this.finalVelocity.z *= (double)(0.995f + random.nextFloat() * 0.01f);
        this.finalVelocity.multiply(this.velocity);
    }

    @Override
    protected boolean doTask() {
        Entity held;
        if (this.finalVelocity == null) {
            this.finalVelocity = new Vector3(0.0, 0.0, 0.0);
        }
        if (this.tileEntity.getGrabbedEntities().size() > 0 && (held = (Entity)this.tileEntity.getGrabbedEntities().get(0)) != null) {
            this.world.playSound((double)this.tileEntity.xCoord, (double)this.tileEntity.yCoord, (double)this.tileEntity.zCoord, "random.bow", this.velocity, 2.0f - this.velocity / 4.0f, true);
            if (held instanceof EntityItem) {
                EntityItem item = (EntityItem)held;
                ItemStack stack = item.getEntityItem();
                ItemStack thrown = stack.copy();
                thrown.stackSize = 1;
                if (item.getEntityItem().stackSize > 0) {
                    --stack.stackSize;
                    item.setEntityItemStack(stack);
                } else {
                    this.commandManager.getNewCommand(this.tileEntity, CommandDrop.class, new String[0]).doTask();
                    if (!this.world.isRemote) {
                        this.world.removeEntity(held);
                    }
                }
                if (item.getEntityItem().getItem() == Items.arrow) {
                    EntityArrow arrow = new EntityArrow(this.world, this.tileEntity.getHandPosition().x, this.tileEntity.getHandPosition().y, this.tileEntity.getHandPosition().z);
                    arrow.motionX = this.finalVelocity.x;
                    arrow.motionY = this.finalVelocity.y;
                    arrow.motionZ = this.finalVelocity.z;
                    if (!this.world.isRemote) {
                        this.world.spawnEntityInWorld((Entity)arrow);
                    }
                } else {
                    EntityItem item2 = new EntityItem(this.world, this.tileEntity.getHandPosition().x, this.tileEntity.getHandPosition().y, this.tileEntity.getHandPosition().z, thrown);
                    item2.motionX = this.finalVelocity.x;
                    item2.motionY = this.finalVelocity.y;
                    item2.motionZ = this.finalVelocity.z;
                    if (!this.world.isRemote) {
                        this.world.spawnEntityInWorld((Entity)item2);
                    }
                }
            } else {
                this.commandManager.getNewCommand(this.tileEntity, CommandDrop.class, new String[0]).doTask();
                held.motionX = this.finalVelocity.x;
                held.motionY = this.finalVelocity.y;
                held.motionZ = this.finalVelocity.z;
            }
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound taskCompound) {
        super.readFromNBT(taskCompound);
        this.actualYaw = taskCompound.getFloat("fireYaw");
        this.actualPitch = taskCompound.getFloat("firePitch");
        this.velocity = taskCompound.getFloat("fireVelocity");
        this.finalVelocity = new Vector3();
        this.finalVelocity.x = taskCompound.getDouble("fireVectorX");
        this.finalVelocity.y = taskCompound.getDouble("fireVectorY");
        this.finalVelocity.z = taskCompound.getDouble("fireVectorZ");
    }

    @Override
    public void writeToNBT(NBTTagCompound taskCompound) {
        super.writeToNBT(taskCompound);
        taskCompound.setFloat("fireYaw", this.actualYaw);
        taskCompound.setFloat("firePitch", this.actualPitch);
        taskCompound.setFloat("fireVelocity", this.velocity);
        if (this.finalVelocity != null) {
            taskCompound.setDouble("fireVectorX", this.finalVelocity.x);
            taskCompound.setDouble("fireVectorY", this.finalVelocity.y);
            taskCompound.setDouble("fireVectorZ", this.finalVelocity.z);
        }
    }

    @Override
    public String toString() {
        return "FIRE " + Float.toString(this.velocity);
    }
}

