package com.kothead.gdxjam.base.context;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.kothead.gdxjam.base.GdxJamGame;
import com.kothead.gdxjam.base.screen.ScreenBuilder;
import com.kothead.gdxjam.base.context.LoadingContext.LoadingProgressListener;

public class DefaultContext implements Context {

    public static final Context create(ScreenBuilder contextScreen,
                                       ScreenBuilder<? extends LoadingProgressListener> loadingScreen,
                                       AssetDescriptor... descriptors) {
        Context context = new DefaultContext();
        return wrap(context, contextScreen, loadingScreen, descriptors);
    }

    public static final Context wrap(Context context,
                                     ScreenBuilder contextScreen,
                                     ScreenBuilder<? extends LoadingProgressListener> loadingScreen,
                                     AssetDescriptor... descriptors) {
        if (contextScreen != null) context = new ScreenContext(context, contextScreen);
        if (loadingScreen != null) context = new LoadingContext(context, loadingScreen, descriptors);
        return context;
    }

    @Override
    public void enter(GdxJamGame game) {

    }

    @Override
    public void update(GdxJamGame game) {

    }

    @Override
    public void exit(GdxJamGame game) {

    }

    @Override
    public boolean onMessage(GdxJamGame game, Telegram telegram) {
        return false;
    }
}
