package assemblyline.client.render;

import assemblyline.client.model.ModelAngledBelt;
import assemblyline.client.model.ModelConveyorBelt;
import assemblyline.common.machine.belt.TileEntityConveyorBelt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RenderConveyorBelt
extends TileEntitySpecialRenderer {
    public static final ModelConveyorBelt MODEL = new ModelConveyorBelt();
    public static final ModelAngledBelt MODEL2 = new ModelAngledBelt();

    private void renderAModelAt(TileEntityConveyorBelt tileEntity, double x, double y, double z, float f) {
        boolean mid = tileEntity.getIsMiddleBelt();
        TileEntityConveyorBelt.SlantType slantType = tileEntity.getSlant();
        int face = tileEntity.getDirection().ordinal();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)x + 0.5f), (float)((float)y + 1.5f), (float)((float)z + 0.5f));
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        int frame = tileEntity.getAnimationFrame();
        if (slantType != null && slantType != TileEntityConveyorBelt.SlantType.NONE) {
            boolean slantAdjust;
            switch (face) {
                case 2: {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    break;
                }
                case 3: {
                    GL11.glRotatef((float)0.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    break;
                }
                case 4: {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    break;
                }
                case 5: {
                    GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
            }
            if (slantType == TileEntityConveyorBelt.SlantType.UP) {
                this.bindTexture(new ResourceLocation("assemblyline", "textures/models/slantedbelt/frame" + frame + ".png"));
                GL11.glTranslatef((float)0.0f, (float)0.8f, (float)-0.8f);
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)1.0f);
                slantAdjust = false;
                TileEntity test = tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord + tileEntity.getDirection().offsetX, tileEntity.yCoord, tileEntity.zCoord + tileEntity.getDirection().offsetZ);
                if (test != null && test instanceof TileEntityConveyorBelt && ((TileEntityConveyorBelt)test).getSlant() == TileEntityConveyorBelt.SlantType.TOP) {
                    GL11.glRotatef((float)10.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    slantAdjust = true;
                }
                MODEL2.render(0.0625f, true);
            } else if (slantType == TileEntityConveyorBelt.SlantType.DOWN) {
                this.bindTexture(new ResourceLocation("assemblyline", "textures/models/slantedbelt/frame" + frame + ".png"));
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                slantAdjust = false;
                TileEntity test = tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord - tileEntity.getDirection().offsetX, tileEntity.yCoord, tileEntity.zCoord - tileEntity.getDirection().offsetZ);
                if (test != null && test instanceof TileEntityConveyorBelt && ((TileEntityConveyorBelt)test).getSlant() == TileEntityConveyorBelt.SlantType.TOP) {
                    GL11.glRotatef((float)-10.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glTranslatef((float)0.0f, (float)0.25f, (float)0.0f);
                    slantAdjust = true;
                }
                MODEL2.render(0.0625f, slantAdjust);
            } else {
                this.bindTexture(new ResourceLocation("assemblyline", "textures/models/belt/frame" + frame + ".png"));
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)0.0f, (float)-0.68f, (float)0.0f);
                MODEL.render(0.0625f, (float)Math.toRadians(tileEntity.wheelRotation), tileEntity.getIsLastBelt(), tileEntity.getIsFirstBelt(), false, false);
            }
        } else {
            switch (face) {
                case 2: {
                    GL11.glRotatef((float)0.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    break;
                }
                case 3: {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    break;
                }
                case 4: {
                    GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    break;
                }
                case 5: {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
            }
            this.bindTexture(new ResourceLocation("assemblyline", "textures/models/belt/frame" + frame + ".png"));
            MODEL.render(0.0625f, (float)Math.toRadians(tileEntity.wheelRotation), tileEntity.getIsLastBelt(), tileEntity.getIsFirstBelt(), false, true);
        }
        Block ent = tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double var2, double var4, double var6, float var8) {
        this.renderAModelAt((TileEntityConveyorBelt)tileEntity, var2, var4, var6, var8);
    }
}

