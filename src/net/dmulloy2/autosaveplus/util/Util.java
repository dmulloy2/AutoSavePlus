package net.dmulloy2.autosaveplus.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Util 
{
	public static World matchWorld(String s)
	{
		return Bukkit.getWorld(s);
	}
}