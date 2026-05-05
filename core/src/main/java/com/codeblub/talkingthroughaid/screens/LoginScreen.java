package com.codeblub.talkingthroughaid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input;
import com.codeblub.talkingthroughaid.TalkingThroughAidGame;

public class LoginScreen implements Screen {
    private final TalkingThroughAidGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final Preferences prefs;
    private String username;
    private String password;
    private boolean isLoginMode;
    private String message;

    public LoginScreen(TalkingThroughAidGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1280, 720);
        this.prefs = Gdx.app.getPreferences("TalkingThroughAid");
        this.username = "";
        this.password = "";
        this.isLoginMode = true;
        this.message = "";
    }

    @Override
    public void show() {
        String savedUsername = prefs.getString("username", "");
        if (!savedUsername.isEmpty()) {
            username = savedUsername;
            login();
        }
    }

    private void login() {
        if (!username.isEmpty()) {
            prefs.putString("username", username);
            prefs.flush();
            game.setScreen(new MainMenuScreen(game));
        } else {
            message = "Please enter a username";
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Talking Through Aid", 100, 640);
        font.draw(batch, isLoginMode ? "Login" : "Sign Up", 100, 600);
        font.draw(batch, "Username: " + username, 100, 560);
        font.draw(batch, "Password: " + "*".repeat(password.length()), 100, 520);
        font.draw(batch, "[LOGIN]", 100, 460);
        font.draw(batch, "[SIGN UP]", 300, 460);
        font.draw(batch, message, 100, 400);
        batch.end();

        // Simple input handling - in real app, use TextInputListener
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (touchX >= 100 && touchX <= 200 && touchY >= 430 && touchY <= 470) {
                login();
            } else if (touchX >= 300 && touchX <= 400 && touchY >= 430 && touchY <= 470) {
                isLoginMode = !isLoginMode;
                message = "";
            }
        }

        // For demo, allow typing username
        for (int key = Input.Keys.A; key <= Input.Keys.Z; key++) {
            if (Gdx.input.isKeyJustPressed(key)) {
                username += (char) ('A' + (key - Input.Keys.A));
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && !username.isEmpty()) {
            username = username.substring(0, username.length() - 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            login();
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