package com.codeblub.talkingthroughaid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.codeblub.talkingthroughaid.TalkingThroughAidGame;
import com.codeblub.talkingthroughaid.game.Difficulty;

public class LoadoutScreen implements Screen {
    private final TalkingThroughAidGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private String selectedWeapon;
    private String selectedMap;
    private String character;
    private Difficulty selectedDifficulty;

    public LoadoutScreen(TalkingThroughAidGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1280, 720);
        this.selectedWeapon = "striker";
        this.selectedMap = "night_street";
        this.character = "Raptor (Assault)";
        this.selectedDifficulty = Difficulty.NORMAL;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Loadout", 100, 640);
        font.draw(batch, "Character: " + character, 100, 600);
        font.draw(batch, "Shotguns:", 100, 560);
        font.draw(batch, "  - Striker", 120, 540);
        font.draw(batch, "SMGs:", 100, 500);
        font.draw(batch, "  - Switchblade X9", 120, 480);
        font.draw(batch, "Snipers:", 100, 440);
        font.draw(batch, "  - Rytech AMR", 120, 420);
        font.draw(batch, "Selected Weapon: " + getWeaponName(selectedWeapon), 100, 380);
        font.draw(batch, "Maps:", 100, 340);
        font.draw(batch, "  - Night Street", 120, 320);
        font.draw(batch, "Selected Map: " + getMapName(selectedMap), 100, 280);
        font.draw(batch, "Difficulty:", 100, 240);
        font.draw(batch, "  - Easy", 120, 220);
        font.draw(batch, "  - Normal", 120, 200);
        font.draw(batch, "  - Hard", 120, 180);
        font.draw(batch, "Selected Difficulty: " + selectedDifficulty, 100, 140);
        font.draw(batch, "[START GAME]", 100, 100);
        batch.end();

        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (touchX >= 100 && touchX <= 200 && touchY >= 80 && touchY <= 100) {
                game.setScreen(new GameScreen(game, selectedWeapon, selectedMap, character, selectedDifficulty));
            } else if (touchX >= 120 && touchX <= 220 && touchY >= 520 && touchY <= 540) {
                selectedWeapon = "striker";
            } else if (touchX >= 120 && touchX <= 250 && touchY >= 460 && touchY <= 480) {
                selectedWeapon = "switchblade_x9";
            } else if (touchX >= 120 && touchX <= 220 && touchY >= 400 && touchY <= 420) {
                selectedWeapon = "rytech_amr";
            } else if (touchX >= 120 && touchX <= 220 && touchY >= 300 && touchY <= 320) {
                selectedMap = "night_street";
            } else if (touchX >= 120 && touchX <= 200 && touchY >= 200 && touchY <= 220) {
                selectedDifficulty = Difficulty.EASY;
            } else if (touchX >= 120 && touchX <= 200 && touchY >= 180 && touchY <= 200) {
                selectedDifficulty = Difficulty.NORMAL;
            } else if (touchX >= 120 && touchX <= 200 && touchY >= 160 && touchY <= 180) {
                selectedDifficulty = Difficulty.HARD;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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