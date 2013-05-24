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
	public AutoSavePlus plugin;
	public AutoSaveManager(AutoSavePlus plugin)
	{
		this.plugin = plugin;
	}

	public void run()
	{
		long start = System.currentTimeMillis();
		
		plugin.outConsole("Saving worlds and player data!");
		
		plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.start));
		
		List<String> worlds = plugin.getConfig().getStringList("worlds");
		for (String string : worlds)
		{
			World world = plugin.getServer().getWorld(string);
			if (world != null)
			{
				if (plugin.logAllWorlds)
				{
					plugin.outConsole("Saving world: " + world.getName());
				}
				world.save();
			}
			else
			{
				plugin.outConsole(Level.WARNING, "Error Saving World: \""+string+"\". Does it exist?");
			}
		}
		
		if (plugin.logAllWorlds)
		{
			plugin.outConsole("Saving players");
		}
		plugin.getServer().savePlayers();
		
		plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.finish));
		
		plugin.outConsole("Save Complete. ("+(System.currentTimeMillis() - start)+"ms)");
	}
}