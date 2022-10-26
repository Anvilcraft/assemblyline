package assemblyline.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
@Deprecated
public class GuiButtonImage
extends GuiButton {
    private int type = 0;

    public GuiButtonImage(int par1, int par2, int par3, int type) {
        super(par1, par2, par3, 12, 12, "");
        this.type = type;
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int width, int hight) {
        if (this.visible) {
            par1Minecraft.getTextureManager().bindTexture(new ResourceLocation("assemblyline", "textures/gui@.png"));
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean var4 = width >= this.xPosition && hight >= this.yPosition && width < this.xPosition + this.width && hight < this.yPosition + this.height;
            int var5 = 106;
            int var6 = 0;
            if (var4) {
                var5 += this.height;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, var6, var5, this.width, this.height);
        }
    }
}

