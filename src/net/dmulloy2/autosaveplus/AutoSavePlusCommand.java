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
	private final AutoSavePlus plugin;
	public AutoSavePlusCommand(final AutoSavePlus plugin)  
	{
		this.plugin = plugin;
	}	
	  
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		perform(sender, args);
		return true;
	}
	
	public void perform(CommandSender sender, String[] args)
	{
		if (args.length == 0)
		{
			displayHelp(sender);
			return;
		}
		
		if (args[0].equalsIgnoreCase("help"))
		{
			displayHelp(sender);
			return;
		}
		
		if (args[0].equalsIgnoreCase("reload"))
		{
			if (! sender.hasPermission("asp.reload"))
			{
				sender.sendMessage(plugin.prefix + ChatColor.RED + "You do not have permission to do this!");
				return;
			}
			
			sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Reloading AutoSavePlus!");
			
			plugin.reload();
			
			sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Reload complete!");
			return;
		}
		
		if (args[0].equalsIgnoreCase("save"))
		{
			if (! sender.hasPermission("asp.save"))
			{
				sender.sendMessage(plugin.prefix + ChatColor.RED + "You do not have permission to do this!");
			}
			
			sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Saving worlds and player data!");
			plugin.debug(sender.getName() + " is forcing a save!");
			plugin.getAutoSaveManager().run();
			return;
		}
		
		displayHelp(sender);
		return;
	}

	private void displayHelp(CommandSender sender) 
	{
		sender.sendMessage(ChatColor.DARK_RED + "==== " + ChatColor.GOLD + plugin.getDescription().getFullName() + ChatColor.DARK_RED + " ====");
		sender.sendMessage(ChatColor.RED + "/asp" + ChatColor.DARK_RED + " help " + ChatColor.YELLOW + "Display this help menu");
		sender.sendMessage(ChatColor.RED + "/asp" + ChatColor.DARK_RED + " reload " + ChatColor.YELLOW + "Reload this plugin");
		sender.sendMessage(ChatColor.RED + "/asp" + ChatColor.DARK_RED + " save " + ChatColor.YELLOW + "Save all worlds");
	}
}