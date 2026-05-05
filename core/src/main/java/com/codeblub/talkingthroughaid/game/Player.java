package com.codeblub.talkingthroughaid.game;

public class Player {
    private float x, y;
    private float health;
    private String weapon;
    private String character;
    private boolean alive;

    public Player(float startX, float startY, String weapon, String character) {
        this.x = startX;
        this.y = startY;
        this.health = 100;
        this.weapon = weapon;
        this.character = character;
        this.alive = true;
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
        // Clamp to map bounds, assume 1280x720
        x = Math.max(0, Math.min(1280, x));
        y = Math.max(0, Math.min(720, y));
    }

    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getHealth() { return health; }
    public String getWeapon() { return weapon; }
    public String getCharacter() { return character; }
}