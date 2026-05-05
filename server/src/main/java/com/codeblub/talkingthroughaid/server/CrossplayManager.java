package com.codeblub.talkingthroughaid.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrossplayManager {
    private final List<MapInfo> availableMaps;
    private final List<WeaponInfo> availableWeapons;
    private final List<CharacterInfo> availableCharacters;

    public CrossplayManager() {
        this.availableMaps = createMaps();
        this.availableWeapons = createWeapons();
        this.availableCharacters = createCharacters();
    }

    public List<MapInfo> getAvailableMaps() {
        return Collections.unmodifiableList(availableMaps);
    }

    public List<WeaponInfo> getAvailableWeapons() {
        return Collections.unmodifiableList(availableWeapons);
    }

    public List<CharacterInfo> getAvailableCharacters() {
        return Collections.unmodifiableList(availableCharacters);
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
        weapons.add(new WeaponInfo("rytech_amr", "Rytech AMR", "Sniper", 95, 120, 1.2f));
        weapons.add(new WeaponInfo("switchblade_x9", "Switchblade X9", "SMG", 24, 45, 12.0f));
        weapons.add(new WeaponInfo("striker", "Striker", "Shotgun", 35, 20, 2.5f));
        return weapons;
    }

    private List<CharacterInfo> createCharacters() {
        List<CharacterInfo> characters = new ArrayList<>();
        characters.add(new CharacterInfo("assault_operator", "Raptor", "Assault", 100, 80, "Rapid reload and improved recoil control"));
        characters.add(new CharacterInfo("sniper_operator", "Spectre", "Sniper", 80, 70, "Long-range targeting boost and reduced sway"));
        characters.add(new CharacterInfo("support_operator", "Medic", "Support", 90, 75, "Deployable heal station and radar ping"));
        return characters;
    }
}
