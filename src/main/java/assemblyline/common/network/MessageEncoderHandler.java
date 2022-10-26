package assemblyline.common.network;

import assemblyline.common.machine.encoder.TileEntityEncoder;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.tileentity.TileEntity;

public class MessageEncoderHandler implements IMessageHandler<MessageEncoder, IMessage> {

    @Override
    public IMessage onMessage(MessageEncoder msg, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            TileEntity tile = msg.tileLocation.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
            if (tile instanceof TileEntityEncoder) {
                ((TileEntityEncoder)tile).handleMessage(msg.nbt);
            }
        }
        return null;
    }
    
}
