package net.dmulloy2.autosaveplus;

import java.util.List;
import java.util.logging.Level;

import net.dmulloy2.autosaveplus.util.FormatUtil;
import net.dmulloy2.autosaveplus.util.Util;

import org.bukkit.World;

/**
 * @author dmulloy2
 */

public class AutoSaveManager 
{
	private final AutoSavePlus plugin;
	public AutoSaveManager(final AutoSavePlus plugin)
	{
		this.plugin = plugin;
	}

	/** Run Save **/
	public void run()
	{
		long start = System.currentTimeMillis();
		
		plugin.outConsole("Saving worlds and player data!");
		
		plugin.getServer().broadcastMessage(FormatUtil.format(plugin.start));
		
		List<String> worlds = plugin.getConfig().getStringList("worlds");
		for (String string : worlds)
		{
			World world = Util.matchWorld(string);
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
				plugin.outConsole(Level.WARNING, "Could not save World \"{0}\". Does it exist?", string);
			}
		}
		
		if (plugin.logAllWorlds)
		{
			plugin.outConsole("Saving players");
		}
		
		plugin.getServer().savePlayers();
		
		plugin.getServer().broadcastMessage(FormatUtil.format(plugin.finish));
		
		plugin.outConsole("Save complete. Took {0} ms!", System.currentTimeMillis() - start);
	}
}