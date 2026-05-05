package com.codeblub.talkingthroughaid.server;

public class CharacterInfo {
    private final String id;
    private final String name;
    private final String role;
    private final int health;
    private final int speed;
    private final String ability;

    public CharacterInfo(String id, String name, String role, int health, int speed, String ability) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.health = health;
        this.speed = speed;
        this.ability = ability;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public String getAbility() {
        return ability;
    }
}
