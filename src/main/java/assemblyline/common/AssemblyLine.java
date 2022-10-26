package assemblyline.common;

import assemblyline.common.CommonProxy;
import assemblyline.common.TabAssemblyLine;
import assemblyline.common.block.BlockCrate;
import assemblyline.common.block.BlockTurntable;
import assemblyline.common.block.ItemBlockCrate;
import assemblyline.common.machine.BlockManipulator;
import assemblyline.common.machine.BlockRejector;
import assemblyline.common.machine.armbot.BlockArmbot;
import assemblyline.common.machine.belt.BlockConveyorBelt;
import assemblyline.common.machine.command.GrabDictionary;
import assemblyline.common.machine.crane.BlockCraneController;
import assemblyline.common.machine.crane.BlockCraneFrame;
import assemblyline.common.machine.detector.BlockDetector;
import assemblyline.common.machine.encoder.BlockEncoder;
import assemblyline.common.machine.encoder.ItemDisk;
import assemblyline.common.machine.imprinter.BlockImprinter;
import assemblyline.common.machine.imprinter.ItemImprinter;
import assemblyline.common.network.MessageEncoder;
import assemblyline.common.network.MessageEncoderHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import universalelectricity.prefab.TranslationHelper;
import universalelectricity.prefab.multiblock.BlockMulti;

@Mod(modid="AssemblyLine", name="Assembly Line", version="0.3.5.117", dependencies="after:BasicComponents; after:IC2", useMetadata=true)
public class AssemblyLine {
    public static final String MAJOR_VERSION = "0";
    public static final String MINOR_VERSION = "3";
    public static final String REVIS_VERSION = "5";
    public static final String BUILD_VERSION = "117";
    public static final String MOD_ID = "AssemblyLine";
    public static final String MOD_NAME = "Assembly Line";
    public static final String VERSION = "0.3.5.117";
    public static final String CHANNEL = "AssemblyLine";
    @SidedProxy(clientSide="assemblyline.client.ClientProxy", serverSide="assemblyline.common.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance(value="AssemblyLine")
    public static AssemblyLine instance;
    @Mod.Metadata(value="AssemblyLine")
    public static ModMetadata meta;
    public static final String LANGUAGE_PATH = "/assets/assemblyline/languages/";
    private static final String[] LANGUAGES_SUPPORTED;
    public static final Configuration CONFIGURATION;
    public static final int BLOCK_ID_PREFIX = 3030;
    public static Block blockConveyorBelt;
    public static Block blockManipulator;
    public static BlockCrate blockCrate;
    public static Block blockImprinter;
    public static Block blockEncoder;
    public static Block blockDetector;
    public static Block blockRejector;
    public static Block blockArmbot;
    public static Block blockCraneController;
    public static Block blockCraneFrame;
    public static Block blockTurntable;
    public static BlockMulti blockMulti;
    public static final int ITEM_ID_PREFIX = 13030;
    public static Item itemImprint;
    public static Item itemDisk;
    public static ItemStack ic2Wrench;
    public static Logger FMLog;
    public static final boolean DEBUG = false;
    public static boolean REQUIRE_NO_POWER;
    public static SimpleNetworkWrapper NETWORK;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //FMLog.setParent(FMLLog.getLogger());
        instance = this;
        CONFIGURATION.load();
        blockConveyorBelt = new BlockConveyorBelt();
        blockManipulator = new BlockManipulator();
        blockCrate = new BlockCrate();
        blockImprinter = new BlockImprinter();
        blockDetector = new BlockDetector();
        blockRejector = new BlockRejector();
        blockEncoder = new BlockEncoder();
        blockArmbot = new BlockArmbot();
        blockMulti = new BlockMulti();
        blockCraneController = new BlockCraneController();
        blockCraneFrame = new BlockCraneFrame();
        blockTurntable = new BlockTurntable();
        itemImprint = new ItemImprinter();
        itemDisk = new ItemDisk();
        REQUIRE_NO_POWER = !CONFIGURATION.get("general", "requirePower", true).getBoolean(true);
        CONFIGURATION.save();
        NetworkRegistry.INSTANCE.registerGuiHandler((Object)this, (IGuiHandler)proxy);
        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        NETWORK.registerMessage(new MessageEncoderHandler(), MessageEncoder.class, 0, Side.SERVER);
        GameRegistry.registerBlock(blockConveyorBelt, "conveyor_belt");
        GameRegistry.registerBlock(blockCrate, ItemBlockCrate.class, "crate");
        GameRegistry.registerBlock(blockManipulator, "manipulator");
        GameRegistry.registerBlock(blockImprinter, "imprinter");
        GameRegistry.registerBlock(blockEncoder, "encoder");
        GameRegistry.registerBlock(blockDetector, "detector");
        GameRegistry.registerBlock(blockRejector, "rejector");
        GameRegistry.registerBlock(blockArmbot, "armbot");
        GameRegistry.registerBlock(blockTurntable, "turntable");
        GameRegistry.registerBlock(blockCraneController, "crane_controller");
        GameRegistry.registerBlock(blockCraneFrame, "crane_frame");
        GameRegistry.registerItem(itemDisk, "disk");
        GameRegistry.registerItem(itemImprint, "imprint");
        TabAssemblyLine.itemStack = new ItemStack(blockConveyorBelt);
        proxy.preInit();
    }

