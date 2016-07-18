package villagerWipe.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

		if (foundMobs.size() > 0) {
			for (int i = 0; i <= foundMobs.size() - 1; i++) {
				Entity em = (Entity) foundMobs.get(i);
				int x = (int) em.posX;
				int y = (int) em.posY;
				int z = (int) em.posZ;
				String name = em.getClass().getName().toLowerCase();
				Entity mobInstance = em;

				Utils.LOG_INFO("Found "+name+" at x:"+x+" y:"+y+" z:"+z);

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

			try {

				//Iterator<EntityDataStorage> itr = MobData.iterator();
				// remove all mobs close to each other
				//while (itr.hasNext()) {
					for(int i=0; i<MobData.size(); i++){
						//EntityDataStorage temp = itr.next();
						for(int j=i + 1; j<MobData.size(); j++){
							if(MobData.get(i) != MobData.get(j)){
								if (entityMath(MobData.get(i).getMob(), MobData.get(j).getMob()) < minDis){
									//itr.remove();
									MobData.get(j).getMob().setDead();
									kills++;
								}					    	
							}
						}
					}
				//}
			} catch(Throwable e){

			}
		} 
		else {
			return false;
		}		

		/*ArrayList<Entity> checkingfoundMobs = EntityFilter(P, W, Cl, Dis);
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
			}
		}*/

		Utils.sendChatToPlayer(P, "Kills: "+String.valueOf(kills)+" || There should be "+String.valueOf(aliveMobs-kills)+" left.");

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
		int x1 = (int) E1.posX;
		int x2 = (int) E2.posX;
		int y1 = (int) E1.posY;
		int y2 = (int) E2.posY;
		int z1 = (int) E1.posZ;
		int z2 = (int) E2.posZ;
		double d = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1));
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
