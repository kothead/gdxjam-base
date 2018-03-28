package com.kothead.gdxjam.base.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {

    public static final ComponentMapper<AnimationComponent> mapper = ComponentMapper.getFor(AnimationComponent.class);

    public Animation<TextureRegion> animation;
    public float time = 0.0f;

    public AnimationComponent(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(time);
    }
}
