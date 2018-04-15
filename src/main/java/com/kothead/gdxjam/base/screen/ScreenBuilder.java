package com.kothead.gdxjam.base.screen;

import com.kothead.gdxjam.base.GdxJamGame;

public interface ScreenBuilder<T extends BaseScreen> {
    T build(GdxJamGame game);
}
