package assemblyline.client.render;

import assemblyline.client.model.ModelManipulator;
import assemblyline.common.machine.TileEntityManipulator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderManipulator
extends RenderImprintable {
    private ModelManipulator model = new ModelManipulator();

    private void renderAModelAt(TileEntityManipulator tileEntity, double x, double y, double z, float f) {
        int face = tileEntity.getDirection().ordinal();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)x + 0.5f), (float)((float)y + 1.5f), (float)((float)z + 0.5f));
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        if (tileEntity.isOutput()) {
            this.bindTexture(new ResourceLocation("assemblyline", "textures/models/manipulator1.png"));
        } else {
            this.bindTexture(new ResourceLocation("assemblyline", "textures/models/manipulator2.png"));
        }
        if (face == 2) {
            GL11.glRotatef((float)0.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        } else if (face == 3) {
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        } else if (face == 4) {
            GL11.glRotatef((float)270.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        } else if (face == 5) {
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        }
        this.model.render(0.0625f, true, 0);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double var2, double var4, double var6, float var8) {
        this.renderAModelAt((TileEntityManipulator)tileEntity, var2, var4, var6, var8);
        super.renderTileEntityAt(tileEntity, var2, var4, var6, var8);
    }
}

