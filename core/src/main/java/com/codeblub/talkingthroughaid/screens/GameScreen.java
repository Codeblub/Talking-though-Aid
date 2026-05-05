package com.codeblub.talkingthroughaid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.codeblub.talkingthroughaid.TalkingThroughAidGame;

public class GameScreen implements Screen {
    private final TalkingThroughAidGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final String weapon;
    private final String map;
    private final String character;

    public GameScreen(TalkingThroughAidGame game, String weapon, String map, String character) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1280, 720);
        this.weapon = weapon;
        this.map = map;
        this.character = character;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.10f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Game started!", 100, 660);
        font.draw(batch, "Character: " + character, 100, 620);
        font.draw(batch, "Weapon: " + getWeaponName(weapon), 100, 580);
        font.draw(batch, "Map: " + getMapName(map), 100, 540);
        font.draw(batch, "Tap/click anywhere to return to menu.", 100, 500);
        batch.end();

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
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
