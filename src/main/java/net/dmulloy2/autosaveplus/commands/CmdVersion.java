/**
 * (c) 2014 dmulloy2
 */
package net.dmulloy2.autosaveplus.commands;

import net.dmulloy2.autosaveplus.AutoSavePlus;

/**
 * @author dmulloy2
 */

public class CmdVersion extends AutoSavePlusCommand
{
	public CmdVersion(AutoSavePlus plugin)
	{
		super(plugin);
		this.name = "version";
		this.aliases.add("v");
		this.description = "Display version information";
	}

	@Override
	public void perform()
	{
		sendMessage("&3====[ &eAutoSavePlus &3]====");
		sendMessage("&bVersion&e: {0}", plugin.getDescription().getVersion());
		sendMessage("&bAuthor&e: dmulloy2");
		sendMessage("&bIssues&e: https://github.com/dmulloy2/AutoSavePlus/issues");
	}
}