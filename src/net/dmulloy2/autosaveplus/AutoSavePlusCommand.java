/**
 * (c) 2013 dmulloy2
 */
package net.dmulloy2.autosaveplus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author dmulloy2
 */

public class AutoSavePlusCommand implements CommandExecutor
{
	public AutoSavePlus plugin;
	public AutoSavePlusCommand(AutoSavePlus plugin)  
	{
		this.plugin = plugin;
	}	
	  
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (args.length == 0)
		{
			displayHelp(sender);
		}	
		else
		{
			if (args[0].equalsIgnoreCase("help"))
			{
				  displayHelp(sender);
			}
			else if (args[0].equalsIgnoreCase("reload"))
			{
				if (sender.hasPermission("asp.reload"))
				{
					plugin.reload();
					sender.sendMessage(ChatColor.GREEN + "Reloading AutoSavePlus!");
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "You do not have permission to do this!");
				}
			}
			else if (args[0].equalsIgnoreCase("save"))
			{
				if (sender.hasPermission("asp.save"))
				{
					sender.sendMessage(ChatColor.GREEN + "Saving all worlds!");
					if (plugin.debug) plugin.outConsole(sender.getName() + " is saving all worlds");
					AutoSaveManager.run();
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "You do not have permission to do this!");
				}
			}
			else
			{
				displayHelp(sender);
			}
		}
		  
		return true;
	}

	private void displayHelp(CommandSender sender) 
	{
		sender.sendMessage(ChatColor.DARK_RED + "==== " + ChatColor.GOLD + plugin.getDescription().getFullName() + ChatColor.DARK_RED + " ====");
		sender.sendMessage(ChatColor.RED + "/asp" + ChatColor.DARK_RED + " help " + ChatColor.YELLOW + "Display this help menu");
		sender.sendMessage(ChatColor.RED + "/asp" + ChatColor.DARK_RED + " reload " + ChatColor.YELLOW + "Reload this plugin");
		sender.sendMessage(ChatColor.RED + "/asp" + ChatColor.DARK_RED + " save " + ChatColor.YELLOW + "Save all worlds");
	}
}