    @EventHandler
    public void load(FMLInitializationEvent evt) {
        proxy.init();
        GrabDictionary.registerList();
        FMLog.info("Loaded: " + TranslationHelper.loadLanguages(LANGUAGE_PATH, LANGUAGES_SUPPORTED) + " languages.");
        AssemblyLine.meta.modId = "AssemblyLine";
        AssemblyLine.meta.name = MOD_NAME;
        AssemblyLine.meta.description = "A mod that brings conveyor belt transporting systems to Minecraft.";
        AssemblyLine.meta.url = "http://calclavia.com/universalelectricity/?m=18";
        AssemblyLine.meta.logoFile = "/al_logo.png";
        AssemblyLine.meta.version = VERSION;
        AssemblyLine.meta.authorList = Arrays.asList("DarkGuardsman, Briaman, Calclavia");
        AssemblyLine.meta.credits = "Please see the website.";
        AssemblyLine.meta.autogenerated = false;
        this.createStandardRecipes();
        this.createUERecipes();
    }

    private void createUERecipes() {
        System.out.println("BasicComponents Found...adding UE recipes for Assembly Line.");
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(blockArmbot, new Object[]{"II ", "SIS", "MCM", Character.valueOf('S'), "plateSteel", Character.valueOf('C'), "advancedCircuit", Character.valueOf('I'), "ingotSteel", Character.valueOf('M'), "motor"}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(itemDisk, new Object[]{"III", "ICI", "III", Character.valueOf('I'), itemImprint, Character.valueOf('C'), "advancedCircuit"}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(blockEncoder, new Object[]{"SIS", "SCS", "SSS", Character.valueOf('I'), itemImprint, Character.valueOf('S'), "ingotSteel", Character.valueOf('C'), "advancedCircuit"}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(blockDetector, new Object[]{"SES", "SCS", "S S", Character.valueOf('S'), "ingotSteel", Character.valueOf('C'), "basicCircuit", Character.valueOf('E'), Items.ender_eye}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(blockConveyorBelt, 10), new Object[]{"III", "WMW", Character.valueOf('I'), "ingotSteel", Character.valueOf('W'), Blocks.planks, Character.valueOf('M'), "motor"}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(blockRejector, new Object[]{"WPW", "@R@", Character.valueOf('@'), "ingotSteel", Character.valueOf('R'), Items.redstone, Character.valueOf('P'), Blocks.piston, Character.valueOf('C'), "basicCircuit", Character.valueOf('W'), "copperWire"}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(blockTurntable, new Object[]{"M", "P", Character.valueOf('M'), "motor", Character.valueOf('P'), Blocks.piston}));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(new ItemStack(blockManipulator, 2), new Object[]{Blocks.dispenser, "basicCircuit"}));
    }

    private void createStandardRecipes() {
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(itemImprint, 2), new Object[]{"R", "P", "I", Character.valueOf('P'), Items.paper, Character.valueOf('R'), Items.redstone, Character.valueOf('I'), new ItemStack(Items.dye, 1, 0)}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(blockImprinter, new Object[]{"SIS", "SPS", "WCW", Character.valueOf('S'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest, Character.valueOf('W'), Blocks.crafting_table, Character.valueOf('P'), Blocks.piston, Character.valueOf('I'), new ItemStack(Items.dye, 1, 0)}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(blockCrate, 1, 0), new Object[]{"TST", "S S", "TST", Character.valueOf('S'), Items.iron_ingot, Character.valueOf('T'), Blocks.log}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(blockCrate, 1, 1), new Object[]{"TST", "SCS", "TST", Character.valueOf('C'), new ItemStack(blockCrate, 1, 0), Character.valueOf('S'), Items.iron_ingot, Character.valueOf('T'), Blocks.log}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(blockCrate, 1, 2), new Object[]{"TST", "SCS", "TST", Character.valueOf('C'), new ItemStack(blockCrate, 1, 1), Character.valueOf('S'), Items.iron_ingot, Character.valueOf('T'), Blocks.log}));
    }

    public static void printSidedData(String data) {
        System.out.print(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT ? "[C]" : "[S]");
        System.out.println(" " + data);
    }

    static {
        LANGUAGES_SUPPORTED = new String[]{"en_US"};
        CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "UniversalElectricity/AssemblyLine.cfg"));
        ic2Wrench = null;
        FMLog = Logger.getLogger(MOD_NAME);
        REQUIRE_NO_POWER = false;
    }
}

