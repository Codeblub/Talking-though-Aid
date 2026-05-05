package com.codeblub.talkingthroughaid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.codeblub.talkingthroughaid.TalkingThroughAidGame;
import com.codeblub.talkingthroughaid.game.GameWorld;
import com.codeblub.talkingthroughaid.game.Difficulty;
import com.codeblub.talkingthroughaid.game.Bot;

import com.codeblub.talkingthroughaid.game.GameWorld;
import com.codeblub.talkingthroughaid.game.Difficulty;

public class GameScreen implements Screen {
    private final TalkingThroughAidGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final String weapon;
    private final String map;
    private final String character;
    private final Difficulty difficulty;
    private GameWorld world;
    private float lastShotTime;
    private static final float SHOT_COOLDOWN = 0.5f;

    public GameScreen(TalkingThroughAidGame game, String weapon, String map, String character, Difficulty difficulty) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1280, 720);
        this.weapon = weapon;
        this.map = map;
        this.character = character;
        this.difficulty = difficulty;
        this.world = new GameWorld(weapon, character, map, difficulty);
        this.lastShotTime = 0;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        world.update(delta);
        lastShotTime += delta;

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.10f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Draw map background
        font.draw(batch, "Map: " + getMapName(map), 10, 710);

        // Draw player
        font.draw(batch, "P", world.getPlayer().getX(), world.getPlayer().getY());
        font.draw(batch, "HP: " + (int)world.getPlayer().getHealth(), world.getPlayer().getX() - 20, world.getPlayer().getY() + 20);

        // Draw bots
        for (int i = 0; i < world.getBots().size(); i++) {
            var bot = world.getBots().get(i);
            if (bot.isAlive()) {
                font.draw(batch, "B" + (i+1), bot.getX(), bot.getY());
                font.draw(batch, "HP: " + (int)bot.getHealth(), bot.getX() - 20, bot.getY() + 20);
            }
        }

        // Draw UI
        font.draw(batch, "Character: " + character, 10, 680);
        font.draw(batch, "Weapon: " + getWeaponName(weapon), 10, 650);
        font.draw(batch, "Difficulty: " + difficulty, 10, 620);

        if (world.isGameOver()) {
            font.draw(batch, "GAME OVER - Winner: " + world.getWinner(), 400, 400);
            font.draw(batch, "Tap to return to menu", 400, 360);
        } else {
            font.draw(batch, "WASD to move, SPACE to shoot", 10, 50);
        }

        batch.end();

        // Input handling
        if (!world.isGameOver()) {
            float moveSpeed = 200 * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                world.getPlayer().move(0, moveSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                world.getPlayer().move(0, -moveSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                world.getPlayer().move(-moveSpeed, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                world.getPlayer().move(moveSpeed, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && lastShotTime > SHOT_COOLDOWN) {
                shootAtNearestBot();
                lastShotTime = 0;
            }
        }

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void shootAtNearestBot() {
        var player = world.getPlayer();
        var bots = world.getBots();
        float minDist = Float.MAX_VALUE;
        Bot target = null;
        for (Bot bot : bots) {
            if (bot.isAlive()) {
                float dist = (float) Math.sqrt(Math.pow(bot.getX() - player.getX(), 2) + Math.pow(bot.getY() - player.getY(), 2));
                if (dist < minDist) {
                    minDist = dist;
                    target = bot;
                }
            }
        }
        if (target != null && minDist < 200) { // Range
            target.takeDamage(20 + (int)(Math.random() * 10));
        }
    }

    private String getWeaponName(String id) {
        switch (id) {
            case "striker": return "Striker";
            case "switchblade_x9": return "Switchblade X9";
            case "rytech_amr": return "Rytech AMR";
            default: return "Unknown";
        }
    }

    private String getMapName(String id) {
        switch (id) {
            case "night_street": return "Night Street";
            default: return "Unknown";
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
