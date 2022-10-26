package assemblyline.client.render;

import assemblyline.common.machine.imprinter.ItemImprinter;
import assemblyline.common.machine.imprinter.TileEntityFilterable;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;

public abstract class RenderImprintable
extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float var8) {
        EntityClientPlayerMP player;
        MovingObjectPosition objectPosition;
        TileEntityFilterable tileFilterable;
        ItemStack filter;
        if (tileEntity != null && tileEntity instanceof TileEntityFilterable && (filter = (tileFilterable = (TileEntityFilterable)tileEntity).getFilter()) != null && (objectPosition = (player = Minecraft.getMinecraft().thePlayer).rayTrace(8.0, 1.0f)) != null && objectPosition.blockX == tileFilterable.xCoord && objectPosition.blockY == tileFilterable.yCoord && objectPosition.blockZ == tileFilterable.zCoord) {
            ArrayList filters = ItemImprinter.getFilters(filter);
            for (int i = 0; i < filters.size(); ++i) {
                if (((TileEntityFilterable)tileEntity).isInverted()) {
                    RenderHelper.renderFloatingText(((ItemStack)filters.get(i)).getTooltip((EntityPlayer)player, Minecraft.getMinecraft().gameSettings.advancedItemTooltips).get(0).toString(), (float)x + 0.5f, (float)y + (float)i * 0.25f - 1.0f, (float)z + 0.5f, 0xFF8888);
                    continue;
                }
                RenderHelper.renderFloatingText(((ItemStack)filters.get(i)).getTooltip((EntityPlayer)player, Minecraft.getMinecraft().gameSettings.advancedItemTooltips).get(0).toString(), (float)x + 0.5f, (float)y + (float)i * 0.25f - 1.0f, (float)z + 0.5f, 0x88FF88);
            }
        }
    }
}

