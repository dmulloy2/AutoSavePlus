/**
* AutoSavePlus - a bukkit plugin
* Copyright (C) 2013 dmulloy2
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package net.dmulloy2.autosaveplus;

import java.util.logging.Level;

import lombok.Getter;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author dmulloy2
 */

public class AutoSavePlus extends JavaPlugin
{
	private @Getter AutoSaveManager autoSaveManager;
	
	public String start, finish, prefix;
	public boolean debug, logAllWorlds;
	public int delay;
	
	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();
		
		autoSaveManager = new AutoSaveManager(this);
		
		prefix = ChatColor.GOLD + "[AutoSavePlus] ";

		/**Set command executor**/
		getCommand("asp").setExecutor(new AutoSavePlusCommand (this));
		
		/**Save/Load config**/
		saveDefaultConfig();
		loadConfig();

		/**Schedule AutoSave task**/
		int delaym = delay * 20 * 60;
		new AutoSaveTask().runTaskTimer(this, delaym, delaym);
		
		long finish = System.currentTimeMillis();
		outConsole(getDescription().getFullName() + " has been enabled ("+(finish-start)+"ms)");
	}
	
	@Override
	public void onDisable()
	{
		long start = System.currentTimeMillis();
		
		autoSaveManager.run();
		
		/**Cancel AutoSave task**/
		getServer().getScheduler().cancelTasks(this);
		
		long finish = System.currentTimeMillis();
		
		outConsole(getDescription().getFullName() + " has been disabled ("+(finish-start)+"ms)");
	}
	
	/**Console Logging**/
	public void outConsole(String string)
	{
		getLogger().info(string);
	}
	
	public void outConsole(Level level, String string)
	{
		getLogger().log(level, string);
	}
	
	/**Load Configuration**/
	public void loadConfig()
	{
		start = getConfig().getString("start");
		finish = getConfig().getString("finish");
		delay = getConfig().getInt("delay");
	    debug = getConfig().getBoolean("debug");
	    logAllWorlds = getConfig().getBoolean("logAllWorlds");
	}

	/**Reload the Plugin**/
	public void reload()
	{
		reloadConfig();
		loadConfig();
	}
	
	/**AutoSave Task**/
	public class AutoSaveTask extends BukkitRunnable
	{
		@Override
		public void run() 
		{
			autoSaveManager.run();
		}
	}
}