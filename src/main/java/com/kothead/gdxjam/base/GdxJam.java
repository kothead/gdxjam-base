package com.kothead.gdxjam.base;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.assets.AssetManager;
import com.kothead.gdxjam.base.context.Context;
import com.kothead.gdxjam.base.data.GameAudioManager;

public class GdxJam {

    public static GdxJamGame game() {
        return (GdxJamGame) Gdx.app.getApplicationListener();
    }

    public static AssetManager assets() {
        return game().getAssetManager();
    }

    public static Engine engine() {
        return game().getEngine();
    }

    public static GameAudioManager music() {
        return game().getAudioManager();
    }

    public static StateMachine<GdxJamGame, Context> contexts() {
        return game().getStateMachine();
    }
}
