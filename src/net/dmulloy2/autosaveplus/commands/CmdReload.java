package net.dmulloy2.autosaveplus.commands;

import net.dmulloy2.autosaveplus.AutoSavePlus;
import net.dmulloy2.autosaveplus.types.Permission;

/**
 * @author dmulloy2
 */

public class CmdReload extends AutoSavePlusCommand
{
	public CmdReload(AutoSavePlus plugin)
	{
		super(plugin);
		this.name = "reload";
		this.aliases.add("rl");
		this.description = "reload AutoSavePlus";
		this.permission = Permission.RELOAD;

		this.mustBePlayer = false;
	}

	@Override
	public void perform()
	{
		sendMessage("&aReloading AutoSavePlus...");

		long start = System.currentTimeMillis();

		plugin.reloadConfig();

		long finish = System.currentTimeMillis();

		sendMessage("&aReload Complete! Took {0} ms!", finish - start);
	}
}