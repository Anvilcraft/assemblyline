package assemblyline.client.gui;

import cpw.mods.fml.client.GuiScrollingList;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiCommandList
extends GuiScrollingList {
    private ArrayList commands = new ArrayList();
    private int selIndex = -1;
    private Minecraft mc;

    public GuiCommandList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight) {
        super(client, width, height, top, bottom, left, entryHeight);
        this.mc = client;
    }

    public void setCommands(ArrayList commands) {
        this.commands = (ArrayList)commands.clone();
    }

    protected int getSize() {
        return this.commands.size();
    }

    protected void elementClicked(int index, boolean doubleClick) {
        this.selIndex = index;
    }

    protected boolean isSelected(int index) {
        return this.selIndex == index;
    }

    protected void drawBackground() {
        GuiCommandList.drawOutlineRect(this.left, this.left + this.listWidth, this.top, this.top + this.listHeight, 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f);
    }

    public static void drawOutlineRect(int x1, int y1, int x2, int y2, float rR, float rG, float rB, float lR, float lG, float lB) {
        Tessellator tesselator = Tessellator.instance;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)rR, (float)rG, (float)rB, (float)1.0f);
        if (rR > 0.0f && rG > 0.0f && rB > 0.0f) {
            tesselator.startDrawingQuads();
            tesselator.addVertex((double)x1, (double)y2, 0.0);
            tesselator.addVertex((double)x2, (double)y2, 0.0);
            tesselator.addVertex((double)x2, (double)y1, 0.0);
            tesselator.addVertex((double)x1, (double)y1, 0.0);
            tesselator.draw();
        }
        GL11.glColor4f((float)lR, (float)lG, (float)lB, (float)1.0f);
        tesselator.startDrawingQuads();
        tesselator.addVertex((double)x1, (double)y1, 0.0);
        tesselator.addVertex((double)x1, (double)y2, 0.0);
        tesselator.addVertex((double)x1 + 1.0, (double)y2, 0.0);
        tesselator.addVertex((double)x1 + 1.0, (double)y1, 0.0);
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.addVertex((double)x2 - 1.0, (double)y1, 0.0);
        tesselator.addVertex((double)x2 - 1.0, (double)y2, 0.0);
        tesselator.addVertex((double)x2, (double)y2, 0.0);
        tesselator.addVertex((double)x2, (double)y1, 0.0);
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.addVertex((double)x1, (double)y1, 0.0);
        tesselator.addVertex((double)x1, (double)y1 + 1.0, 0.0);
        tesselator.addVertex((double)x2, (double)y1 + 1.0, 0.0);
        tesselator.addVertex((double)x2, (double)y1, 0.0);
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.addVertex((double)x1, (double)y2 - 1.0, 0.0);
        tesselator.addVertex((double)x1, (double)y2, 0.0);
        tesselator.addVertex((double)x2, (double)y2, 0.0);
        tesselator.addVertex((double)x2, (double)y2 - 1.0, 0.0);
        tesselator.draw();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    protected void drawSlot(int slotID, int width, int slotY, int slotHeight, Tessellator tessellator) {
        if (slotID < this.commands.size()) {
            String command = (String)this.commands.get(slotID);
            if (this.isSelected(slotID)) {
                GuiCommandList.drawOutlineRect(this.left, this.left + width, this.top + slotY, this.top + slotY + slotHeight, -1.0f, -1.0f, -1.0f, 0.5f, 0.5f, 0.5f);
            }
            this.mc.fontRenderer.drawString(command, this.left + 4, slotY + 4, 0xAAAAAA, false);
        }
    }
}

