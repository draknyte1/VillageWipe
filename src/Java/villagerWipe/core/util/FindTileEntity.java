package villagerWipe.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import villagerWipe.core.common.TileEntityDataStorage;
import villagerWipe.core.lib.Strings;

public class FindTileEntity {

	private static Class Cl = null;

	public static boolean get(ICommandSender S, World W, int Dis){
		//Vars
		ArrayList<TileEntity> foundTiles = new ArrayList<TileEntity>();
		ArrayList<TileEntityDataStorage> MobData = new ArrayList<TileEntityDataStorage>();
		ArrayList<TileEntityDataStorage> checkingMobData = new ArrayList<TileEntityDataStorage>();
		Cl = null;
		EntityPlayer P = Strings.C.getPlayer(S);
		int kills = 0;
		int aliveTiles = 0;
		List xList = W.loadedTileEntityList;
		Map<Integer,TileEntity> xMap = new HashMap<Integer,TileEntity>(); 
		
		
		EntityPlayer entityplayer = null;
		if(S instanceof EntityPlayer)
		{
		entityplayer = (EntityPlayer) S;
		}

		
		int thaumcraftNodesNearby = 0;
		int railcraftHiddenBlocksNearby = 0;
		int forestryBeeHivesNearby = 0;
		int mcChestsNearby = 0;
		int mcSpawnersNearby = 0;
		
		if(entityplayer != null)
		{
			for (int i = 0; i <= xList.size() -1; i++) {
				int ifCheck = (int) entityMath(entityplayer, (TileEntity) xList.get(i));
				if(ifCheck <= Dis && !xList.get(i).getClass().getName().contains("mods.railcraft.common.blocks.hidden.TileHidden")){
					Utils.LOG_INFO("Found "+xList.get(i)+", it was "+ifCheck+"m away.");
				}
				if(ifCheck <= Dis && xList.get(i).getClass().getName().contains("mods.railcraft.common.blocks.hidden.TileHidden")){
					railcraftHiddenBlocksNearby++;
				}
				if(ifCheck <= Dis && xList.get(i).getClass().getName().contains("thaumcraft.common.tiles.TileNode")){
					thaumcraftNodesNearby++;
				}
				if(ifCheck <= Dis && xList.get(i).getClass().getName().contains("forestry.apiculture.tiles.TileSwarm")){
					forestryBeeHivesNearby++;
				}
				if(ifCheck <= Dis && xList.get(i).getClass().getName().contains("net.minecraft.tileentity.TileEntityChest")){
					mcChestsNearby++;
				}
				if(ifCheck <= Dis && xList.get(i).getClass().getName().contains("net.minecraft.tileentity.TileEntityMobSpawner")){
					mcSpawnersNearby++;
				}
			}
			
			Utils.LOG_INFO("Found "+railcraftHiddenBlocksNearby+" Railcraft hidden air blocks");
			Utils.LOG_INFO("Found "+thaumcraftNodesNearby+" Thaumcraft Nodes");
			Utils.LOG_INFO("Found "+forestryBeeHivesNearby+" Forestry Beehives");
			Utils.LOG_INFO("Found "+mcChestsNearby+" Minecraft Chests");
			Utils.LOG_INFO("Found "+mcSpawnersNearby+" Minecraft Spawners");
			
		}
		
		
		return false;
		
		/*for (Object i : xList) {
			xMap.put(i.hashCode(),(TileEntity) i);
			int xCoord = ((TileEntity) i).xCoord;
			int yCoord = ((TileEntity) i).yCoord;
			int zCoord = ((TileEntity) i).zCoord;
			Utils.LOG_INFO("Found "+((TileEntity) i).getBlockType().getUnlocalizedName()+" at x:"+xCoord+" y:"+yCoord+" z:"+zCoord);
		}
		//List<String> list = new ArrayList<String>(xMap.values());
		Set<Entry<Integer, TileEntity>> set = xMap.entrySet();
		List<Entry<Integer, TileEntity>> activeTileEntityList = new ArrayList<>(set); 
		int loadedCount = activeTileEntityList.size();
		Statement loadedTileEntityList = new Statement(loadedCount);
		Iterator<Entry<Integer, TileEntity>> it = activeTileEntityList.iterator();
		int itCount = 0;


		while (it.hasNext()) { 
			itCount++;
			Entry<Integer, TileEntity> entry = it.next(); 
			//String ENTITY_CLASS = entry.getValue();

			String F1 = entry.toString().replaceAll(".*class ", "");
			String F2 = F1.split("=", 2)[0];
			Class ENTITY_CLASS = null;
			try {
				ENTITY_CLASS = Class.forName(F2);
				Utils.LOG_INFO("Found TileEntity with Class name: "+F2);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			String ENTITY_NAME = entry.toString().replaceAll(".*=", "");
			//System.out.println("Entry from converted list : " + entry.toString().replaceAll(".*=", ""));
			loadedTileEntityList.addCase(itCount, ENTITY_NAME, ENTITY_CLASS);

		}

		try {
			Cl = Class.forName(loadedTileEntityList.executeSwitch(CN));
			Utils.LOG_INFO("Using TileEntity Class: "+Cl.getSimpleName());
		} catch (NoSuchMethodError | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		if (Cl.equals(null)){
			try {
				Cl = Class.forName(loadedTileEntityList.executeSwitch(CN));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	

		if (!Cl.equals(null)){
			System.out.println("You're looking up: "+CN);
			foundTiles = TileEntityFilter(P, W, Cl, Dis);
		}

		if (foundTiles.size() > 0) {
			for (int i = 0; i <= foundTiles.size() - 1; i++) {
				TileEntity em = (TileEntity) foundTiles.get(i);
				int x = (int) em.xCoord;
				int y = (int) em.yCoord;
				int z = (int) em.zCoord;
				String name = em.getClass().getName().toLowerCase();
				TileEntity mobInstance = em;

				Utils.LOG_INFO("Found "+name+" at x:"+x+" y:"+y+" z:"+z);

				//You can create a ticket like this
				TileEntityDataStorage NPC = new TileEntityDataStorage();
				NPC.setID(generateUUIDforTE());
				NPC.setName(name);
				NPC.setMob(mobInstance);
				NPC.setX(x);
				NPC.setY(y);
				NPC.setZ(z);
				MobData.add(NPC);

				//TODO ADD What happens to found mobs from class.
			}

			try {

				//Iterator<TileEntityDataStorage> itr = MobData.iterator();
				// remove all mobs close to each other
				//while (itr.hasNext()) {
					for(int i=0; i<MobData.size(); i++){
						//TileEntityDataStorage temp = itr.next();
						for(int j=i + 1; j<MobData.size(); j++){
							if(MobData.get(i) != MobData.get(j)){
								if (entityMath(MobData.get(i).getTileEntity(), MobData.get(j).getTileEntity()) < 64){
									//itr.remove();
									Utils.LOG_INFO("Found two TEs close to each other. 1:"+MobData.get(i).getName()+" 2:"+MobData.get(j).getName());
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

		ArrayList<TileEntity> checkingfoundTiles = TileEntityFilter(P, W, Cl, Dis);
		if (checkingfoundTiles.size() > 0) {
			//ArrayList<TileEntityDataStorage> checkingMobData = new ArrayList<TileEntityDataStorage>();
			for (int i = 0; i <= checkingfoundTiles.size() - 1; i++) {
				TileEntity em = (TileEntity) checkingfoundTiles.get(i);
				int x = (int) em.xCoord;
				int y = (int) em.yCoord;
				int z = (int) em.zCoord;
				String name = em.getClass().getName().toLowerCase();
				TileEntity mobInstance = em;

				//You can create a ticket like this
				TileEntityDataStorage checkingNPC = new TileEntityDataStorage();
				checkingNPC.setID(generateUUIDforTE());
				checkingNPC.setName(name);
				checkingNPC.setMob(mobInstance);
				checkingNPC.setX(x);
				checkingNPC.setY(y);
				checkingNPC.setZ(z);
				checkingMobData.add(checkingNPC);
			}
		}

		Utils.sendChatToPlayer(P, "Kills: "+String.valueOf(kills)+" || There should be "+String.valueOf(aliveTiles-kills)+" left.");

		// TODO Gotta set a variable to be the filtered entitity list so it's all dynamic.
		Set<TileEntityDataStorage> opened = new LinkedHashSet<TileEntityDataStorage>();
		List<TileEntityDataStorage> list = MobData;
		opened.addAll(list);


		return true;*/
	}

