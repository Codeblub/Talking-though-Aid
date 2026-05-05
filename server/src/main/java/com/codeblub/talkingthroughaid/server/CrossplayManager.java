package com.codeblub.talkingthroughaid.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrossplayManager {
    private final List<MapInfo> availableMaps;
    private final List<WeaponInfo> availableWeapons;

    public CrossplayManager() {
        this.availableMaps = createMaps();
        this.availableWeapons = createWeapons();
    }

    public List<MapInfo> getAvailableMaps() {
        return Collections.unmodifiableList(availableMaps);
    }

    public List<WeaponInfo> getAvailableWeapons() {
        return Collections.unmodifiableList(availableWeapons);
    }

    public MatchSetup createMatch(String mapId, int playerCount) {
        MapInfo map = availableMaps.stream()
            .filter(m -> m.getId().equals(mapId))
            .findFirst()
            .orElse(availableMaps.get(0));

        return new MatchSetup(map, playerCount, availableWeapons);
    }

    private List<MapInfo> createMaps() {
        List<MapInfo> maps = new ArrayList<>();
        maps.add(new MapInfo(
            "urban_assault",
            "Urban Assault",
            "A medium-sized city district with three lanes, tall crates, narrow alleys, and a central tower for snipers.",
            "medium",
            12));
        maps.add(new MapInfo(
            "desert_compound",
            "Desert Compound",
            "A close-quarters compound with sandy walls, roof covers, and long sightlines across the open courtyard.",
            "small",
            10));
        return maps;
    }

    private List<WeaponInfo> createWeapons() {
        List<WeaponInfo> weapons = new ArrayList<>();
        weapons.add(new WeaponInfo("assault_rifle", "Falcon AR", "Assault", 35, 70, 8.5f));
        weapons.add(new WeaponInfo("smg", "Viper SMG", "SMG", 24, 45, 12.0f));
        weapons.add(new WeaponInfo("sniper", "Horizon SR", "Sniper", 95, 120, 1.2f));
        return weapons;
    }
}
