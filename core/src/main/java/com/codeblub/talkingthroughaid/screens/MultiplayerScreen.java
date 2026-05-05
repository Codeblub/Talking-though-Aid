package com.codeblub.talkingthroughaid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.codeblub.talkingthroughaid.TalkingThroughAidGame;
import com.codeblub.talkingthroughaid.multiplayer.MultiplayerClient;

public class MultiplayerScreen implements Screen {
    private final TalkingThroughAidGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final MultiplayerClient client;
    private String status;
    private boolean ready;

    public MultiplayerScreen(TalkingThroughAidGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1280, 720);
        this.client = new MultiplayerClient("Player1");
        this.status = "Connecting to match server...";
        this.ready = false;
    }

    @Override
    public void show() {
        if (client.connect("127.0.0.1", 9000)) {
            status = "Connected. Searching for match...";
            if (client.joinMatch("codm-lobby-1")) {
                ready = true;
                status = "Match ready! Tap to start battle.";
            }
        } else {
            status = "Connection failed.";
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.04f, 0.06f, 0.12f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "CODM Multiplayer", 100, 640);
        font.draw(batch, status, 100, 600);
        font.draw(batch, "Tap anywhere to return to menu.", 100, 560);
        batch.end();

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            client.disconnect();
            game.setScreen(new MainMenuScreen(game));
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
