package villagerWipe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.minecraftforge.common.MinecraftForge;
import villagerWipe.core.commands.CommandFindTE;
import villagerWipe.core.common.CommonProxy;
import villagerWipe.core.lib.Strings;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid=Strings.MODID, name=Strings.name, version=Strings.VERSION, acceptableRemoteVersions = "*")
public class VillagerWipe
implements ActionListener
{ 
	
	@Mod.Instance(Strings.MODID)
	public static VillagerWipe instance;

	@SidedProxy(clientSide="TEDUMPER.core.proxy.ClientProxy", serverSide="TEDUMPER.core.proxy.ServerProxy")
	public static CommonProxy proxy;


	//Pre-Init
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}

	//Init
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	//Post-Init
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandFindTE());

	}

	@Mod.EventHandler
	public void serverStopping(FMLServerStoppingEvent event){
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

}
