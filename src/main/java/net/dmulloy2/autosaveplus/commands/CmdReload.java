/**
 * (c) 2014 dmulloy2
 */
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
	}

	@Override
	public void perform()
	{
		long start = System.currentTimeMillis();
		sendMessage("Reloading AutoSavePlus...");

		plugin.reload();

		sendMessage("Reload Complete! Took &b{0} &ems!", System.currentTimeMillis() - start);
	}
}