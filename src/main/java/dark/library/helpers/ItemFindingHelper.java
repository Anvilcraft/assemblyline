package dark.library.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

public class ItemFindingHelper {
    public static List findAllItemIn(World world, Vector3 start, Vector3 end) {
        return world.getEntitiesWithinAABB(
            EntityItem.class,
            AxisAlignedBB.getBoundingBox(
                (double) start.x,
                (double) start.y,
                (double) start.z,
                (double) end.x,
                (double) end.y,
                (double) end.z
            )
        );
    }

    public static List
    findSelectItems(World world, Vector3 start, Vector3 end, List disiredItems) {
        List entityItems = ItemFindingHelper.findAllItemIn(world, start, end);
        return ItemFindingHelper.filterEntityItemsList(entityItems, disiredItems);
    }

    public static List<EntityItem>
    filterEntityItemsList(List<EntityItem> entityItems, List<ItemStack> disiredItems) {
        ArrayList<EntityItem> newItemList = new ArrayList<EntityItem>();
    block0:
        for (EntityItem entityItem : entityItems) {
            for (ItemStack itemStack : disiredItems) {
                if (entityItem.getEntityItem().getItem() != itemStack.getItem()
                    || entityItem.getEntityItem().getItemDamage()
                        != itemStack.getItemDamage()
                    || newItemList.contains((Object) entityItem))
                    continue;
                newItemList.add(entityItem);
                continue block0;
            }
        }
        return newItemList;
    }

    public static List<EntityItem> filterOutEntityItems(List<Entity> entities) {
        ArrayList<EntityItem> newEntityList = new ArrayList<EntityItem>();
        for (Entity entity : entities) {
            if (!(entity instanceof EntityItem))
                continue;
            newEntityList.add((EntityItem) entity);
        }
        return newEntityList;
    }

    public static List<ItemStack>
    filterItems(List<ItemStack> totalItems, List<ItemStack> desiredItems) {
        ArrayList<ItemStack> newItemList = new ArrayList<ItemStack>();
    block0:
        for (ItemStack entityItem : totalItems) {
            for (ItemStack itemStack : desiredItems) {
                if (entityItem.getItem() != itemStack.getItem()
                    || entityItem.getItemDamage() != itemStack.getItemDamage()
                    || newItemList.contains((Object) entityItem))
                    continue;
                newItemList.add(entityItem);
                continue block0;
            }
        }
        return newItemList;
    }

    public static boolean
    dropItemStackExact(World world, double x, double y, double z, ItemStack stack) {
        if (!world.isRemote && stack != null) {
            EntityItem entity = new EntityItem(world, x, y, z, stack);
            entity.delayBeforeCanPickup = 10;
            return world.spawnEntityInWorld((Entity) entity);
        }
        return false;
    }

    public static void dropBlockAsItem(World world, int x, int y, int z) {
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);
            Block block = world.getBlock(x, y, z);
            ArrayList<ItemStack> items = block.getDrops(world, x, y, z, meta, 0);
            for (ItemStack item : items) {
                ItemFindingHelper.dropItemStackExact(
                    world, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, item
                );
            }
        }
    }
}
