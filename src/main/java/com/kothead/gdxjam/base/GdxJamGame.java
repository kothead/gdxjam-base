package com.kothead.gdxjam.base;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.context.Context;
import com.kothead.gdxjam.base.data.GameAudioManager;
import com.kothead.gdxjam.base.data.GdxJamConfiguration;
import com.kothead.gdxjam.base.data.loader.TextureAnimationLoader;
import com.kothead.gdxjam.base.data.loader.TextureRegionLoader;
import com.kothead.gdxjam.base.util.TextureAnimation;

public class GdxJamGame extends Game {

    private GdxJamConfiguration configuration;
    private AssetManager assetManager;
    private StateMachine<GdxJamGame, Context> stateMachine;
    private Engine engine;
    private GameAudioManager audioManager;

    public GdxJamGame(GdxJamConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void create() {
        assetManager = new AssetManager();
        assetManager.setLoader(TextureRegion.class, new TextureRegionLoader(new FileHandleResolver() {
            @Override
            public FileHandle resolve(String fileName) {
                return null;
            }
        }));
        assetManager.setLoader(TextureAnimation.class, new TextureAnimationLoader(new InternalFileHandleResolver()));;

        stateMachine = new StackStateMachine<>(this);
        engine = new Engine();
        audioManager = new GameAudioManager();
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render() {
        stateMachine.update();                      // sets current screen
        super.render();                             // render background of current screen
        engine.update(Gdx.graphics.getDeltaTime()); // rendering of game objects happens here
    }

    public GdxJamConfiguration getConfiguration() {
        return configuration;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public StateMachine<GdxJamGame, Context> getStateMachine() {
        return stateMachine;
    }

    public Engine getEngine() {
        return engine;
    }

    public GameAudioManager getAudioManager() {
        return audioManager;
    }
}
