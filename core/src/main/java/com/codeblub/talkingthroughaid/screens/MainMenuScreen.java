package com.codeblub.talkingthroughaid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.codeblub.talkingthroughaid.TalkingThroughAidGame;
import com.codeblub.talkingthroughaid.screens.GameScreen;
import com.codeblub.talkingthroughaid.screens.MultiplayerScreen;

public class MainMenuScreen implements Screen {
    private final TalkingThroughAidGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final int buttonX = 100;
    private final int buttonY = 480;
    private final int buttonWidth = 420;
    private final int buttonHeight = 80;
    private final int secondButtonY = 360;

    public MainMenuScreen(TalkingThroughAidGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1280, 720);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Talking Through Aid", 100, 640);
        font.draw(batch, "Choose your mode:", 100, 600);
        font.draw(batch, "[ SOLO TRAINING ]", buttonX + 20, buttonY + 55);
        font.draw(batch, "[ MULTIPLAYER ]", buttonX + 20, secondButtonY + 55);
        batch.end();

        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (touchX >= buttonX && touchX <= buttonX + buttonWidth && touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                Gdx.app.log("MainMenu", "Solo button pressed");
                game.setScreen(new LoadoutScreen(game));
            } else if (touchX >= buttonX && touchX <= buttonX + buttonWidth && touchY >= secondButtonY && touchY <= secondButtonY + buttonHeight) {
                Gdx.app.log("MainMenu", "Multiplayer button pressed");
                game.setScreen(new MultiplayerScreen(game));
            }
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
