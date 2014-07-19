package net.dmulloy2.autosaveplus.commands;

import net.dmulloy2.autosaveplus.AutoSavePlus;
import net.dmulloy2.autosaveplus.types.Permission;

import org.bukkit.World;

/**
 * @author dmulloy2
 */

public class CmdSave extends AutoSavePlusCommand
{
	public CmdSave(AutoSavePlus plugin)
	{
		super(plugin);
		this.name = "save";
		this.optionalArgs.add("world");
		this.description = "Saves all worlds";
		this.permission = Permission.SAVE;
	}

	@Override
	public void perform()
	{
		if (args.length == 0)
		{
			sendpMessage("&aSaving all worlds!");
			plugin.getAutoSaveHandler().save();
		}
		else
		{
			World world = plugin.getServer().getWorld(args[0]);
			if (world == null)
			{
				err("World \"&c{0}&4\" not found!", args[0]);
				return;
			}

			sendpMessage("Saving world &b{0}&e!", world.getName());
			world.save();
			sendpMessage("World saved!");
		}
	}
}