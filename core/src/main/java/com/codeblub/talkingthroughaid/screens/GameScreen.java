package com.codeblub.talkingthroughaid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.codeblub.talkingthroughaid.TalkingThroughAidGame;
import com.codeblub.talkingthroughaid.game.Bot;
import com.codeblub.talkingthroughaid.game.Difficulty;
import com.codeblub.talkingthroughaid.game.GameWorld;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final TalkingThroughAidGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera hudCamera;
    private final PerspectiveCamera camera3D;
    private final ModelBatch modelBatch;
    private final Model floorModel;
    private final Model playerModel;
    private final Model botModel;
    private final Model gunModel;
    private final ModelInstance floorInstance;
    private final ModelInstance playerInstance;
    private final ModelInstance gunInstance;
    private final List<ModelInstance> botInstances;
    private final Environment environment;
    private final String weapon;
    private final String map;
    private final String character;
    private final Difficulty difficulty;
    private final GameWorld world;
    private final float eyeHeight = 20f;
    private float yaw;
    private final Vector3 forward;
    private boolean aiming;
    private float lastShotTime;
    private static final float SHOT_COOLDOWN = 0.5f;

    public GameScreen(TalkingThroughAidGame game, String weapon, String map, String character, Difficulty difficulty) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.hudCamera = new OrthographicCamera();
        this.hudCamera.setToOrtho(false, 1280, 720);

        this.camera3D = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera3D.near = 1f;
        this.camera3D.far = 3000f;

        this.modelBatch = new ModelBatch();
        ModelBuilder builder = new ModelBuilder();

        this.floorModel = builder.createBox(1280f, 4f, 720f,
                new com.badlogic.gdx.graphics.g3d.Material(ColorAttribute.createDiffuse(Color.DARK_GRAY)),
                Usage.Position | Usage.Normal);
        this.playerModel = builder.createBox(40f, 80f, 40f,
                new com.badlogic.gdx.graphics.g3d.Material(ColorAttribute.createDiffuse(Color.BLUE)),
                Usage.Position | Usage.Normal);
        this.botModel = builder.createBox(40f, 80f, 40f,
                new com.badlogic.gdx.graphics.g3d.Material(ColorAttribute.createDiffuse(Color.RED)),
                Usage.Position | Usage.Normal);

        this.floorInstance = new ModelInstance(floorModel);
        this.playerInstance = new ModelInstance(playerModel);
        this.gunModel = builder.createBox(80f, 20f, 15f,
                new com.badlogic.gdx.graphics.g3d.Material(ColorAttribute.createDiffuse(Color.SKY)),
                Usage.Position | Usage.Normal);
        this.gunInstance = new ModelInstance(gunModel);
        this.botInstances = new ArrayList<>();

        this.weapon = weapon;
        this.map = map;
        this.character = character;
        this.difficulty = difficulty;
        this.world = new GameWorld(weapon, character, map, difficulty);
        this.yaw = 0f;
        this.forward = new Vector3(0, 0, -1);
        this.lastShotTime = 0;

        for (int i = 0; i < world.getBots().size(); i++) {
            botInstances.add(new ModelInstance(botModel));
        }

        this.aiming = false;
        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.7f, 0.7f, 0.7f, 1f));
        this.environment.add(new DirectionalLight().set(1f, 1f, 1f, -0.4f, -1f, -0.4f));

        floorInstance.transform.setTranslation(640f, -2f, 360f);
        updateInstances();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        world.update(delta);
        lastShotTime += delta;
        updateCamera();
        updateInstances();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0.08f, 0.10f, 0.14f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera3D);
        modelBatch.render(floorInstance, environment);
        for (int i = 0; i < world.getBots().size(); i++) {
            if (world.getBots().get(i).isAlive()) {
                modelBatch.render(botInstances.get(i), environment);
            }
        }
        modelBatch.render(gunInstance, environment);
        modelBatch.end();

        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        font.draw(batch, "Map: " + getMapName(map), 10, 710);
        font.draw(batch, "Character: " + character, 10, 680);
        font.draw(batch, "Weapon: " + getWeaponName(weapon), 10, 650);
        font.draw(batch, "Difficulty: " + difficulty, 10, 620);
        font.draw(batch, "+", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + 5);

        if (world.isGameOver()) {
            font.draw(batch, "GAME OVER - Winner: " + world.getWinner(), 420, 360);
            font.draw(batch, "Click to return to menu or press ESC", 420, 340);
        } else {
            font.draw(batch, "WASD to move, LEFT/RIGHT to turn, LMB to shoot, RMB to aim", 10, 50);
            font.draw(batch, "Aiming: " + (aiming ? "ON" : "OFF"), 10, 30);
        }
        batch.end();

        handleInput(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
        if (world.isGameOver() && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void handleInput(float delta) {
        if (world.isGameOver()) return;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            yaw += 120f * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            yaw -= 120f * delta;
        }

        aiming = Gdx.input.isButtonPressed(Input.Buttons.RIGHT);

        float yawRad = (float) Math.toRadians(yaw);
        forward.set((float) Math.sin(yawRad), 0, (float) -Math.cos(yawRad)).nor();
        Vector3 right = forward.cpy().crs(new Vector3(0f, 1f, 0f)).nor();
        Vector3 move = new Vector3();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) move.add(forward);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) move.sub(forward);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) move.add(right);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) move.sub(right);

        if (move.len() > 0.01f) {
            move.nor().scl(220 * delta);
            world.getPlayer().move(move.x, move.z);
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && lastShotTime > SHOT_COOLDOWN) {
            shootAtNearestBot();
            lastShotTime = 0;
        }
    }

    private void updateCamera() {
        float px = world.getPlayer().getX();
        float pz = world.getPlayer().getY();
        camera3D.position.set(px, eyeHeight, pz);
        camera3D.direction.set(forward);
        camera3D.up.set(0f, 1f, 0f);
        camera3D.update();
    }

    private void updateInstances() {
        var player = world.getPlayer();
        playerInstance.transform.setTranslation(player.getX(), 40f, player.getY());

        for (int i = 0; i < world.getBots().size(); i++) {
            var bot = world.getBots().get(i);
            botInstances.get(i).transform.setTranslation(bot.getX(), 40f, bot.getY());
        }

        Vector3 camPos = camera3D.position.cpy();
        Vector3 gunOffset = forward.cpy().scl(40f).add(0f, -12f, 0f);
        gunInstance.transform.setToTranslation(camPos.add(gunOffset));
    }

    private void shootAtNearestBot() {
        var player = world.getPlayer();
        var bots = world.getBots();
        float minDist = Float.MAX_VALUE;
        Bot target = null;
        for (Bot bot : bots) {
            if (bot.isAlive()) {
                float dx = bot.getX() - player.getX();
                float dz = bot.getY() - player.getY();
                float dist = (float) Math.sqrt(dx * dx + dz * dz);
                if (dist < minDist) {
                    minDist = dist;
                    target = bot;
                }
            }
        }
        if (target != null && minDist < 220) {
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
        if ("night_street".equals(id)) {
            return "Night Street";
        }
        return "Unknown";
    }

    @Override
    public void resize(int width, int height) {
        hudCamera.setToOrtho(false, width, height);
        camera3D.viewportWidth = width;
        camera3D.viewportHeight = height;
        camera3D.update();
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
        modelBatch.dispose();
        floorModel.dispose();
        playerModel.dispose();
        gunModel.dispose();
        botModel.dispose();
    }
}
