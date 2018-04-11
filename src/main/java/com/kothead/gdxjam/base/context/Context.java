package com.kothead.gdxjam.base.context;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.kothead.gdxjam.base.GdxJamGame;

public interface Context extends State<GdxJamGame> {
    void enter(GdxJamGame game);
    void update(GdxJamGame game);
    void exit(GdxJamGame game);
    boolean onMessage(GdxJamGame game, Telegram telegram);
}
