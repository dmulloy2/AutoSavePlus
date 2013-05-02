/**
 * (c) 2013 dmulloy2
 */
package net.dmulloy2.autosaveplus;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author dmulloy2
 */

public class AutoSavePlus extends JavaPlugin
{
	public static AutoSavePlus p;
	public String start, finish;
	public boolean debug;
	public int delay;
	
	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();
		
		p = this;
		
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
		
		AutoSaveManager.run();
		
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
			AutoSaveManager.run();
		}
	}
}