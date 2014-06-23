package net.dmulloy2.autosaveplus.handlers;

import java.util.ArrayList;
import java.util.List;

import net.dmulloy2.autosaveplus.AutoSavePlus;
import net.dmulloy2.types.Reloadable;
import net.dmulloy2.util.FormatUtil;

import org.bukkit.World;

/**
 * @author dmulloy2
 */

public class AutoSaveHandler implements Reloadable
{
	private List<String> worlds;
	private boolean logAllWorlds;
	private String start;
	private String finish;

	private final AutoSavePlus plugin;
	public AutoSaveHandler(AutoSavePlus plugin)
	{
		this.plugin = plugin;
		this.reload(); // Relod config
	}

	public final void save()
	{
		long start = System.currentTimeMillis();

		plugin.getLogHandler().log("Saving worlds and player data!");
		plugin.getServer().broadcastMessage(this.start);

		for (World world : getWorlds())
		{
			if (logAllWorlds)
			{
				plugin.getLogHandler().log("Saving world {0}", world.getName());
			}

			world.save();
		}

		if (logAllWorlds)
		{
			plugin.getLogHandler().log("Saving players");
		}

		plugin.getServer().savePlayers();

		plugin.getServer().broadcastMessage(this.finish);
		plugin.getLogHandler().log("Save complete. Took {0} ms!", System.currentTimeMillis() - start);
	}

	private final List<World> getWorlds()
	{
		if (worlds.isEmpty() || worlds.get(0).equals("*"))
		{
			return plugin.getServer().getWorlds();
		}

		List<World> ret = new ArrayList<World>();
		for (String s : worlds)
		{
			World world = plugin.getServer().getWorld(s);
			if (world != null)
				ret.add(world);
		}

		return ret;
	}

	@Override
	public void reload()
	{
		this.worlds = plugin.getConfig().getStringList("worlds");
		this.logAllWorlds = plugin.getConfig().getBoolean("logAllWorlds", false);
		this.start = FormatUtil.format(plugin.getConfig().getString("start"));
		this.finish = FormatUtil.format(plugin.getConfig().getString("finish"));
	}
}