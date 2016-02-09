package villagerWipe.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import villagerWipe.core.common.EntityDataStorage;
import villagerWipe.core.lib.Strings;
import villagerWipe.core.util.dynamicswitch.Statement;

public class FindEntity {
	
	private static Class Cl = null;

	public static boolean get(ICommandSender S, World W, String Classname, int Dis, int minDis){
		//Vars
		ArrayList<Entity> foundMobs = new ArrayList<Entity>();
		ArrayList<EntityDataStorage> MobData = new ArrayList<EntityDataStorage>();
		ArrayList<EntityDataStorage> checkingMobData = new ArrayList<EntityDataStorage>();
		Cl = null;
		EntityPlayer P = Strings.C.getPlayer(S);
		int kills = 0;
		int aliveMobs = 0;
		String CN = Classname.toLowerCase();
		Map xMap = EntityList.classToStringMapping;
		//List<String> list = new ArrayList<String>(xMap.values());
		Set<Entry<String, String>> set = xMap.entrySet(); 
		List<Entry<String, String>> activeEntityList = new ArrayList<>(set); 
		int loadedCount = activeEntityList.size();
		Statement loadedEntityList = new Statement(loadedCount);
		Iterator<Entry<String, String>> it = activeEntityList.iterator();
		int itCount = 0;
		
		
		while (it.hasNext()) { 
			itCount++;
			Entry<String, String> entry = it.next(); 
			//String ENTITY_CLASS = entry.getValue();
			
			String F1 = entry.toString().replaceAll(".*class ", "");
			String F2 = F1.split("=", 2)[0];
			Class ENTITY_CLASS = null;
			try {
				ENTITY_CLASS = Class.forName(F2);
				Utils.LOG_INFO("Found Entity with Class name: "+F2);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			String ENTITY_NAME = entry.toString().replaceAll(".*=", "");
			//System.out.println("Entry from converted list : " + entry.toString().replaceAll(".*=", ""));
			loadedEntityList.addCase(itCount, ENTITY_NAME, ENTITY_CLASS);
			
			}
		
		try {
			Cl = Class.forName(loadedEntityList.executeSwitch(CN));
			Utils.LOG_INFO("Using Entity Class: "+Cl.getSimpleName());
		} catch (NoSuchMethodError | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if (Cl.equals(null)){
			try {
				Cl = Class.forName(loadedEntityList.executeSwitch(CN));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if (!Cl.equals(null)){
			System.out.println("You're looking up: "+CN);
			foundMobs = EntityFilter(P, W, Cl, Dis);
		}
		else {
			foundMobs = null;
		}

		if (foundMobs.size() > 0) {
			for (int i = 0; i <= foundMobs.size() - 1; i++) {
				Entity em = (Entity) foundMobs.get(i);
				int x = (int) em.posX;
				int y = (int) em.posY;
				int z = (int) em.posZ;
				String name = em.getClass().getName().toLowerCase();
				Entity mobInstance = em;

				//You can create a ticket like this
				EntityDataStorage NPC = new EntityDataStorage();
				NPC.setID(em.getUniqueID());
				NPC.setName(name);
				NPC.setMob(mobInstance);
				NPC.setX(x);
				NPC.setY(y);
				NPC.setZ(z);
				MobData.add(NPC);

				//TODO ADD What happens to found mobs from class.
			}

			// iterate via "iterator loop"
			Utils.LOG_INFO("Starting Entity Math");
			Iterator<EntityDataStorage> Iterator = MobData.iterator();
			aliveMobs = MobData.size();
			while (Iterator.hasNext()){
				try{
					EntityDataStorage R = Iterator.next();
					Utils.LOG_INFO("Name: ["+R.getName()+"] UUID: ["+R.getId()+"] X: ["+R.getX()+"] Y: ["+R.getY()+"] Z: ["+R.getZ()+"]");
					Utils.LOG_INFO("STEP: 1");
					Iterator<EntityDataStorage> Iterator2 = MobData.iterator();
					while (Iterator2.hasNext() && !Iterator2.next().getMob().equals(null)){
						try{
							Utils.LOG_INFO("STEP: 2");
							EntityDataStorage R2 = Iterator2.next();
							double distance = entityMath(R.getMob(), R2.getMob());
							int R5 = new Random().nextInt(20);
							if (!R2.equals(R)){
								if (distance < minDis || distance < 10){
									Utils.LOG_INFO("Distance too small - Removing");
									Utils.LOG_INFO("Killing Name: ["+R2.getName()+"] UUID: ["+R2.getId()+"] X: ["+R2.getX()+"] Y: ["+R2.getY()+"] Z: ["+R2.getZ()+"]");
									Utils.LOG_WARNING("removing "+R2.getMob().getEntityId());
									MobData.remove(R2.getMob());
									Utils.LOG_WARNING("removing "+R2.getId());
									MobData.remove(R2.getId());
									R2.getMob().setDead();
									//Iterator2.remove();
									kills++;
								}

								else if (R5 > 2){
									Utils.LOG_INFO("Bad Luck Roll - Removing");
									Utils.LOG_INFO("Killing Name: ["+R2.getName()+"] UUID: ["+R2.getId()+"] X: ["+R2.getX()+"] Y: ["+R2.getY()+"] Z: ["+R2.getZ()+"]");
									Utils.LOG_WARNING("removing "+R2.getMob().getEntityId());
									MobData.remove(R2.getMob());
									Utils.LOG_WARNING("removing "+R2.getId());
									MobData.remove(R2.getId());
									R2.getMob().setDead();
									//Iterator2.remove();
									kills++;
								}
							}
							else {
								Utils.LOG_INFO("Skipping Check - Entity Trying to check distance from itself.");
								break;
							}
							

					}catch(NoSuchElementException e) {  }
				}
			}catch(NoSuchElementException e) {  }
		}



	} 
	else {
		return false;
	}		

	ArrayList<Entity> checkingfoundMobs = EntityFilter(P, W, Cl, Dis);
	if (checkingfoundMobs.size() > 0) {
		//ArrayList<EntityDataStorage> checkingMobData = new ArrayList<EntityDataStorage>();
		for (int i = 0; i <= checkingfoundMobs.size() - 1; i++) {
			Entity em = (Entity) checkingfoundMobs.get(i);
			int x = (int) em.posX;
			int y = (int) em.posY;
			int z = (int) em.posZ;
			String name = em.getClass().getName().toLowerCase();
			Entity mobInstance = em;

			//You can create a ticket like this
			EntityDataStorage checkingNPC = new EntityDataStorage();
			checkingNPC.setID(em.getUniqueID());
			checkingNPC.setName(name);
			checkingNPC.setMob(mobInstance);
			checkingNPC.setX(x);
			checkingNPC.setY(y);
			checkingNPC.setZ(z);
			checkingMobData.add(checkingNPC);

			//TODO ADD What happens to found mobs from class.
		}
	}
	int newaliveMobs = checkingMobData.size();
	Utils.sendChatToPlayer(P, "Kills: "+String.valueOf(kills)+" || "+String.valueOf(aliveMobs-newaliveMobs));

	// TODO Gotta set a variable to be the filtered entitity list so it's all dynamic.
	/*Set<EntityDataStorage> opened = new LinkedHashSet<EntityDataStorage>();
		List<EntityDataStorage> list = MobData;
		opened.addAll(list);*/


	return true;
}
	
	  private static class CurrentClassGetter extends SecurityManager {
		    public String getClassName(String S) {
		      return getClassContext()[1].getSimpleName(); 
		                                             
		    }
		  }

//Sorts loaded entities to get the one you want, after making a call to the loadAll funct.
//@SuppressWarnings("null")
private static ArrayList<Entity> EntityFilter(EntityPlayer S, World W, Class Cl, int Dis){
	ArrayList<Entity> AL = EntitiesLoaded(S, W, Cl, Dis);
	ArrayList<Entity> NPCfound = new ArrayList<Entity>();
	//NPCfound.clear();
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
private static ArrayList<Entity> EntitiesLoaded(EntityPlayer P, World W, Class Cl, int Dis){
	int Radius = Dis;
	return (ArrayList<Entity>) P.worldObj.getEntitiesWithinAABB(Cl, AxisAlignedBB.getBoundingBox(P.posX-Radius, P.posY-Radius, P.posZ-Radius, (P.posX + Radius),(P.posY + Radius),(P.posZ + Radius)));
}

private static double entityMath(Entity E1, Entity E2){
	//Utils.LOG_ERROR("Starting math function");
	int x1 = (int) E1.posX;
	//Utils.LOG_WARNING("X1: "+x1);
	int x2 = (int) E2.posX;
	//Utils.LOG_WARNING("X2: "+x2);
	int y1 = (int) E1.posY;
	//Utils.LOG_WARNING("Y1: "+y1);
	int y2 = (int) E2.posY;
	//Utils.LOG_WARNING("Y2: "+y2);
	int z1 = (int) E1.posZ;
	//Utils.LOG_WARNING("Z1: "+z1);
	int z2 = (int) E2.posZ;
	//Utils.LOG_WARNING("Z2: "+z2);


	double d = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1));
	//Utils.LOG_WARNING("Distance to : "+E2.getClass().getName()+" was "+(int) d+" blocks.");
	return d;
}
/**
 * also (optimization) if the function remains symmetric (except for kill and remove) you only need to walk iterator 2 until it points to the same element as iterator 1
 * so... in (pseudo)code: for(it1 = MobData.iterator(); it1.hasNext();) { bool kill = false; for(it2 = Mobdata.iterator(); it2 != it1; it2.next()) {if(condition) {kill = true; break;}} if(kill) {it1.kill(); it1.remove()}}
 * <equod> i tend to avoid writing java... in c++ its iterator++ and then check iterator != collection.end()
 * <equod> well, check the javadoc if hasnext advances the iterator ;)
 * 
 */
	
}
