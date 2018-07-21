package com.kothead.gdxjam.base.data.loader;

import com.badlogic.gdx.assets.loaders.BitmapFontLoader;

public class BitmapFontParameter extends BitmapFontLoader.BitmapFontParameter {

    public BitmapFontParameter(String atlasName) {
        super();

        this.atlasName = atlasName;
    }
}
