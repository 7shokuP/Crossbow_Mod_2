package micdoodle8.mods.crossbowmod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Achievement;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraftforge.common.AchievementPage;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid = "CrossbowMod2", name = "Crossbow Mod 2 1.3.2", version = "v1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, connectionHandler = ConnectionHandler.class)
public class CrossbowModCore 
{
	@SidedProxy(clientSide = "micdoodle8.mods.crossbowmod.ClientProxy", serverSide = "micdoodle8.mods.crossbowmod.CommonProxy")
	public static CommonProxy proxy;
	@Instance
	public static CrossbowModCore instance;
	
	public static CrossbowModLocalization lang;
	
	public static long firstBootTime = System.currentTimeMillis();
	
	private static long hasBooted;
	
	public static List crossbowsList = new ArrayList();
	public static List woodenCrossbowsList = new ArrayList();
	public static List stoneCrossbowsList = new ArrayList();
	public static List ironCrossbowsList = new ArrayList();
	public static List goldCrossbowsList = new ArrayList();
	public static List diamondCrossbowsList = new ArrayList();
	
	public static final CreativeTabs crossbowTab = new CreativeTabCrossbows("crossbows");

	public static Achievement createBench;
	public static Achievement createCrossbow;
	public static Achievement sniper;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		new ConfigManager(event);
		
		proxy.preInit(event);
	}
	
	@Init
	public void load(FMLInitializationEvent event)
	{
		new Items();
		
		EntityRegistry.registerModEntity(EntityWoodBolt.class, "CB_WoodBolt", ConfigManager.idEntityWoodCrossbow, this, 64, 4, true);
		EntityRegistry.registerModEntity(EntityStoneBolt.class, "CB_StoneBolt", ConfigManager.idEntityStoneCrossbow, this, 64, 4, true);
		EntityRegistry.registerModEntity(EntityIronBolt.class, "CB_IronBolt", ConfigManager.idEntityIronCrossbow, this, 64, 4, true);
		EntityRegistry.registerModEntity(EntityGoldBolt.class, "CB_GoldBolt", ConfigManager.idEntityGoldCrossbow, this, 64, 4, true);
		EntityRegistry.registerModEntity(EntityDiamondBolt.class, "CB_DiamondBolt", ConfigManager.idEntityDiamondCrossbow, this, 64, 4, true);
		
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		NetworkRegistry.instance().registerChannel(new ServerPacketHandler(), "CrossbowMod", Side.SERVER);
		
		GameRegistry.registerBlock(Items.crossbowBench);
		
		this.lang = new CrossbowModLocalization("Mic'sMods/CrossbowMod");
		
		Items.addNames();
		
		Util.addRecipes();
		
		createBench = new Achievement(491, "CreateBench", 0, 0, Items.crossbowBench, (Achievement) null).registerAchievement();
		createCrossbow = new Achievement(492, "CreateCrossbow", 0, 2, Items.woodenCrossbowBase, createBench).registerAchievement();
		ItemStack stack = ItemCrossbow.setAttachmentAndMaterial(new ItemStack(Items.diamondCrossbowBase), EnumAttachmentType.longscope, EnumCrossbowMaterial.diamond, EnumCrossbowFireRate.none);
		sniper = new Achievement(493, "Sniper", 2, 3, stack, createCrossbow).setSpecial().registerAchievement();
		
		AchievementPage.registerAchievementPage(new AchievementPage("Crossbow Mod", createBench, createCrossbow, sniper));

		proxy.load(event);
		
		proxy.registerRenderInformation();
	}
	
	public class GuiHandler implements IGuiHandler
	{
		@Override
		public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
		{
			if (!world.blockExists(x, y, z))
			{
				return null;
			}
			
			int blockID = world.getBlockId(x, y, z);

			if (ID == ConfigManager.GUIID_BlockCrossbowBench)
			{
				if (!(blockID == Items.crossbowBench.blockID))
				{
					return null;
				}
				else
				{
					return new ContainerCrossbowBench(player.inventory);
				}
			}
			else
			{
				return null;
			}
		}

		@Override
		public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
		{
			if (!world.blockExists(x, y, z))
			{
				return null;
			}
			
			int blockID = world.getBlockId(x, y, z);

			if (ID == ConfigManager.GUIID_BlockCrossbowBench)
			{
				if (!(blockID == Items.crossbowBench.blockID))
				{
					return null;
				}
				else
				{
					return new GuiCrossbowBench(player.inventory);
				}
			}
			else
			{
				return null;
			}
		}
	}
	
	public class ServerPacketHandler implements IPacketHandler
	{
		@Override
		public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
		{
			CrossbowModServer.onPacketData(manager, packet, player);
		}
	}
}
