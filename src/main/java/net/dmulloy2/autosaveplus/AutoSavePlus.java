/**
 * AutoSavePlus - a bukkit plugin
 * Copyright (C) 2013 - 2014 dmulloy2
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

import lombok.Getter;
import net.dmulloy2.SwornPlugin;
import net.dmulloy2.autosaveplus.commands.CmdReload;
import net.dmulloy2.autosaveplus.commands.CmdSave;
import net.dmulloy2.autosaveplus.commands.CmdVersion;
import net.dmulloy2.autosaveplus.handlers.AutoSaveHandler;
import net.dmulloy2.commands.CmdHelp;
import net.dmulloy2.handlers.CommandHandler;
import net.dmulloy2.handlers.LogHandler;
import net.dmulloy2.handlers.PermissionHandler;
import net.dmulloy2.types.Reloadable;
import net.dmulloy2.util.FormatUtil;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author dmulloy2
 */

public class AutoSavePlus extends SwornPlugin implements Reloadable
{
	private @Getter AutoSaveHandler autoSaveHandler;

	private @Getter String prefix = FormatUtil.format("&3[&eAutoSave&3]&e ");

	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();

		/** Configuration **/
		saveDefaultConfig();
		reloadConfig();

		/** Register Handlers **/
		logHandler = new LogHandler(this);
		permissionHandler = new PermissionHandler(this);
		autoSaveHandler = new AutoSaveHandler(this);
		commandHandler = new CommandHandler(this);

		/** Register Commands **/
		commandHandler.setCommandPrefix("asp");
		commandHandler.registerPrefixedCommand(new CmdHelp(this));
		commandHandler.registerPrefixedCommand(new CmdReload(this));
		commandHandler.registerPrefixedCommand(new CmdSave(this));
		commandHandler.registerPrefixedCommand(new CmdVersion(this));

		/** Schedule AutoSave task **/
		int delay = getConfig().getInt("delay", 15) * 20 * 60;
		new AutoSaveTask().runTaskTimer(this, delay, delay);

		logHandler.log("{0} has been enabled ({1}ms)", getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	@Override
	public void onDisable()
	{
		long start = System.currentTimeMillis();

		getServer().getScheduler().cancelTasks(this);

		autoSaveHandler.save();

		logHandler.log("{0} has been disabled ({1}ms)", getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	/** AutoSave Task **/
	public class AutoSaveTask extends BukkitRunnable
	{
		@Override
		public void run()
		{
			autoSaveHandler.save();
		}
	}

	@Override
	public void reload()
	{
		reloadConfig();

		autoSaveHandler.reload();
	}
}