package assemblyline.client;

import assemblyline.client.gui.GuiEncoder;
import assemblyline.client.gui.GuiImprinter;
import assemblyline.client.render.BlockRenderingHandler;
import assemblyline.client.render.RenderArmbot;
import assemblyline.client.render.RenderConveyorBelt;
import assemblyline.client.render.RenderCraneController;
import assemblyline.client.render.RenderCraneFrame;
import assemblyline.client.render.RenderCrate;
import assemblyline.client.render.RenderDetector;
import assemblyline.client.render.RenderManipulator;
import assemblyline.client.render.RenderRejector;
import assemblyline.common.CommonProxy;
import assemblyline.common.block.TileEntityCrate;
import assemblyline.common.machine.TileEntityManipulator;
import assemblyline.common.machine.TileEntityRejector;
import assemblyline.common.machine.armbot.TileEntityArmbot;
import assemblyline.common.machine.belt.TileEntityConveyorBelt;
import assemblyline.common.machine.crane.TileEntityCraneController;
import assemblyline.common.machine.crane.TileEntityCraneRail;
import assemblyline.common.machine.detector.TileEntityDetector;
import assemblyline.common.machine.encoder.TileEntityEncoder;
import assemblyline.common.machine.imprinter.TileEntityImprinter;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy
extends CommonProxy {
    @Override
    public void preInit() {
        RenderingRegistry.registerBlockHandler(new BlockRenderingHandler());
    }

    @Override
    public void init() {
        super.init();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConveyorBelt.class, (TileEntitySpecialRenderer)new RenderConveyorBelt());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRejector.class, (TileEntitySpecialRenderer)new RenderRejector());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityManipulator.class, (TileEntitySpecialRenderer)new RenderManipulator());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrate.class, (TileEntitySpecialRenderer)new RenderCrate());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityArmbot.class, (TileEntitySpecialRenderer)new RenderArmbot());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDetector.class, (TileEntitySpecialRenderer)new RenderDetector());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraneController.class, (TileEntitySpecialRenderer)new RenderCraneController());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraneRail.class, (TileEntitySpecialRenderer)new RenderCraneFrame());
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null) {
            switch (ID) {
                case 1: {
                    return new GuiImprinter(player.inventory, (TileEntityImprinter)tileEntity);
                }
                case 2: {
                    if (tileEntity == null || !(tileEntity instanceof TileEntityEncoder)) break;
                    return new GuiEncoder(player.inventory, (TileEntityEncoder)tileEntity);
                }
            }
        }
        return null;
    }

    @Override
    public boolean isCtrKeyDown() {
        return GuiScreen.isCtrlKeyDown();
    }
}

