package assemblyline.client.render;

import assemblyline.client.model.ModelCraneController;
import assemblyline.common.machine.crane.CraneHelper;
import assemblyline.common.machine.crane.TileEntityCraneController;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class RenderCraneController extends RenderImprintable {
    public static final String TEXTURE = "crane_controller_off.png";
    public static final String TEXTURE_VALID = "crane_controller_on.png";
    public static final ModelCraneController MODEL = new ModelCraneController();

    @Override
    public void
    renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        if (tileEntity != null && tileEntity instanceof TileEntityCraneController) {
            this.bindTexture(new ResourceLocation(
                "assemblyline",
                "textures/models/"
                    + (((TileEntityCraneController) tileEntity).isCraneValid()
                           ? TEXTURE_VALID
                           : TEXTURE)
            ));
            ForgeDirection front = ForgeDirection.getOrientation(
                (int) tileEntity.getWorldObj().getBlockMetadata(
                    tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord
                )
            );
            ForgeDirection right = CraneHelper.rotateClockwise(front);
            float angle = 0.0f;
            switch (front) {
                case NORTH: {
                    angle = 90.0f;
                    break;
                }
                case SOUTH: {
                    angle = 270.0f;
                    break;
                }
                case EAST: {
                    angle = 180.0f;
                }
            }
            int tX = tileEntity.xCoord;
            int tY = tileEntity.yCoord;
            int tZ = tileEntity.zCoord;
            boolean connectFront = CraneHelper.canFrameConnectTo(
                tileEntity,
                tX + front.offsetX,
                tY,
                tZ + front.offsetZ,
                front.getOpposite()
            );
            boolean connectRight = CraneHelper.canFrameConnectTo(
                tileEntity,
                tX + right.offsetX,
                tY,
                tZ + right.offsetZ,
                right.getOpposite()
            );
            GL11.glPushMatrix();
            GL11.glTranslated((double) (x + 0.5), (double) (y + 1.5), (double) (z + 0.5));
            GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
            GL11.glRotatef((float) angle, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            GL11.glEnable((int) 2896);
            MODEL.render(0.0625f, connectRight, connectFront);
            GL11.glPopMatrix();
        }
    }
}
