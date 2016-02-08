package villagerWipe.core.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class CommandXP implements ICommand
{ 
	private final List<String> aliases;

	protected String fullEntityName; 
	protected Entity conjuredEntity; 
	private int Radius = 0;

	public CommandXP() 
	{ 
		aliases = new ArrayList<String>(); 

		aliases.add("xp"); 

	} 

	@Override 
	public int compareTo(Object o)
	{ 
		return 0; 

	} 

	@Override 
	public String getCommandName() 
	{ 
		return "GiveXP"; 

	} 

	@Override         
	public String getCommandUsage(ICommandSender var1) 
	{ 
		return "/GiveXP"; 

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
			if (W.isRemote) { 
				System.out.println("Not processing on Client side"); 
			} 
			else { 
				System.out.println("Processing on Server side - maximum 500 levels engaged by: "+P.getDisplayName()); 
				P.addExperienceLevel(500);
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

