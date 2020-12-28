package com.crimsonvalkyrie.commandspy;

import org.bukkit.plugin.java.JavaPlugin;

public final class CommandSpy extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		getCommand("commandspy").setExecutor(new CommandSpyCommand());
	}

	@Override
	public void onDisable()
	{
	}
}