	private static class CurrentClassGetter extends SecurityManager {
		public String getClassName(String S) {
			return getClassContext()[1].getSimpleName(); 

		}
	}
	
	private static UUID generateUUIDforTE(){
			   UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");		  
			   return uid.randomUUID();			   
	}

	//Sorts loaded entities to get the one you want, after making a call to the loadAll funct.
	//@SuppressWarnings("null")
	private static ArrayList<TileEntity> TileEntityFilter(EntityPlayer S, World W, Class Cl, int Dis){
		ArrayList<TileEntity> AL = EntitiesLoaded(S, W, Cl, Dis);
		ArrayList<TileEntity> NPCfound = new ArrayList<TileEntity>();
		//NPCfound.clear();
		if (AL.size() > 0) {
			for (int i = 0; i <= AL.size() - 1; i++) {
				TileEntity em = (TileEntity) AL.get(i);
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
	private static ArrayList<TileEntity> EntitiesLoaded(EntityPlayer P, World W, Class Cl, int Dis){
		int Radius = Dis;
		return (ArrayList<TileEntity>) P.worldObj.getEntitiesWithinAABB(Cl, AxisAlignedBB.getBoundingBox(P.posX-Radius, P.posY-Radius, P.posZ-Radius, (P.posX + Radius),(P.posY + Radius),(P.posZ + Radius)));
	}

	private static double entityMath(EntityPlayer E1, TileEntity E2){
		int x1 = (int) E1.posX;
		int x2 = (int) E2.xCoord;
		int y1 = (int) E1.posY;
		int y2 = (int) E2.yCoord;
		int z1 = (int) E1.posZ;
		int z2 = (int) E2.zCoord;
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
