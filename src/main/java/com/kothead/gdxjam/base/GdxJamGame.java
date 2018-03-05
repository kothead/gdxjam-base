package com.kothead.gdxjam.base;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.assets.AssetManager;
import com.kothead.gdxjam.base.data.GdxJamConfiguration;

public class GdxJamGame extends Game {

    private GdxJamConfiguration configuration;
    private StateMachine<GdxJamGame, ? extends State<GdxJamGame>> stateMachine;
    private AssetManager assetManager;
    private Engine engine;

    public GdxJamGame(GdxJamConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void create() {
        stateMachine = new DefaultStateMachine<>(this);
        assetManager = new AssetManager();
        engine = new Engine();
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render() {
        super.render();
        stateMachine.update();
    }

    @Override
    public void dispose() {
        Screen screen = getScreen();
        if (screen != null) screen.dispose();
        super.dispose();
    }

    @Override
    public void setScreen(Screen screen) {
        Screen old = getScreen();
        super.setScreen(screen);
        if (old != null) old.dispose();
    }

    public GdxJamConfiguration getConfiguration() {
        return configuration;
    }

    public Engine getEngine() {
        return engine;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
