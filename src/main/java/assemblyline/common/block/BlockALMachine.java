package assemblyline.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import universalelectricity.prefab.block.BlockAdvanced;

public abstract class BlockALMachine extends BlockAdvanced {
    public IIcon machine_icon;

    public BlockALMachine(Material material) {
        super(material);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconReg) {
        this.machine_icon = iconReg.registerIcon("assemblyline:machine");
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon
    getIcon(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
        return this.machine_icon;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int par2) {
        return this.machine_icon;
    }

    @Override
    public TileEntity createTileEntity(World w, int meta) {
        return this.createNewTileEntity(w, meta);
    }
}
