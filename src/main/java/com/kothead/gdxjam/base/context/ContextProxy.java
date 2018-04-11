package com.kothead.gdxjam.base.context;

import com.badlogic.gdx.ai.msg.Telegram;
import com.kothead.gdxjam.base.GdxJamGame;

public class ContextProxy implements Context {

    private Context context;

    public ContextProxy(Context context) {
        this.context = context;
    }

    @Override
    public void enter(GdxJamGame game) {
        if (context != null) context.enter(game);
    }

    @Override
    public void update(GdxJamGame game) {
        if (context != null) context.update(game);
    }

    @Override
    public void exit(GdxJamGame game) {
        if (context != null) context.exit(game);
    }

    @Override
    public boolean onMessage(GdxJamGame game, Telegram telegram) {
        return context != null ? context.onMessage(game, telegram) : false;
    }
}
