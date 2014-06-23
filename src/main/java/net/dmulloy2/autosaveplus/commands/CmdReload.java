package net.dmulloy2.autosaveplus.commands;

import net.dmulloy2.autosaveplus.AutoSavePlus;
import net.dmulloy2.autosaveplus.types.Permission;
import net.dmulloy2.types.Reloadable;

/**
 * @author dmulloy2
 */

public class CmdReload extends AutoSavePlusCommand implements Reloadable
{
	public CmdReload(AutoSavePlus plugin)
	{
		super(plugin);
		this.name = "reload";
		this.aliases.add("rl");
		this.description = "reload AutoSavePlus";
		this.permission = Permission.RELOAD;
	}

	@Override
	public void perform()
	{
		reload();
	}

	@Override
	public void reload()
	{
		sendMessage("&aReloading AutoSavePlus...");

		long start = System.currentTimeMillis();

		plugin.reload();

		sendMessage("&aReload Complete! Took {0} ms!", System.currentTimeMillis() - start);
	}
}