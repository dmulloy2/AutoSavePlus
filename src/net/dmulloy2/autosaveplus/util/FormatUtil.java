package net.dmulloy2.autosaveplus.util;

import java.text.MessageFormat;

import org.bukkit.ChatColor;

/**
 * @author dmulloy2
 */

public class FormatUtil 
{
	public static String format(String format, Object... objects)
	{
		String ret = MessageFormat.format(format, objects);
		return ChatColor.translateAlternateColorCodes('&', ret);
	}
	
	public static String getLogString(String format, Object... objects)
	{
		return MessageFormat.format(format, objects);
	}
}