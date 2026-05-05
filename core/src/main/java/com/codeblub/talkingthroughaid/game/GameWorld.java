package com.codeblub.talkingthroughaid.game;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {
    private Player player;
    private List<Bot> bots;
    private String map;
    private boolean gameOver;
    private String winner;

    public GameWorld(String weapon, String character, String map, Difficulty botDifficulty) {
        this.map = map;
        this.player = new Player(640, 360, weapon, character);
        this.bots = new ArrayList<>();
        // Spawn 3 bots at random positions
        for (int i = 0; i < 3; i++) {
            float x = 100 + i * 400;
            float y = 200 + i * 200;
            bots.add(new Bot(x, y, botDifficulty));
        }
        this.gameOver = false;
        this.winner = null;
    }

    public void update(float delta) {
        if (gameOver) return;

        for (Bot bot : bots) {
            if (bot.isAlive()) {
                bot.update(delta, player);
            }
        }

        // Check win conditions
        boolean allBotsDead = bots.stream().noneMatch(Bot::isAlive);
        boolean playerDead = !player.isAlive();

        if (allBotsDead) {
            gameOver = true;
            winner = "Player";
        } else if (playerDead) {
            gameOver = true;
            winner = "Bots";
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<Bot> getBots() {
        return bots;
    }

    public String getMap() {
        return map;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinner() {
        return winner;
    }
}