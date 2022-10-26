package assemblyline.client.render;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
    public static void renderFloatingText(String text, float x, float y, float z) {
        RenderHelper.renderFloatingText(text, x, y, z, 0xFFFFFF);
    }

    public static void renderFloatingText(String text, float x, float y, float z, int color) {
        RenderManager renderManager = RenderManager.instance;
        FontRenderer fontRenderer = renderManager.getFontRenderer();
        float scale = 0.027f;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(x + 0.0f), (float)(y + 2.3f), (float)z);
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-scale), (float)(-scale), (float)scale);
        GL11.glDisable((int)2896);
        OpenGlHelper.setLightmapTextureCoords((int)OpenGlHelper.lightmapTexUnit, (float)240.0f, (float)240.0f);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        Tessellator tessellator = Tessellator.instance;
        int yOffset = 0;
        GL11.glDisable((int)3553);
        tessellator.startDrawingQuads();
        int stringMiddle = fontRenderer.getStringWidth(text) / 2;
        tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.5f);
        tessellator.addVertex((double)(-stringMiddle - 1), (double)(-1 + yOffset), 0.0);
        tessellator.addVertex((double)(-stringMiddle - 1), (double)(8 + yOffset), 0.0);
        tessellator.addVertex((double)(stringMiddle + 1), (double)(8 + yOffset), 0.0);
        tessellator.addVertex((double)(stringMiddle + 1), (double)(-1 + yOffset), 0.0);
        tessellator.draw();
        GL11.glEnable((int)3553);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
        fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, yOffset, color);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, yOffset, color);
        GL11.glEnable((int)2896);
        GL11.glDisable((int)3042);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }
}

