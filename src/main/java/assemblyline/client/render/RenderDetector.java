package assemblyline.client.render;

import assemblyline.client.model.ModelHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import universalelectricity.core.vector.Vector3;

@SideOnly(value=Side.CLIENT)
public class RenderDetector
extends RenderImprintable {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float var8) {
        super.renderTileEntityAt(tileEntity, x, y, z, var8);
    }

    public static void render(boolean isInverted, Vector3 position) { //TODO: WTF
        GL11.glPushMatrix();
        GL11.glTranslated((double)position.x, (double)position.y, (double)position.z);
        if (isInverted) {
            // empty if block
        }
        ModelHelper.setGlobalTextureResolution(128, 128);
        ModelHelper.setTextureClip(false);
        ModelHelper.setTextureOffset(0, 64);
        ModelHelper.setTextureSubResolution(64, 64);
        ModelHelper.drawCuboid(0.45f, 0.75f, 0.45f, 0.125f, 0.25f, 0.125f);
        ModelHelper.setTextureOffset(0, 0);
        ModelHelper.setTextureSubResolution(128, 64);
        ModelHelper.drawCuboid(0.25f, 0.25f, 0.25f, 0.5f, 0.5f, 0.5f);
        ModelHelper.setTextureOffset(64, 64);
        ModelHelper.setTextureSubResolution(64, 32);
        ModelHelper.drawCuboid(0.375f, 0.1875f, 0.375f, 0.25f, 0.0625f, 0.25f);
        GL11.glPopMatrix();
    }
}

