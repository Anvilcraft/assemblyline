package assemblyline.client.render;

import assemblyline.client.model.ModelConveyorBelt;
import assemblyline.client.model.ModelManipulator;
import assemblyline.client.model.ModelRejectorPiston;
import assemblyline.common.AssemblyLine;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(value = Side.CLIENT)
public class BlockRenderingHandler implements ISimpleBlockRenderingHandler {
    public static BlockRenderingHandler instance = new BlockRenderingHandler();
    public static final int BLOCK_RENDER_ID
        = RenderingRegistry.getNextAvailableRenderId();
    private ModelConveyorBelt modelConveyorBelt = new ModelConveyorBelt();
    private ModelRejectorPiston modelEjector = new ModelRejectorPiston();
    private ModelManipulator modelInjector = new ModelManipulator();

    @Override
    public void
    renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (block == AssemblyLine.blockConveyorBelt) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) 0.0f, (float) 1.5f, (float) 0.0f);
            GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(
                new ResourceLocation("assemblyline", "textures/models/belt/frame0.png")
            );
            this.modelConveyorBelt.render(0.0625f, 0.0f, false, false, false, false);
            GL11.glPopMatrix();
        } else if (block == AssemblyLine.blockRejector) {
            FMLClientHandler.instance().getClient().getTextureManager();
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(
                new ResourceLocation("assemblyline", "textures/models/rejector.png")
            );
            GL11.glPushMatrix();
            GL11.glTranslatef((float) 0.6f, (float) 1.5f, (float) 0.6f);
            GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
            GL11.glRotatef((float) -90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            this.modelEjector.render(0.0625f);
            this.modelEjector.renderPiston(0.0625f, 1);
            GL11.glPopMatrix();
        } else if (block == AssemblyLine.blockManipulator) {
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(
                new ResourceLocation("assemblyline", "textures/models/manipulator1.png")
            );
            GL11.glPushMatrix();
            GL11.glTranslatef((float) 0.6f, (float) 1.5f, (float) 0.6f);
            GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
            GL11.glRotatef((float) -90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            this.modelInjector.render(0.0625f, true, 0);
            GL11.glPopMatrix();
        } else if (block == AssemblyLine.blockArmbot) {
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(
                new ResourceLocation("assemblyline", "textures/models/armbot.png")
            );
            GL11.glPushMatrix();
            GL11.glTranslatef((float) 0.4f, (float) 0.8f, (float) 0.0f);
            GL11.glScalef((float) 0.7f, (float) 0.7f, (float) 0.7f);
            GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
            GL11.glRotatef((float) -90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            RenderArmbot.MODEL.render(0.0625f, 0.0f, 0.0f);
            GL11.glPopMatrix();
        } else if (block == AssemblyLine.blockCraneController) {
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(
                new ResourceLocation(
                    "assemblyline", "textures/models/crane_controller_off.png"
                )
            );
            GL11.glPushMatrix();
            GL11.glTranslatef((float) 0.0f, (float) 1.0f, (float) 0.0f);
            GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
            GL11.glRotatef((float) -90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            RenderCraneController.MODEL.render(0.0625f, false, false);
            GL11.glPopMatrix();
        } else if (block == AssemblyLine.blockCraneFrame) {
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(
                new ResourceLocation("assemblyline", "textures/models/crane_frame.png")
            );
            GL11.glPushMatrix();
            GL11.glTranslatef((float) 0.0f, (float) 1.0f, (float) 0.0f);
            GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
            GL11.glRotatef((float) -90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            RenderCraneFrame.MODEL.render(true, true, false, false, false, false, false);
            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean renderWorldBlock(
        IBlockAccess world,
        int x,
        int y,
        int z,
        Block block,
        int modelId,
        RenderBlocks renderer
    ) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int par0) {
        return true;
    }

    @Override
    public int getRenderId() {
        return BLOCK_RENDER_ID;
    }
}
