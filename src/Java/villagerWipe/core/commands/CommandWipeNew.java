package villagerWipe.core.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import villagerWipe.core.util.FindEntity;
import villagerWipe.core.util.Utils;


public class CommandWipeNew implements ICommand
{ 
	private final List<String> aliases;

	protected String fullEntityName; 
	protected Entity conjuredEntity; 
	private int Radius = 0;
	private String mob;
	private int minDis = 0;

	public CommandWipeNew() 
	{ 
		aliases = new ArrayList<String>(); 

		aliases.add("findE"); 

		aliases.add("FE"); 

	} 

	@Override 
	public int compareTo(Object o)
	{ 
		return 0; 

	} 

	@Override 
	public String getCommandName() 
	{ 
		return "find"; 

	} 

	@Override         
	public String getCommandUsage(ICommandSender var1) 
	{ 
		return "/find [Wipe Distance] [Entity Name]"; 

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
					Utils.sendChatToPlayer(P, "Please enter a number after the command");
					Radius = 250;
					Utils.LOG_INFO("Setting value to 250 as default.");
				}
				else if (!argString[0].equals(null) || argString.length > 0){
					String commandValue = argString[0];
					if (!commandValue.equals(null)){

						try {
							Radius = Integer.parseInt(commandValue);
							Utils.LOG_INFO("Setting value to "+Radius);
						} catch (NumberFormatException e) {
							Utils.sendChatToPlayer(P, "Please enter a number after the command");
							Radius = 250;
							Utils.LOG_INFO("Setting value to 250 as default.");
						}
						//Utils.LOG_INFO("Command Value: "+commandValue);
					}
					else if (commandValue.equals(null)){
						Utils.LOG_INFO("No Value Given.");
					}
				}

				else {
					//
				}
				
				//Decode Entity Name
				try {			  	
					if (argString[1].equals(null) && argString.length <= 1){
						//ERROR
					}
					else if (!argString[1].equals(null) || argString.length > 1){
						String commandValue1 = argString[1];
						if (!commandValue1.equals(null)){

							try {
								mob = commandValue1;
								Utils.LOG_INFO("Setting Entity value to "+mob);
							} catch (NumberFormatException e) {
								Utils.sendChatToPlayer(P, "Please enter a name after the command");
								//Radius = 250;
								//Utils.LOG_INFO("Setting value to 250 as default.");
							}
							//Utils.LOG_INFO("Command Value: "+commandValue);
						}
						else if (commandValue1.equals(null)){
							Utils.LOG_INFO("No Value Given.");
						}
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					Utils.sendChatToPlayer(P, "Please enter a number after the command, do not leave it blank");
					Utils.LOG_INFO("Please enter a number after the command, do not leave it blank");
				}
				
				//minimum space between mobs
				try {			  	
					if (argString[2].equals(null) && argString.length <= 2){
						//ERROR
					}
					else if (!argString[1].equals(null) || argString.length > 2){
						String commandValue1 = argString[2];
						if (!commandValue1.equals(null)){

							try {
								minDis = Integer.parseInt(commandValue1);
								Utils.LOG_INFO("Setting Minimum distance value to "+minDis);
							} catch (NumberFormatException e) {
								Utils.sendChatToPlayer(P, "Please enter a minimum entity space after the name");
								//Radius = 250;
								//Utils.LOG_INFO("Setting value to 250 as default.");
							}
							//Utils.LOG_INFO("Command Value: "+commandValue);
						}
						else if (commandValue1.equals(null)){
							Utils.LOG_INFO("No Value Given.");
						}
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					Utils.sendChatToPlayer(P, "Please enter a minimum entity space after the name");
					Utils.LOG_INFO("Please enter a minimum entity space after the name");
				}

				if (W.isRemote) { 
					Utils.LOG_INFO("Not processing on Client side"); 
				} 
				else { 
					Utils.LOG_INFO("Processing on Server side - Villager/RfTools Sequencer Wipe engaged by: "+P.getDisplayName()); 
					ChunkCoordinates X = P.getPlayerCoordinates();
					Utils.LOG_WARNING("Player Location: "+X);
					 //Insert Code
					FindEntity.get(S, W, mob, Radius, minDis);
					
					
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

