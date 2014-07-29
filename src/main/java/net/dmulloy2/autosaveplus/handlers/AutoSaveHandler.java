/**
 * (c) 2014 dmulloy2
 */
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
	private String startMessage;
	private String finishMessage;
	private List<String> worlds;
	private boolean verbose;

	private final AutoSavePlus plugin;
	public AutoSaveHandler(AutoSavePlus plugin)
	{
		this.plugin = plugin;
		this.reload(); // Reload config
	}

	public final void save()
	{
		long start = System.currentTimeMillis();

		plugin.getLogHandler().log("Saving worlds and player data!");
		plugin.getServer().broadcastMessage(startMessage);

		for (World world : getWorlds())
		{
			if (verbose)
				plugin.getLogHandler().log("Saving world {0}", world.getName());

			world.save();
		}

		if (verbose)
			plugin.getLogHandler().log("Saving players");

		plugin.getServer().savePlayers();

		plugin.getServer().broadcastMessage(finishMessage);
		plugin.getLogHandler().log("Save complete. Took {0} ms!", System.currentTimeMillis() - start);
	}

	private final List<World> getWorlds()
	{
		if (worlds.isEmpty() || worlds.get(0).equals("*"))
			return plugin.getServer().getWorlds();

		List<World> ret = new ArrayList<>();
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
		this.verbose = plugin.getConfig().getBoolean("verbose", false);
		this.startMessage = FormatUtil.format(plugin.getConfig().getString("start"));
		this.finishMessage = FormatUtil.format(plugin.getConfig().getString("finish"));
	}
}