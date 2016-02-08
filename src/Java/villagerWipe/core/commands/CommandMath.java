package villagerWipe.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import villagerWipe.core.util.Utils;


public class CommandMath implements ICommand
{ 
	private final List<String> aliases;

	protected String fullEntityName; 
	protected Entity conjuredEntity; 
	private int Radius = 0;

	public CommandMath() 
	{ 
		aliases = new ArrayList<String>(); 

		aliases.add("slaughter"); 

		aliases.add("wipe"); 

	} 

	@Override 
	public int compareTo(Object o)
	{ 
		return 0; 

	} 

	@Override 
	public String getCommandName() 
	{ 
		return "Wipe"; 

	} 

	@Override         
	public String getCommandUsage(ICommandSender var1) 
	{ 
		return "/wipe [Wipe Distance]"; 

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

				if (W.isRemote) { 
					Utils.LOG_INFO("Not processing on Client side"); 
				} 
				else { 
					Utils.LOG_INFO("Processing on Server side - Villager/RfTools Sequencer Wipe engaged by: "+P.getDisplayName()); 
					ChunkCoordinates X = P.getPlayerCoordinates();
					Utils.LOG_WARNING("Player Location: "+X);
					@SuppressWarnings("unchecked")
					List<EntityVillager> e = P.worldObj.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getBoundingBox(P.posX-Radius, P.posY-Radius, P.posZ-Radius, (P.posX + Radius),(P.posY + Radius),(P.posZ + Radius)));
					if (e.size() > 0) {
						int kills = 0;
						for (int i = 0; i <= e.size() - 1; i++) {
							EntityVillager em = (EntityVillager) e.get(i);
							//Utils.LOG_INFO(em.toString());	

							if (em.hasCustomNameTag()){
								Utils.sendChatToPlayer(P, "Found a player/custom named Villager, "+em.getCustomNameTag()+", it will be ignored. [x:"+(int) em.posX+"][y:"+(int) em.posY+"][z:"+(int) em.posZ+"]");
								Utils.LOG_INFO("Found a player/custom named Villager, "+em.getCustomNameTag()+", it will be ignored. [x:"+(int) em.posX+"][y:"+(int) em.posY+"][z:"+(int) em.posZ+"]");
							}
							else {
								int R2 = new Random().nextInt(20);
								if (R2 > 1){
									em.setHealth(0);
									kills++;
								}
							}
						}    
						Utils.sendChatToPlayer(P, "Kills: "+String.valueOf(kills));
						Utils.LOG_INFO("Kills: "+String.valueOf(kills));
					} 


					@SuppressWarnings("unchecked")
					//List<TileEntity> e2 = P.worldObj.getEntitiesWithinAABB(mcjty.rftools.blocks.logic.SequencerTileEntity.class, AxisAlignedBB.getBoundingBox(P.posX-75, P.posY-75, P.posZ-75, (P.posX + 75),(P.posY + 75),(P.posZ + 75)));
					List<TileEntity> allTEs = P.worldObj.loadedTileEntityList;
					//Utils.LOG_INFO(allTEs.toString());
					if (e.size() > 0) {
						int RemovedBlocks = 0;				
						for (int i = 0; i <= allTEs.size() - 1; i++) {
							TileEntity em = (TileEntity) allTEs.get(i);

							if (em.getClass().getName().equals("mcjty.rftools.blocks.logic.SequencerTileEntity")){
								int x = em.xCoord;
								int y = em.yCoord;
								int z = em.zCoord;
								Utils.LOG_INFO("Removing "+em.getClass().getName()+" block at: [x:"+em.xCoord+"][y:"+em.yCoord+"][z:"+em.zCoord+"]");	
								P.worldObj.setBlockToAir(x, y, z);
								RemovedBlocks++;
							}
							//Utils.LOG_INFO(em.toString());	   	
						} 
						Utils.sendChatToPlayer(P, "Removed Blocks: "+String.valueOf(RemovedBlocks));
						Utils.LOG_INFO("Removed Blocks: "+String.valueOf(RemovedBlocks));
					}
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

