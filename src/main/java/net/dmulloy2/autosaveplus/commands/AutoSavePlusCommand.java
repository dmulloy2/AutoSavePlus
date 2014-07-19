package net.dmulloy2.autosaveplus.commands;

import net.dmulloy2.autosaveplus.AutoSavePlus;
import net.dmulloy2.commands.Command;

/**
 * @author dmulloy2
 */

public abstract class AutoSavePlusCommand extends Command
{
	protected final AutoSavePlus plugin;
	public AutoSavePlusCommand(AutoSavePlus plugin)
	{
		super(plugin);
		this.plugin = plugin;
		this.usesPrefix = true;
	}
}