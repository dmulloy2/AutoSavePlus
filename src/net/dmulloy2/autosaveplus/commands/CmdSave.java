package net.dmulloy2.autosaveplus.commands;

import net.dmulloy2.autosaveplus.AutoSavePlus;
import net.dmulloy2.autosaveplus.types.Permission;

public class CmdSave extends AutoSavePlusCommand
{
	public CmdSave(AutoSavePlus plugin)
	{
		super(plugin);
		this.name = "save";
		this.description = "Saves all worlds";
		
		this.permission = Permission.SAVE;
	}

	@Override
	public void perform()
	{
		sendpMessage("&aSaving all worlds!");
		
		plugin.getAutoSaveHandler().run();
	}
}