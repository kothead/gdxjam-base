package com.kothead.gdxjam.base.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kothead.gdxjam.base.GdxJamGame;
import com.kothead.gdxjam.base.data.GdxJamConfiguration;
import com.kothead.gdxjam.base.util.Utils;

public abstract class BaseScreen extends ScreenAdapter {

    private GdxJamGame game;
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private float worldWidth, worldHeight;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private Stage stage;
    private InputMultiplexer multiplexer;

    public BaseScreen(GdxJamGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        calcWorldSize();
        viewport = new ExtendViewport(worldWidth, worldHeight, camera);

        batch = new SpriteBatch();
        stage = new Stage(viewport);
        shapes = new ShapeRenderer();
        multiplexer = new InputMultiplexer();
    }

    protected abstract void layoutViewsLandscape(int width, int height);

    protected abstract void layoutViewsPortrait(int width, int height);

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        updateGraphics(width, height);
        layoutViews(width, height);
    }

    public GdxJamGame getGame() {
        return game;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch batch() {
        return batch;
    }

    public ShapeRenderer shapes() {
        return shapes;
    }

    public AssetManager assets() {
        return getGame().getAssetManager();
    }

    public Stage stage() {
        return stage;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void addInputProcessor(InputProcessor processor) {
        multiplexer.addProcessor(processor);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void updateGraphics(int width, int height) {
        calcWorldSize();

        viewport.setMinWorldWidth(worldWidth);
        viewport.setMinWorldHeight(worldHeight);
        viewport.update(width, height, true);

        worldWidth = viewport.getWorldWidth();
        worldHeight = viewport.getWorldHeight();

        batch.setProjectionMatrix(getCamera().combined);
        shapes.setProjectionMatrix(getCamera().combined);
    }

    private void calcWorldSize() {
        GdxJamConfiguration configuration = getGame().getConfiguration();
        worldWidth = configuration.width * configuration.scaleFactor;
        worldHeight = configuration.height * configuration.scaleFactor;

        if (Utils.isLandscape()) {
            float temp = worldHeight;
            worldHeight = worldWidth;
            worldWidth = temp;
        }
    }

    private void layoutViews(int width, int height) {
        if (Utils.isLandscape()) {
            layoutViewsLandscape(width, height);
        } else {
            layoutViewsPortrait(width, height);
        }
    }

    @Override
    public void dispose() {
        shapes().dispose();
        stage().dispose();
        batch().dispose();
        super.dispose();
    }
}
