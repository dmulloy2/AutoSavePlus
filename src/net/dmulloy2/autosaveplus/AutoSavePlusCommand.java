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
					sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Reloading AutoSavePlus!");
				}
				else
				{
					sender.sendMessage(plugin.prefix + ChatColor.RED + "You do not have permission to do this!");
				}
			}
			else if (args[0].equalsIgnoreCase("save"))
			{
				if (sender.hasPermission("asp.save"))
				{
					sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Saving worlds and player data!");
					if (plugin.debug) plugin.outConsole(sender.getName() + " is forcing a save!");
					plugin.getAutoSaveManager().run();
				}
				else
				{
					sender.sendMessage(plugin.prefix + ChatColor.RED + "You do not have permission to do this!");
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
