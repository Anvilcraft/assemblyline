package assemblyline.common.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import universalelectricity.core.vector.Vector3;

public class MessageEncoder implements IMessage {
    public NBTTagCompound nbt;
    public Vector3 tileLocation;

    public MessageEncoder() {}

    public MessageEncoder(Vector3 pos, NBTTagCompound nbt) {
        this.nbt = nbt;
        this.tileLocation = pos;
    }

    @Override
    public void fromBytes(ByteBuf bytes) {
        DataInputStream stream = new DataInputStream(new ByteBufInputStream(bytes));
        try {
            NBTTagCompound recTag = CompressedStreamTools.read(stream);
            tileLocation = Vector3.readFromNBT(recTag.getCompoundTag("tilePos"));
            nbt = recTag.getCompoundTag("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf bytes) {
        NBTTagCompound sendTag = new NBTTagCompound();
        sendTag.setTag("tilePos", tileLocation.writeToNBT(new NBTTagCompound()));
        sendTag.setTag("data", nbt);
        DataOutputStream stream = new DataOutputStream(new ByteBufOutputStream(bytes));
        try {
            CompressedStreamTools.write(sendTag, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
