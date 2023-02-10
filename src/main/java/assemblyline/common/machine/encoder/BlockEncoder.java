package assemblyline.common.machine.encoder;

import assemblyline.common.AssemblyLine;
import assemblyline.common.TabAssemblyLine;
import assemblyline.common.block.BlockALMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEncoder extends BlockALMachine {
    IIcon encoder_side;
    IIcon encoder_top;
    IIcon encoder_bottom;

    public BlockEncoder() {
        super(Material.wood);
        this.setBlockName("encoder");
        this.setCreativeTab(TabAssemblyLine.INSTANCE);
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg) {
        this.encoder_side = iconReg.registerIcon("assemblyline:encoder_side");
        this.encoder_top = iconReg.registerIcon("assemblyline:encoder_top");
        this.encoder_bottom = iconReg.registerIcon("assemblyline:encoder_bottom");
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return this.getIcon(side, 0);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 1) {
            return this.encoder_top;
        }
        if (side == 0) {
            return this.encoder_bottom;
        }
        return this.encoder_side;
    }

    @Override
    public boolean onMachineActivated(
        World world,
        int x,
        int y,
        int z,
        EntityPlayer entityPlayer,
        int par6,
        float par7,
        float par8,
        float par9
    ) {
        if (!world.isRemote) {
            entityPlayer.openGui((Object) AssemblyLine.instance, 2, world, x, y, z);
        }
        return true;
    }

    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityEncoder();
    }
}
