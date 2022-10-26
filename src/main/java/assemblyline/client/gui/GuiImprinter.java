package assemblyline.client.gui;

import assemblyline.common.machine.imprinter.ContainerImprinter;
import assemblyline.common.machine.imprinter.TileEntityImprinter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import universalelectricity.prefab.TranslationHelper;

public class GuiImprinter
extends GuiContainer {
    private int containerWidth;
    private int containerHeight;
    private TileEntityImprinter tileEntity;

    public GuiImprinter(InventoryPlayer par1InventoryPlayer, TileEntityImprinter tileEntity) {
        super((Container)new ContainerImprinter(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.ySize = 201;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString("Use Inventories:", 70, 58, 0x404040);
        this.fontRendererObj.drawString("" + this.tileEntity.searchInventories, 105, 67, 0x404040);
        this.fontRendererObj.drawString(TranslationHelper.getLocal("tile.imprinter.name"), 68, 6, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        this.mc.renderEngine.bindTexture(new ResourceLocation("assemblyline", "textures/gui/gui_imprinter.png"));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.containerWidth = (this.width - this.xSize) / 2;
        this.containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(this.containerWidth, this.containerHeight, 0, 0, this.xSize, this.ySize);
    }
}

