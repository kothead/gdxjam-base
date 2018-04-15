package com.kothead.gdxjam.base.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class TextureAnimation extends Animation<TextureRegion> {

    public TextureAnimation(float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
    }

    public TextureAnimation(float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
        super(frameDuration, keyFrames, playMode);
    }

    public TextureAnimation(float frameDuration, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
    }
}
