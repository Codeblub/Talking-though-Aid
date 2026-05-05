package com.codeblub.talkingthroughaid.server;

public class WeaponInfo {
    private final String id;
    private final String name;
    private final String type;
    private final int damage;
    private final int range;
    private final float fireRate;

    public WeaponInfo(String id, String name, String type, int damage, int range, float fireRate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public float getFireRate() {
        return fireRate;
    }
}
