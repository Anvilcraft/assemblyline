package assemblyline.client.render;

import assemblyline.common.block.TileEntityCrate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import universalelectricity.core.vector.Vector3;

public class RenderCrate extends TileEntitySpecialRenderer {
    private final RenderBlocks renderBlocks = new RenderBlocks();

    @Override
    public void
    renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float var8) {
        Vector3 vec = new Vector3(x, y, z);
        double distance = vec.distanceTo(new Vector3(0.0, 0.0, 0.0));
        if (tileEntity instanceof TileEntityCrate && distance < 15.0) {
            TileEntityCrate tileCrate = (TileEntityCrate) tileEntity;
            RenderItem renderItem = (RenderItem
            ) RenderManager.instance.getEntityClassRenderObject(EntityItem.class);
            String itemName = "Empty";
            String amount = "";
            ItemStack itemStack = tileCrate.getStackInSlot(0);
            if (itemStack != null) {
                itemName = itemStack.getDisplayName();
                amount = Integer.toString(itemStack.stackSize);
            }
            for (int side = 2; side < 6; ++side) {
                ForgeDirection direction = ForgeDirection.getOrientation((int) side);
                if (tileCrate.getWorldObj().isSideSolid(
                        tileCrate.xCoord + direction.offsetX,
                        tileCrate.yCoord,
                        tileCrate.zCoord + direction.offsetZ,
                        direction.getOpposite()
                    ))
                    continue;
                this.setupLight(tileCrate, direction.offsetX, direction.offsetZ);
                OpenGlHelper.setLightmapTextureCoords(
                    (int) OpenGlHelper.lightmapTexUnit, (float) 240.0f, (float) 240.0f
                );
                if (itemStack != null) {
                    GL11.glPushMatrix();
                    switch (side) {
                        case 2: {
                            GL11.glTranslated(
                                (double) (x + 0.65),
                                (double) (y + 0.9),
                                (double) (z - 0.01)
                            );
                            break;
                        }
                        case 3: {
                            GL11.glTranslated(
                                (double) (x + 0.35),
                                (double) (y + 0.9),
                                (double) (z + 1.01)
                            );
                            GL11.glRotatef(
                                (float) 180.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f
                            );
                            break;
                        }
                        case 4: {
                            GL11.glTranslated(
                                (double) (x - 0.01),
                                (double) (y + 0.9),
                                (double) (z + 0.35)
                            );
                            GL11.glRotatef(
                                (float) 90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f
                            );
                            break;
                        }
                        case 5: {
                            GL11.glTranslated(
                                (double) (x + 1.01),
                                (double) (y + 0.9),
                                (double) (z + 0.65)
                            );
                            GL11.glRotatef(
                                (float) -90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f
                            );
                        }
                    }
                    float scale = 0.03125f;
                    GL11.glScalef(
                        (float) (0.6f * scale), (float) (0.6f * scale), (float) 0.0f
                    );
                    GL11.glRotatef(
                        (float) 180.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f
                    );
                    TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
                    GL11.glDisable((int) 2896);
                    if (!ForgeHooksClient.renderInventoryItem(
                            (RenderBlocks) this.renderBlocks,
                            renderEngine,
                            (ItemStack) itemStack,
                            (boolean) true,
                            (float) 0.0f,
                            (float) 0.0f,
                            (float) 0.0f
                        )) {
                        renderItem.renderItemIntoGUI(
                            this.func_147498_b(), renderEngine, itemStack, 0, 0
                        );
                    }
                    GL11.glEnable((int) 2896);
                    GL11.glPopMatrix();
                }
                this.renderText(itemName, side, 0.02f, x, y - (double) 0.35f, z);
                if (amount == "")
                    continue;
                this.renderText(amount, side, 0.02f, x, y - (double) 0.15f, z);
            }
        }
    }

    private void setupLight(TileEntity tileEntity, int xDifference, int zDifference) {
        World world = tileEntity.getWorldObj();
        if (world
                .getBlock(
                    tileEntity.xCoord + xDifference,
                    tileEntity.yCoord,
                    tileEntity.zCoord + zDifference
                )
                .isOpaqueCube()) {
            return;
        }
        int br = world.getLightBrightnessForSkyBlocks(
            tileEntity.xCoord + xDifference,
            tileEntity.yCoord,
            tileEntity.zCoord + zDifference,
            0
        );
        int var11 = br % 65536;
        int var12 = br / 65536;
        float scale = 0.6f;
        OpenGlHelper.setLightmapTextureCoords(
            (int) OpenGlHelper.lightmapTexUnit,
            (float) ((float) var11 * scale),
            (float) ((float) var12 * scale)
        );
    }

    private void
    renderText(String text, int side, float maxScale, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glPolygonOffset((float) -10.0f, (float) -10.0f);
        GL11.glEnable((int) 32823);
        float displayX = 0.0f;
        float displayY = 0.0f;
        float displayWidth = 1.0f;
        float displayHeight = 1.0f;
        GL11.glTranslated((double) x, (double) y, (double) z);
        switch (side) {
            case 3: {
                GL11.glTranslatef((float) 0.0f, (float) 1.0f, (float) 0.0f);
                GL11.glRotatef((float) 0.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
                GL11.glRotatef((float) 90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
                break;
            }
            case 2: {
                GL11.glTranslatef((float) 1.0f, (float) 1.0f, (float) 1.0f);
                GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
                GL11.glRotatef((float) 90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
                break;
            }
            case 5: {
                GL11.glTranslatef((float) 0.0f, (float) 1.0f, (float) 1.0f);
                GL11.glRotatef((float) 90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
                GL11.glRotatef((float) 90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
                break;
            }
            case 4: {
                GL11.glTranslatef((float) 1.0f, (float) 1.0f, (float) 0.0f);
                GL11.glRotatef((float) -90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
                GL11.glRotatef((float) 90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
            }
        }
        GL11.glTranslatef(
            (float) (displayWidth / 2.0f), (float) 1.0f, (float) (displayHeight / 2.0f)
        );
        GL11.glRotatef((float) -90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
        FontRenderer fontRenderer = super.func_147498_b();
        int requiredWidth = Math.max(fontRenderer.getStringWidth(text), 1);
        int lineHeight = fontRenderer.FONT_HEIGHT + 2;
        int requiredHeight = lineHeight * 1;
        float scaler = 0.8f;
        float scaleX = displayWidth / (float) requiredWidth;
        float scaleY = displayHeight / (float) requiredHeight;
        float scale = scaleX * scaler;
        if (maxScale > 0.0f) {
            scale = Math.min(scale, maxScale);
        }
        GL11.glScalef((float) scale, (float) (-scale), (float) scale);
        GL11.glDepthMask((boolean) false);
        int realHeight = (int) Math.floor(displayHeight / scale);
        int realWidth = (int) Math.floor(displayWidth / scale);
        int offsetX = (realWidth - requiredWidth) / 2;
        int offsetY = (realHeight - requiredHeight) / 2;
        GL11.glDisable((int) 2896);
        fontRenderer.drawString(
            "\u00a7f" + text, offsetX - realWidth / 2, 1 + offsetY - realHeight / 2, 1
        );
        GL11.glEnable((int) 2896);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 32823);
        GL11.glPopMatrix();
    }
}
