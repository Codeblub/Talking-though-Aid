package com.codeblub.talkingthroughaid.server;

public class MapInfo {
    private final String id;
    private final String name;
    private final String description;
    private final String size;
    private final int maxPlayers;

    public MapInfo(String id, String name, String description, String size, int maxPlayers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
        this.maxPlayers = maxPlayers;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
