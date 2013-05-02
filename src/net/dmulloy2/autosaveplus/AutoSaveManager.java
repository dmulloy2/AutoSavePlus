/**
 * (c) 2013 dmulloy2
 */
package net.dmulloy2.autosaveplus;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.World;

/**
 * @author dmulloy2
 */

public class AutoSaveManager 
{
	public static List<String> worlds = AutoSavePlus.p.getConfig().getStringList("worlds");
	
	public static void run()
	{
		AutoSavePlus.p.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', AutoSavePlus.p.start));
		
		long start = System.currentTimeMillis();
		
		for (String string : worlds)
		{
			World world = AutoSavePlus.p.getServer().getWorld(string);
			if (world != null)
			{
				AutoSavePlus.p.outConsole("Saving world: " + world.getName());
				world.save();
			}
			else
			{
				AutoSavePlus.p.outConsole(Level.WARNING, "Error Saving World: \""+string+"\". Does it exist?");
			}
		}
		
		AutoSavePlus.p.outConsole("Saving players");
		AutoSavePlus.p.getServer().savePlayers();
		
		AutoSavePlus.p.outConsole("Save Complete. ("+(System.currentTimeMillis() - start)+"ms)");
		
		AutoSavePlus.p.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', AutoSavePlus.p.finish));
	}
}