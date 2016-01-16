package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;

public class ServerMuteCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if(BungeeSuiteChat.mute){
			BungeeSuiteChat.mute = false;
			
			sender.sendMessage(ChatColor.GREEN + "Servermute deaktiviert.");
			
		}else{
			BungeeSuiteChat.mute = true;
			
			sender.sendMessage(ChatColor.GREEN + "Servermute aktiviert.");
		}
		
		return true;
	}

}
