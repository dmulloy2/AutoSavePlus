package net.dmulloy2.autosaveplus.types;

/**
 * @author dmulloy2
 */

public enum Permission
{
	RELOAD,
	SAVE;

	public final String node;

	Permission()
	{
		this.node = toString().toLowerCase().replaceAll("_", ".");
	}

	public String getNode()
	{
		return this.node;
	}
}