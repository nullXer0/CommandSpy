package com.crimsonvalkyrie.commandspy;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class CommandSpyCommand implements CommandExecutor
{
	LuckPerms lpAPI;
	Node node;

	public CommandSpyCommand()
	{
		lpAPI = LuckPermsProvider.get();
		node = Node.builder("staffmonitor.admin").value(true).build();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			UserManager userManager = lpAPI.getUserManager();

			lpAPI.getUserManager().loadUser(((Player) sender).getUniqueId()).thenAcceptAsync(user ->
			{
				//Get all permissions assigned directly to the player
				Collection<Node> nodes = user.getNodes();

				//Check to see if the player has the permission
				if(nodes.stream().anyMatch(n -> n.equals(node)))
				{
					//Remove permission
					user.data().remove(node);
					sender.sendMessage("You are no longer spying on player commands.");
				}
				else
				{
					//Add permission
					user.data().add(node);
					sender.sendMessage("You are now spying on player commands.");
				}

				//Save user data
				userManager.saveUser(user);
			});
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
		}
		return true;
	}
}
