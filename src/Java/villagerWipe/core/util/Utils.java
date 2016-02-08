package villagerWipe.core.util;

import java.util.ArrayList;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import villagerWipe.core.commands.CommandUtils;
import villagerWipe.core.lib.Strings;
import cpw.mods.fml.common.FMLLog;

public class Utils {

	//Non-Dev Comments 
	public static void LOG_INFO(String s){
		//if (Strings.DEBUG){
		FMLLog.info("MiscUtils: "+s);
		//}
	}

	//Developer Comments
	public static void LOG_WARNING(String s){
		if (Strings.DEBUG){
			FMLLog.warning("MiscUtils: "+s);
		}
	}

	//Errors
	public static void LOG_ERROR(String s){
		if (Strings.DEBUG){
			FMLLog.severe("MiscUtils: "+s);
		}
	}

	public static void sendChatToPlayer(EntityPlayer aPlayer, String aChatMessage) {
		if (aPlayer != null && aPlayer instanceof EntityPlayerMP && aChatMessage != null) {
			aPlayer.addChatComponentMessage(new ChatComponentText(aChatMessage));
		}
	}

	public boolean findNPC(ICommandSender S, World W, String Classname, int Dis){
		Class Cl = null;
		String CN = Classname.toLowerCase();
		switch(CN)
		{
		//Non-Passive Mobs
		case "zombie" :
			Cl = net.minecraft.entity.monster.EntityZombie.class;
			break;
		case "skeleton" :
			Cl = net.minecraft.entity.monster.EntitySkeleton.class;
			break;
		case "witch" :
			Cl = net.minecraft.entity.monster.EntityWitch.class;
			break;
		case "blaze" :
			Cl = net.minecraft.entity.monster.EntityBlaze.class;
			break;
		case "cavespider" :
			Cl = net.minecraft.entity.monster.EntityCaveSpider.class;
			break;
		case "creeper" :
			Cl = net.minecraft.entity.monster.EntityCreeper.class;
			break;
		case "enderman" :
			Cl = net.minecraft.entity.monster.EntityEnderman.class;
			break;
		case "ghast" :
			Cl = net.minecraft.entity.monster.EntityGhast.class;
			break;
		case "irongolem" :
			Cl = net.minecraft.entity.monster.EntityIronGolem.class;
			break;
		case "magmacube" :
			Cl = net.minecraft.entity.monster.EntityMagmaCube.class;
			break;
		case "zombiepigman" :
			Cl = net.minecraft.entity.monster.EntityPigZombie.class;
			break;
		case "silverfish" :
			Cl = net.minecraft.entity.monster.EntitySilverfish.class;
			break;
		case "slime" :
			Cl = net.minecraft.entity.monster.EntitySlime.class;
			break;
		case "spider" :
			Cl = net.minecraft.entity.monster.EntitySpider.class;
			break;
		case "snowman" :
			Cl = net.minecraft.entity.monster.EntitySnowman.class;
			break;
		//Passive Mobs
		case "villager" :
			Cl = net.minecraft.entity.passive.EntityVillager.class;
			break;
		case "chicken" :
			Cl = net.minecraft.entity.passive.EntityChicken.class;
			break;
		case "cow" :
			Cl = net.minecraft.entity.passive.EntityCow.class;
			break;
		case "horse" :
			Cl = net.minecraft.entity.passive.EntityHorse.class;
			break;
		case "pig" :
			Cl = net.minecraft.entity.passive.EntityPig.class;
			break;
		case "sheep" :
			Cl = net.minecraft.entity.passive.EntitySheep.class;
			break;
		case "squid" :
			Cl = net.minecraft.entity.passive.EntitySquid.class;
			break;
		case "wolf" :
			Cl = net.minecraft.entity.passive.EntityWolf.class;
			break;
		default :
			System.out.println("Invalid Entity Specified.");
			Cl = null;
			break;
		}
		
		if (!Cl.equals(null)){
			System.out.println("You're looking up: "+CN);
		EntityFilter(S, W, Cl, Dis);
		}
		// TODO Gotta set a variable to be the filtered entitity list so it's all dynamic.
		
		
		
		
		
		
		
		
		
		
		else {
			return false;
		}
		return false;
	}

	//Sorts loaded entities to get the one you want, after making a call to the loadAll funct.
	@SuppressWarnings("null")
	private ArrayList<Entity> EntityFilter(ICommandSender S, World W, Class Cl, int Dis){
		ArrayList<Entity> AL = EntitiesLoaded(S, W, Cl, Dis);
		ArrayList<Entity> NPCfound = null;
		NPCfound.clear();
		if (AL.size() > 0) {
			for (int i = 0; i <= AL.size() - 1; i++) {
				Entity em = (Entity) AL.get(i);
				//Add npc name check to filter into new list
				if (em.getClass().equals(Cl)){					
				NPCfound.add(em);
				}
			}
		} 
		else {
			Utils.LOG_INFO("Couldn't find any entities? o.O");
			NPCfound.clear();
		}

		return NPCfound;		
	}

	//Find All Loaded Entities
	@SuppressWarnings("unchecked")
	private ArrayList<Entity> EntitiesLoaded(ICommandSender S, World W, Class Cl, int Dis){
		int Radius = Dis;
		CommandUtils C = new CommandUtils();
		EntityPlayer P = C.getPlayer(S);
		return (ArrayList<Entity>) P.worldObj.getEntitiesWithinAABB(Cl, AxisAlignedBB.getBoundingBox(P.posX-Radius, P.posY-Radius, P.posZ-Radius, (P.posX + Radius),(P.posY + Radius),(P.posZ + Radius)));
	}

	/*private int entityMath(Entity E1, Entity E2){

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

		return 0;		
	}*/

}
