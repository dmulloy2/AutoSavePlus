/**
 * AutoSavePlus - a bukkit plugin Copyright (C) 2013 dmulloy2
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.dmulloy2.autosaveplus;

import java.util.logging.Level;

import lombok.Getter;
import net.dmulloy2.autosaveplus.commands.CmdHelp;
import net.dmulloy2.autosaveplus.commands.CmdReload;
import net.dmulloy2.autosaveplus.commands.CmdSave;
import net.dmulloy2.autosaveplus.handlers.AutoSaveHandler;
import net.dmulloy2.autosaveplus.handlers.CommandHandler;
import net.dmulloy2.autosaveplus.handlers.LogHandler;
import net.dmulloy2.autosaveplus.handlers.PermissionHandler;
import net.dmulloy2.autosaveplus.types.Reloadable;
import net.dmulloy2.autosaveplus.util.FormatUtil;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author dmulloy2
 */

public class AutoSavePlus extends JavaPlugin implements Reloadable
{
	private @Getter PermissionHandler permissionHandler;
	private @Getter AutoSaveHandler autoSaveHandler;
	private @Getter CommandHandler commandHandler;
	private @Getter LogHandler logHandler;

	private @Getter String prefix = FormatUtil.format("&6[&4&lASP&6] ");

	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();

		saveDefaultConfig();
		reloadConfig();

		/** Register Handlers **/
		autoSaveHandler = new AutoSaveHandler(this);

		permissionHandler = new PermissionHandler(this);
		commandHandler = new CommandHandler(this);
		logHandler = new LogHandler(this);

		/** Register Commands **/
		commandHandler.setCommandPrefix("asp");
		commandHandler.registerCommand(new CmdHelp(this));
		commandHandler.registerCommand(new CmdReload(this));
		commandHandler.registerCommand(new CmdSave(this));

		/** Schedule AutoSave task **/
		int delay = getConfig().getInt("delay", 15) * 20 * 60;
		new AutoSaveTask().runTaskTimer(this, delay, delay);

		long finish = System.currentTimeMillis();

		outConsole("{0} has been enabled ({1}ms)", getDescription().getFullName(), finish - start);
	}

	@Override
	public void onDisable()
	{
		long start = System.currentTimeMillis();

		autoSaveHandler.run();

		getServer().getScheduler().cancelTasks(this);

		long finish = System.currentTimeMillis();

		outConsole("{0} has been disabled ({1}ms)", getDescription().getFullName(), finish - start);
	}

	/** Console Logging **/
	public void outConsole(Level level, String string, Object... objects)
	{
		logHandler.log(level, string, objects);
	}

	public void outConsole(String string, Object... objects)
	{
		logHandler.log(string, objects);
	}

	public void debug(String string, Object... objects)
	{
		logHandler.debug(string, objects);
	}

	/** AutoSave Task **/
	public class AutoSaveTask extends BukkitRunnable
	{
		@Override
		public void run()
		{
			autoSaveHandler.run();
		}
	}

	@Override
	public void reload()
	{
		reloadConfig();
	}
}