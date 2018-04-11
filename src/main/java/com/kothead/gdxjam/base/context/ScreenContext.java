package com.kothead.gdxjam.base.context;

import com.badlogic.gdx.Screen;
import com.kothead.gdxjam.base.GdxJamGame;
import com.kothead.gdxjam.base.screen.ScreenBuilder;

public class ScreenContext extends ContextProxy {

    private ScreenBuilder builder;

    public ScreenContext(Context context, ScreenBuilder builder) {
        super(context);
        this.builder = builder;
    }

    @Override
    public void enter(GdxJamGame game) {
        game.setScreen(builder.build(game));
        super.enter(game);
    }

    @Override
    public void exit(GdxJamGame game) {
        Screen screen = game.getScreen();
        if (screen != null) {
            screen.dispose();
        }
        game.setScreen(null);
        super.exit(game);
    }
}
