package villagerWipe.core.util;

import villagerWipe.core.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
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
    
}
