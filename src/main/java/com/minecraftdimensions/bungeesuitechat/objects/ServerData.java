package com.minecraftdimensions.bungeesuitechat.objects;


public class ServerData {
    static String serverName;
    static String shortName;
    static boolean connectionMessages;


    public ServerData(String name, String shortName, boolean connectionMessages) {
        this.serverName = name;
        this.shortName = shortName;
        this.connectionMessages = connectionMessages;
    }

    public static String getServerName() {
        return serverName;
    }

    public static String getServerShortName() {
        return shortName;
    }

    public static boolean usingConnectionMessages() {
        return connectionMessages;
    }
}
