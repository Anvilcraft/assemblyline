package assemblyline.common.machine.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;

public class GrabDictionary {
    private static List<GrabDictionary> grabList = new ArrayList<>();
    private Class<? extends Entity> entityClass;
    private String name;

    public GrabDictionary(String name, Class<? extends Entity> Class2) {
        this.entityClass = Class2;
        this.name = name;
    }

    public static List<GrabDictionary> getList() {
        return grabList;
    }

    public static GrabDictionary get(Object ob) {
        if (ob instanceof String) {
            String name = (String) ob;
            for (GrabDictionary ref : grabList) {
                if (!ref.getName().equalsIgnoreCase(name))
                    continue;
                return ref;
            }
        }
        if (ob instanceof Class) {
            Class cc = (Class) ob;
            for (GrabDictionary ref : grabList) {
                if (ref.getEntityClass() != cc)
                    continue;
                return ref;
            }
        }
        return new GrabDictionary("Entity", Entity.class);
    }

    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        return "";
    }

    public Class getEntityClass() {
        if (this.entityClass != null) {
            return this.entityClass;
        }
        return Entity.class;
    }

    public static void registerGrabableEntity(String name, Class eClass) {
        GrabDictionary newGrab = new GrabDictionary(name, eClass);
        if (!grabList.contains(newGrab)) {
            for (GrabDictionary ref : grabList) {
                if (!ref.getName().equalsIgnoreCase(name))
                    continue;
                return;
            }
            GrabDictionary.getList().add(newGrab);
        }
    }

    public static void registerList() {
        GrabDictionary.registerGrabableEntity("chicken", EntityChicken.class);
        GrabDictionary.registerGrabableEntity("cow", EntityCow.class);
        GrabDictionary.registerGrabableEntity("sheep", EntitySheep.class);
        GrabDictionary.registerGrabableEntity("pig", EntityPig.class);
        GrabDictionary.registerGrabableEntity("player", EntityPlayer.class);
        GrabDictionary.registerGrabableEntity("zombie", EntityZombie.class);
        GrabDictionary.registerGrabableEntity("zomb", EntityZombie.class);
        GrabDictionary.registerGrabableEntity("skeleton", EntitySkeleton.class);
        GrabDictionary.registerGrabableEntity("skel", EntitySkeleton.class);
        GrabDictionary.registerGrabableEntity("animal", EntityAnimal.class);
        GrabDictionary.registerGrabableEntity("monster", EntityMob.class);
        GrabDictionary.registerGrabableEntity("mob", EntityMob.class);
        GrabDictionary.registerGrabableEntity("creeper", EntityCreeper.class);
        GrabDictionary.registerGrabableEntity("spider", EntitySpider.class);
        GrabDictionary.registerGrabableEntity("slime", EntitySlime.class);
        GrabDictionary.registerGrabableEntity("items", EntityItem.class);
        GrabDictionary.registerGrabableEntity("all", Entity.class);
        GrabDictionary.registerGrabableEntity("everything", Entity.class);
        GrabDictionary.registerGrabableEntity("boat", EntityBoat.class);
        GrabDictionary.registerGrabableEntity("cart", EntityMinecart.class);
    }
}
