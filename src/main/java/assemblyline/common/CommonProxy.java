package assemblyline.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import assemblyline.common.block.TileEntityCrate;
import assemblyline.common.machine.TileEntityManipulator;
import assemblyline.common.machine.TileEntityRejector;
import assemblyline.common.machine.armbot.TileEntityArmbot;
import assemblyline.common.machine.belt.TileEntityConveyorBelt;
import assemblyline.common.machine.crane.TileEntityCraneController;
import assemblyline.common.machine.crane.TileEntityCraneRail;
import assemblyline.common.machine.detector.TileEntityDetector;
import assemblyline.common.machine.encoder.ContainerEncoder;
import assemblyline.common.machine.encoder.TileEntityEncoder;
import assemblyline.common.machine.imprinter.ContainerImprinter;
import assemblyline.common.machine.imprinter.TileEntityImprinter;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.prefab.multiblock.TileEntityMulti;

public class CommonProxy implements IGuiHandler {
    public static final int GUI_IMPRINTER = 1;
    public static final int GUI_ENCODER = 2;

    public void preInit() {}

    public void init() {
        GameRegistry.registerTileEntity(
            TileEntityConveyorBelt.class, (String) "ALConveyorBelt"
        );
        GameRegistry.registerTileEntity(TileEntityRejector.class, (String) "ALSorter");
        GameRegistry.registerTileEntity(
            TileEntityManipulator.class, (String) "ALManipulator"
        );
        GameRegistry.registerTileEntity(TileEntityCrate.class, (String) "ALCrate");
        GameRegistry.registerTileEntity(TileEntityDetector.class, (String) "ALDetector");
        GameRegistry.registerTileEntity(TileEntityEncoder.class, (String) "ALEncoder");
        GameRegistry.registerTileEntity(TileEntityArmbot.class, (String) "ALArmbot");
        GameRegistry.registerTileEntity(
            TileEntityCraneController.class, (String) "ALCraneController"
        );
        GameRegistry.registerTileEntity(
            TileEntityCraneRail.class, (String) "ALCraneRail"
        );
        GameRegistry.registerTileEntity(
            TileEntityImprinter.class, (String) "ALImprinter"
        );
        GameRegistry.registerTileEntity(TileEntityMulti.class, (String) "ALMulti");
    }

    private void
    extractZipToLocation(File zipFile, String sourceFolder, String destFolder) {
        try {
            File destFile = new File(
                FMLCommonHandler.instance().getMinecraftServerInstance().getFile("."),
                destFolder
            );
            String destinationName = destFile.getAbsolutePath();
            byte[] buf = new byte[1024];
            ZipInputStream zipinputstream = null;
            zipinputstream = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry zipentry = zipinputstream.getNextEntry();
            while (zipentry != null) {
                int n;
                String zipentryName = zipentry.getName();
                if (!zipentryName.startsWith(sourceFolder)) {
                    zipentry = zipinputstream.getNextEntry();
                    continue;
                }
                String entryName = destinationName
                    + zipentryName.substring(
                        Math.min(zipentryName.length(), sourceFolder.length() - 1)
                    );
                entryName = entryName.replace('/', File.separatorChar);
                entryName = entryName.replace('\\', File.separatorChar);
                File newFile = new File(entryName);
                if (zipentry.isDirectory()) {
                    if (!newFile.mkdirs())
                        break;
                    zipentry = zipinputstream.getNextEntry();
                    continue;
                }
                FileOutputStream fileoutputstream = new FileOutputStream(entryName);
                while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
                    fileoutputstream.write(buf, 0, n);
                }
                fileoutputstream.close();
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();
            }
            zipinputstream.close();
        } catch (Exception e) {
            System.out.println("Error while loading AssemblyLine Lua libraries: ");
            e.printStackTrace();
        }
    }

    public Object
    getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null) {
            switch (ID) {
                case 1: {
                    return new ContainerImprinter(
                        player.inventory, (TileEntityImprinter) tileEntity
                    );
                }
                case 2: {
                    if (tileEntity == null || !(tileEntity instanceof TileEntityEncoder))
                        break;
                    return new ContainerEncoder(
                        player.inventory, (TileEntityEncoder) tileEntity
                    );
                }
            }
        }
        return null;
    }

    public Object
    getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public boolean isCtrKeyDown() {
        return false;
    }
}
