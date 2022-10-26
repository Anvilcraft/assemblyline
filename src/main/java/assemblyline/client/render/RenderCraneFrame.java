package assemblyline.client.render;

import assemblyline.client.model.ModelCraneRail;
import assemblyline.common.machine.crane.CraneHelper;
import assemblyline.common.machine.crane.TileEntityCraneRail;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class RenderCraneFrame
extends TileEntitySpecialRenderer {
    public static final String TEXTURE = "crane_frame.png";
    public static final ModelCraneRail MODEL = new ModelCraneRail();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        if (tileEntity != null && tileEntity instanceof TileEntityCraneRail) {
            int tX = tileEntity.xCoord;
            int tY = tileEntity.yCoord;
            int tZ = tileEntity.zCoord;
            boolean renderUp = CraneHelper.canFrameConnectTo(tileEntity, tX, tY + 1, tZ, ForgeDirection.DOWN);
            boolean renderDown = CraneHelper.canFrameConnectTo(tileEntity, tX, tY - 1, tZ, ForgeDirection.UP);
            boolean renderLeft = CraneHelper.canFrameConnectTo(tileEntity, tX - 1, tY, tZ, ForgeDirection.EAST);
            boolean renderRight = CraneHelper.canFrameConnectTo(tileEntity, tX + 1, tY, tZ, ForgeDirection.WEST);
            boolean renderFront = CraneHelper.canFrameConnectTo(tileEntity, tX, tY, tZ - 1, ForgeDirection.SOUTH);
            boolean renderBack = CraneHelper.canFrameConnectTo(tileEntity, tX, tY, tZ + 1, ForgeDirection.NORTH);
            boolean renderFoot = tileEntity.getWorldObj().isSideSolid(tX, tY - 1, tZ, ForgeDirection.UP);
            if (renderLeft && renderRight || renderFront && renderBack) {
                renderFoot = false;
            }
            this.bindTexture(new ResourceLocation("assemblyline", "textures/models/crane_frame.png"));
            GL11.glPushMatrix();
            GL11.glTranslated((double)(x + 0.5), (double)(y + 1.5), (double)(z + 0.5));
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glEnable((int)2896);
            MODEL.render(renderUp, renderDown && !renderFoot, renderLeft, renderRight, renderFront, renderBack, renderFoot);
            GL11.glPopMatrix();
        }
    }
}

