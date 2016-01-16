package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;

public class GlobalChatCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if(BungeeSuiteChat.global){
			BungeeSuiteChat.global = false;
			
			sender.sendMessage(ChatColor.GREEN + "Globaler Chat deaktiviert.");
			
		}else{
			BungeeSuiteChat.global = true;
			
			sender.sendMessage(ChatColor.GREEN + "Globaler Chat aktiviert.");
		}
		
		return true;
	}
	
	

}
