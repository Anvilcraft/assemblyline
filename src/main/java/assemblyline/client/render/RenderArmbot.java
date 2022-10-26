package assemblyline.client.render;

import assemblyline.client.model.ModelArmbot;
import assemblyline.common.machine.armbot.TileEntityArmbot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import universalelectricity.core.vector.Vector3;

public class RenderArmbot
extends TileEntitySpecialRenderer {
    public static final ModelArmbot MODEL = new ModelArmbot();
    public static final String TEXTURE = "armbot.png";

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        if (tileEntity instanceof TileEntityArmbot) {
            EntityClientPlayerMP player;
            MovingObjectPosition objectPosition;
            String cmdText = ((TileEntityArmbot)tileEntity).getCommandDisplayText();
            if (!(cmdText == null || cmdText.isEmpty() || (objectPosition = (player = Minecraft.getMinecraft().thePlayer).rayTrace(8.0, 1.0f)) == null || objectPosition.blockX != tileEntity.xCoord || objectPosition.blockY != tileEntity.yCoord && objectPosition.blockY != tileEntity.yCoord + 1 || objectPosition.blockZ != tileEntity.zCoord)) {
                RenderHelper.renderFloatingText(cmdText, (float)x + 0.5f, (float)y + 0.25f, (float)z + 0.5f, 0xFFFFFF);
            }
            this.bindTexture(new ResourceLocation("assemblyline", "textures/models/armbot.png"));
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)x + 0.5f), (float)((float)y + 1.5f), (float)((float)z + 0.5f));
            GL11.glScalef((float)1.0f, (float)-1.0f, (float)-1.0f);
            MODEL.render(0.0625f, ((TileEntityArmbot)tileEntity).renderYaw, ((TileEntityArmbot)tileEntity).renderPitch);
            GL11.glPopMatrix();
            Vector3 handPosition = ((TileEntityArmbot)tileEntity).getDeltaHandPosition();
            handPosition.add(0.5);
            handPosition.add(new Vector3(x, y, z));
            RenderItem renderItem = (RenderItem)RenderManager.instance.getEntityClassRenderObject(EntityItem.class);
            TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
            for (ItemStack itemStack : ((TileEntityArmbot)tileEntity).getGrabbedItems()) {
                if (itemStack == null) continue;
                if (((TileEntityArmbot)tileEntity).renderEntityItem == null) {
                    ((TileEntityArmbot)tileEntity).renderEntityItem = new EntityItem(tileEntity.getWorldObj(), 0.0, 0.0, 0.0, itemStack);
                } else if (!itemStack.isItemEqual(((TileEntityArmbot)tileEntity).renderEntityItem.getEntityItem())) {
                    ((TileEntityArmbot)tileEntity).renderEntityItem = new EntityItem(tileEntity.getWorldObj(), 0.0, 0.0, 0.0, itemStack);
                }
                renderItem.doRender(((TileEntityArmbot)tileEntity).renderEntityItem, handPosition.x, handPosition.y, handPosition.z, 0.0f, f);
                break;
            }
        }
    }
}

