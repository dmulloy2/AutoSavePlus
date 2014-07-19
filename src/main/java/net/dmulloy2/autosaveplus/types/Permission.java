package net.dmulloy2.autosaveplus.types;

import lombok.Getter;
import net.dmulloy2.types.IPermission;

/**
 * @author dmulloy2
 */

@Getter
public enum Permission implements IPermission
{
	RELOAD,
	SAVE,
	;

	public final String node;
	private Permission()
	{
		this.node = toString().toLowerCase().replaceAll("_", ".");
	}
}