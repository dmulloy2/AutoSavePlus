package net.dmulloy2.autosaveplus.commands;

import java.util.ArrayList;
import java.util.List;

import net.dmulloy2.autosaveplus.AutoSavePlus;
import net.dmulloy2.autosaveplus.util.FormatUtil;

/**
 * @author dmulloy2
 */

public class CmdHelp extends PaginatedCommand
{
	public CmdHelp(AutoSavePlus plugin)
	{
		super(plugin);
		this.name = "help";
		this.optionalArgs.add("page");
		this.description = "Shows " + plugin.getName() + " help";
		this.linesPerPage = 6;
	}

	@Override
	public int getListSize()
	{
		return buildHelpMenu().size();
	}

	@Override
	public String getHeader(int index)
	{
		return FormatUtil.format("&3====[ &eAutoSavePlus Help &3(&e{0}&3/&e{1}&3) ]====", index, getPageCount());
	}

	@Override
	public List<String> getLines(int startIndex, int endIndex)
	{
		List<String> lines = new ArrayList<String>();
		for (int i = startIndex; i < endIndex && i < getListSize(); i++)
		{
			lines.add(buildHelpMenu().get(i));
		}

		return lines;
	}

	@Override
	public String getLine(int index)
	{
		return null;
	}

	private final List<String> buildHelpMenu()
	{
		List<String> ret = new ArrayList<String>();

		for (AutoSavePlusCommand cmd : plugin.getCommandHandler().getRegisteredCommands())
		{
			if (plugin.getPermissionHandler().hasPermission(sender, cmd.permission))
			{
				ret.add(cmd.getUsageTemplate(true));
			}
		}

		return ret;
	}
}