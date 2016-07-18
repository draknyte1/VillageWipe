package villagerWipe.core.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import villagerWipe.core.util.FindTileEntity;
import villagerWipe.core.util.Utils;


public class CommandFindTE implements ICommand
{ 
	private final List<String> aliases;

	private int Radius = 0;

	public CommandFindTE() 
	{ 
		aliases = new ArrayList<String>(); 

		aliases.add("findTE"); 

		aliases.add("FTE"); 

	} 

	@Override 
	public int compareTo(Object o)
	{ 
		return 0; 

	} 

	@Override 
	public String getCommandName() 
	{ 
		return "findTE"; 

	} 

	@Override         
	public String getCommandUsage(ICommandSender var1) 
	{ 
		return "/findTE [Wipe Distance] [Entity Name]"; 

	} 

	@Override 
	public List<String> getCommandAliases() 
	{ 
		return this.aliases;

	} 

	public void processCommand(ICommandSender S, String[] argString)
	{ 
		World W = S.getEntityWorld(); 
		CommandUtils C = new CommandUtils();
		EntityPlayer P = C.getPlayer(S);
		//System.out.println(P.getCommandSenderName());
		//System.out.println(P.getDisplayName());

		if (MinecraftServer.getServer().getConfigurationManager().func_152596_g(P.getGameProfile())){
			try {			  	
				if (argString[0].equals(null) && argString.length <= 0){
					Utils.sendChatToPlayer(P, "Please enter a number after the command, Running with 250m default.");
					Radius = 250;
					Utils.LOG_INFO("Setting value to 250 as default.");
				}
				else if (!argString[0].equals(null) || argString.length > 0){
					String commandValue = argString[0];
					if (!commandValue.equals(null)){

						try {
							Radius = Integer.parseInt(commandValue);
							Utils.LOG_INFO("Setting search radius value to "+Radius);
						} catch (NumberFormatException e) {
							Utils.sendChatToPlayer(P, "Please enter a number after the command, Running with 250m default.");
							Radius = 250;
							Utils.LOG_INFO("Setting value to 250 as default.");
						}
						//Utils.LOG_INFO("Command Value: "+commandValue);
					}
					else if (commandValue.equals(null)){
						Utils.LOG_INFO("No Value Given.");
					}
				}

				if (W.isRemote) { 
					Utils.LOG_INFO("Not processing on Client side"); 
				} 
				else { 
					Utils.LOG_INFO("Processing on Server side - Villager/RfTools Sequencer Wipe engaged by: "+P.getDisplayName()); 
					ChunkCoordinates X = P.getPlayerCoordinates();
					Utils.LOG_WARNING("Player Location: "+X);
					 //Insert Code
					FindTileEntity.get(S, W, Radius);
					
					
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				Utils.sendChatToPlayer(P, "Please enter a number after the command, do not leave it blank");
				Utils.LOG_INFO("Please enter a number after the command, do not leave it blank");
			}
		}
		else {
			Utils.sendChatToPlayer(P, "You are not allowed to use this command.");
		}
	} 

	@Override 
	public boolean canCommandSenderUseCommand(ICommandSender var1) 
	{ 
		return true;

	} 

	@Override  
	public List<?> addTabCompletionOptions(ICommandSender var1, String[] var2) 
	{ 
		// TODO Auto-generated method stub 

		return null; 

	} 

	@Override 
	public boolean isUsernameIndex(String[] var1, int var2) 
	{ 
		// TODO Auto-generated method stub 

		return false;

	}

	public boolean playerUsesCommand(World W, EntityPlayer P, int cost)
	{


		return true;
	}

}

