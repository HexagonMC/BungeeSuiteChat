package com.minecraftdimensions.bungeesuitechat;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.managers.PrefixSuffixManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.objects.Channel;
import com.minecraftdimensions.bungeesuitechat.objects.ServerData;
import com.minecraftdimensions.bungeesuitechat.tasks.PluginMessageTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Utilities {

    public static String ReplaceVariables( Player player, String format ) {
        BSPlayer p = PlayerManager.getPlayer( player );
        Channel c = p.getChannel();
        format = format.replace( "{channel}", c.getName() );
        format = format.replace( "{player}", p.getDisplayingName() );
        format = format.replace( "{shortname}", ServerData.getServerShortName() );
        format = format.replace( "{world}", player.getWorld().getName() );
        format = format.replace( "{server}", ServerData.getServerName() );

        if ( BungeeSuiteChat.usingVault ) {
            format = format.replace( "{permgroup}", PrefixSuffixManager.getPlayersPermGroup( player ) );
            format = format.replace( "{permgroupsuffix}", PrefixSuffixManager.getPlayersPermGroupSuffix( player ) );
            format = format.replace( "{permplayersuffix}", PrefixSuffixManager.getPlayersPermSuffix( player ) );
            format = format.replace( "{permprefix}", PrefixSuffixManager.getPermPrefix( player ) );
            format = format.replace( "{permsuffix}", PrefixSuffixManager.getPermSuffix( player ) );
            format = format.replace( "{permgroupprefix}", PrefixSuffixManager.getPlayersGroupPrefix( player ) );
            format = format.replace( "{permplayerprefix}", PrefixSuffixManager.getPlayersPermPrefix( player ) );
        }
        if ( PrefixSuffixManager.suffix ) {
            String group = PrefixSuffixManager.getPlayersSuffixGroup( player );
            format = format.replace( "{suffixgroup}", PrefixSuffixManager.getPlayersSuffixGroup( player ) );
            format = format.replace( "{suffix}", PrefixSuffixManager.getPlayersSuffix( group ) );
        }
        if ( PrefixSuffixManager.prefix ) {
            String group = PrefixSuffixManager.getPlayersPrefixGroup( player );
            format = format.replace( "{prefixgroup}", PrefixSuffixManager.getPlayersPrefixGroup( player ) );
            format = format.replace( "{prefix}", PrefixSuffixManager.getPlayersPrefix( group ) );
        }

        format = format.replace( "{message}", "%2$s" );
        return colorize( format );
    }

    public static String colorize( String input ) {
        return ChatColor.translateAlternateColorCodes( '&', input );
    }

    public static void logChat( String chat ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "LogChat" );
            out.writeUTF( chat );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
    }

    public static String SetMessage( Player player, String message ) {
        if ( player.hasPermission( "bungeesuite.chat.color" ) ) {
            message = colorize( message );
        }
        return message;
    }


}
