package com.codeblub.talkingthroughaid.game;

import java.util.Random;

public class Bot {
    private float x, y;
    private float health;
    private Difficulty difficulty;
    private boolean alive;
    private Random random;
    private float targetX, targetY;
    private float lastShotTime;
    private static final float SHOT_COOLDOWN = 1.0f; // seconds

    public Bot(float startX, float startY, Difficulty difficulty) {
        this.x = startX;
        this.y = startY;
        this.health = 100;
        this.difficulty = difficulty;
        this.alive = true;
        this.random = new Random();
        this.targetX = startX;
        this.targetY = startY;
        this.lastShotTime = 0;
    }

    public void update(float delta, Player player) {
        if (!alive) return;

        switch (difficulty) {
            case EASY:
                updateEasy(delta);
                break;
            case NORMAL:
                updateNormal(delta, player);
                break;
            case HARD:
                updateHard(delta, player);
                break;
        }

        lastShotTime += delta;
    }

    private void updateEasy(float delta) {
        // Random movement
        if (random.nextFloat() < 0.02f) {
            targetX = random.nextFloat() * 1280;
            targetY = random.nextFloat() * 720;
        }
        moveTowards(targetX, targetY, delta * 50);
    }

    private void updateNormal(float delta, Player player) {
        // Move towards player sometimes
        if (random.nextFloat() < 0.05f) {
            targetX = player.getX();
            targetY = player.getY();
        } else if (random.nextFloat() < 0.02f) {
            targetX = random.nextFloat() * 1280;
            targetY = random.nextFloat() * 720;
        }
        moveTowards(targetX, targetY, delta * 60);

        // Shoot if close
        if (distanceTo(player) < 200 && lastShotTime > SHOT_COOLDOWN) {
            shootAt(player);
        }
    }

    private void updateHard(float delta, Player player) {
        // Always target player
        targetX = player.getX();
        targetY = player.getY();
        moveTowards(targetX, targetY, delta * 80);

        // Shoot more frequently
        if (distanceTo(player) < 300 && lastShotTime > SHOT_COOLDOWN * 0.8f) {
            shootAt(player);
        }
    }

    private void moveTowards(float tx, float ty, float speed) {
        float dx = tx - x;
        float dy = ty - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist > 0) {
            x += (dx / dist) * speed;
            y += (dy / dist) * speed;
        }
        x = Math.max(0, Math.min(1280, x));
        y = Math.max(0, Math.min(720, y));
    }

    private float distanceTo(Player player) {
        float dx = player.getX() - x;
        float dy = player.getY() - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private void shootAt(Player player) {
        // Simple damage
        player.takeDamage(10 + random.nextInt(10));
        lastShotTime = 0;
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
    public Difficulty getDifficulty() { return difficulty; }
}