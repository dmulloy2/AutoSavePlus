package net.dmulloy2.autosaveplus.handlers;

import java.util.ArrayList;
import java.util.List;

import net.dmulloy2.autosaveplus.AutoSavePlus;
import net.dmulloy2.autosaveplus.commands.AutoSavePlusCommand;
import net.dmulloy2.autosaveplus.commands.CmdHelp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author dmulloy2
 */

public class CommandHandler implements CommandExecutor
{
	private final AutoSavePlus plugin;
	private String commandPrefix;
	private List<AutoSavePlusCommand> registeredCommands;

	public CommandHandler(final AutoSavePlus plugin)
	{
		this.plugin = plugin;
		this.registeredCommands = new ArrayList<AutoSavePlusCommand>();
	}

	public void registerCommand(AutoSavePlusCommand command)
	{
		if (commandPrefix != null)
			registeredCommands.add(command);
	}

	public List<AutoSavePlusCommand> getRegisteredCommands()
	{
		return registeredCommands;
	}

	public String getCommandPrefix()
	{
		return commandPrefix;
	}

	public void setCommandPrefix(String commandPrefix)
	{
		this.commandPrefix = commandPrefix;
		plugin.getCommand(commandPrefix).setExecutor(this);
	}

	public boolean usesCommandPrefix()
	{
		return commandPrefix != null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		List<String> argsList = new ArrayList<String>();

		if (args.length > 0)
		{
			String commandName = args[0];
			for (int i = 1; i < args.length; i++)
				argsList.add(args[i]);

			for (AutoSavePlusCommand command : registeredCommands)
			{
				if (commandName.equalsIgnoreCase(command.getName()) || command.getAliases().contains(commandName.toLowerCase()))
				{
					command.execute(sender, argsList.toArray(new String[0]));
					return true;
				}
			}

			sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Unknown AutoSavePlus command \"" + args[0] + "\". Try /ua help!");
		}
		else
		{
			new CmdHelp(plugin).execute(sender, args);
		}

		return true;
	}
}