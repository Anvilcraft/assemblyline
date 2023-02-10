package assemblyline.client.render;

import assemblyline.client.model.ModelRejectorPiston;
import assemblyline.common.machine.TileEntityRejector;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderRejector extends RenderImprintable {
    private ModelRejectorPiston model = new ModelRejectorPiston();

    private void
    renderAModelAt(TileEntityRejector tileEntity, double x, double y, double z, float f) {
        boolean fire = tileEntity.firePiston;
        int face = tileEntity.getDirection().ordinal();
        int pos = 0;
        if (fire) {
            pos = 8;
        }
        this.bindTexture(
            new ResourceLocation("assemblyline", "textures/models/rejector.png")
        );
        GL11.glPushMatrix();
        GL11.glTranslatef(
            (float) ((float) x + 0.5f),
            (float) ((float) y + 1.5f),
            (float) ((float) z + 0.5f)
        );
        GL11.glScalef((float) 1.0f, (float) -1.0f, (float) -1.0f);
        if (face == 2) {
            GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
        }
        if (face == 3) {
            GL11.glRotatef((float) 0.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
        } else if (face == 4) {
            GL11.glRotatef((float) 90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
        } else if (face == 5) {
            GL11.glRotatef((float) 270.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
        }
        this.model.render(0.0625f);
        this.model.renderPiston(0.0625f, pos);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(
        TileEntity tileEntity, double var2, double var4, double var6, float var8
    ) {
        this.renderAModelAt((TileEntityRejector) tileEntity, var2, var4, var6, var8);
        super.renderTileEntityAt(tileEntity, var2, var4, var6, var8);
    }
}
