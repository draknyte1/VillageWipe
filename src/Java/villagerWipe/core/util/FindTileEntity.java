package villagerWipe.core.util;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import villagerWipe.core.commands.CommandUtils;

public class FindTileEntity {

	public static boolean get(ICommandSender S, World W, int Dis){
		
		List<?> xList = W.loadedTileEntityList;
		
		EntityPlayer entityplayer = CommandUtils.getPlayer(S);		
		
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
		
		
		return true;
		
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

}
