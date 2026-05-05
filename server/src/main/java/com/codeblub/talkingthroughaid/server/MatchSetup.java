package com.codeblub.talkingthroughaid.server;

import java.util.List;

public class MatchSetup {
    private final MapInfo map;
    private final int playerCount;
    private final List<WeaponInfo> availableWeapons;

    public MatchSetup(MapInfo map, int playerCount, List<WeaponInfo> availableWeapons) {
        this.map = map;
        this.playerCount = playerCount;
        this.availableWeapons = availableWeapons;
    }

    public MapInfo getMap() {
        return map;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public List<WeaponInfo> getAvailableWeapons() {
        return availableWeapons;
    }
}
