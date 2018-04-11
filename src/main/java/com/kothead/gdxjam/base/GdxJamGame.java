package com.kothead.gdxjam.base;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.assets.AssetManager;
import com.kothead.gdxjam.base.context.Context;
import com.kothead.gdxjam.base.data.GdxJamConfiguration;

public class GdxJamGame extends Game {

    private GdxJamConfiguration configuration;
    private StateMachine<GdxJamGame, Context> stateMachine;
    private AssetManager assetManager;
    private Engine engine;

    public GdxJamGame(GdxJamConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void create() {
        stateMachine = new StackStateMachine<>(this);
        assetManager = new AssetManager();
        engine = new Engine();
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render() {
        super.render();
        stateMachine.update();
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

    public StateMachine<GdxJamGame, Context> getStateMachine() {
        return stateMachine;
    }
}
