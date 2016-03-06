package com.minecraftdimensions.bungeesuitechat.listeners;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.Utilities;
import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void setFormatChat( AsyncPlayerChatEvent e ) {
        if ( e.isCancelled() ) {
            return;
        }
        BSPlayer p = PlayerManager.getPlayer( e.getPlayer() );
        if ( p == null ) {
            Bukkit.getConsoleSender().sendMessage( ChatColor.DARK_RED + "Player did not connect properly through BungeeCord, Chat canceled!" );
            Utilities.requestPlayer( e.getPlayer().getName() ); // if we failed sending the message, lets request the player again
            e.getPlayer().sendMessage( ChatColor.RED + "Fehler beim Senden deiner Nachricht. Versuche es in einigen Augenblicken erneut."); // inform the player about this
            e.setCancelled( true );
            return;
        }
        if ( !ChannelManager.playerHasPermissionToTalk( p ) ) {
            e.setCancelled( true );
            e.getPlayer().sendMessage( ChatColor.RED + "You do not have permission to talk in this channel" );
            return;
        }
        
        if( BungeeSuiteChat.mute && !e.getPlayer().hasPermission("bungeesuite.chat.mute.bypass")){
        	e.setCancelled( true );
        	e.getPlayer().sendMessage( ChatColor.RED + "Die Eventleitung hat den Chat aktuell deaktiviert.");
        	return;
        }
        
         e.setFormat( p.getChannelFormat() );
        if ( ChannelManager.isServer( p.getChannel() ) ) {
            e.getRecipients().clear();
            e.getRecipients().addAll( ChannelManager.getServerPlayers() );
            e.getRecipients().removeAll( ChannelManager.getIgnores( e.getPlayer() ) );
        }else if ( ChannelManager.isGlobal( p.getChannel() ) ) {
            e.getRecipients().clear();
            e.getRecipients().addAll( ChannelManager.getGlobalPlayers() );
            e.getRecipients().removeAll( ChannelManager.getIgnores( e.getPlayer() ) );
        } else if ( ChannelManager.isAdmin( p.getChannel() ) ) {
            e.getRecipients().clear();
            e.getRecipients().addAll( ChannelManager.getAdminPlayers() );
        } 
    }

    @EventHandler( priority = EventPriority.HIGH )
    public void setVariables( AsyncPlayerChatEvent e ) {
        if ( e.isCancelled() ) {
            return;
        }
        e.setFormat( Utilities.ReplaceVariables( e.getPlayer(), e.getFormat() ) );
        e.setMessage( Utilities.SetMessage( e.getPlayer(), e.getMessage() ) );
    }

    @EventHandler( priority = EventPriority.MONITOR )
    public void setLogChat( AsyncPlayerChatEvent e ) {
        if ( e.isCancelled() ) {
            return;
        }
        BSPlayer p = PlayerManager.getPlayer( e.getPlayer() );
        if ( p == null ) {
            e.setCancelled( true );
            return;
        }
        if ( ChannelManager.isGlobal( p.getChannel() ) ) {
            ChannelManager.sendGlobalChat( e.getPlayer().getName(), String.format( e.getFormat(), p.getDisplayingName(), e.getMessage() ) );
        } else if ( ChannelManager.isAdmin( p.getChannel() ) ) {
            ChannelManager.sendAdminChat( String.format( e.getFormat(), p.getDisplayingName(), e.getMessage() ) );
        } else {
            e.getRecipients().addAll( PlayerManager.getChatSpies( e.getPlayer(), e.getRecipients() ) );
            Utilities.logChat( String.format( e.getFormat(), p.getDisplayingName(), e.getMessage() ) );
        }

    }


}
