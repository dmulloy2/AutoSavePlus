package net.dmulloy2.autosaveplus.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.dmulloy2.autosaveplus.AutoSavePlus;
import net.dmulloy2.autosaveplus.util.FormatUtil;

import org.bukkit.World;

/**
 * @author dmulloy2
 */

public class AutoSaveHandler 
{
	private final AutoSavePlus plugin;
	public AutoSaveHandler(AutoSavePlus plugin)
	{
		this.plugin = plugin;
	}

	/** Run Save **/
	public void run()
	{
		long start = System.currentTimeMillis();
		
		plugin.outConsole("Saving worlds and player data!");
		
		plugin.getServer().broadcastMessage(FormatUtil.format(plugin.getConfig().getString("start")));
		
		List<String> worlds = plugin.getConfig().getStringList("worlds");
		for (String string : worlds)
		{
			World world = plugin.getServer().getWorld(string);
			if (world != null)
			{
				if (plugin.getConfig().getBoolean("logAllWorlds", false))
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
		
		if (plugin.getConfig().getBoolean("logAllWorlds", false))
		{
			plugin.outConsole("Saving players");
		}
		
		plugin.getServer().savePlayers();
		
		plugin.getServer().broadcastMessage(FormatUtil.format(plugin.getConfig().getString("finish")));
		
		plugin.outConsole("Save complete. Took {0} ms!", System.currentTimeMillis() - start);
	}
	
	public List<World> getWorlds()
	{
		if (plugin.getConfig().getStringList("worlds").get(0).equals("*")) 
		{
			return plugin.getServer().getWorlds();
		}
		
		List<World> worlds = new ArrayList<World>();
		for (String s : plugin.getConfig().getStringList("worlds"))
		{
			World world = plugin.getServer().getWorld(s);
			if (world != null)
				worlds.add(world);
		}
		
		return worlds;
	}
}