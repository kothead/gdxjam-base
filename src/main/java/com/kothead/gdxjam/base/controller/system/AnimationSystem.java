package com.kothead.gdxjam.base.controller.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.model.component.AnimationComponent;
import com.kothead.gdxjam.base.model.component.SpriteComponent;

public class AnimationSystem extends IteratingSystem {

    public AnimationSystem(int priority) {
        super(Family.all(SpriteComponent.class, AnimationComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Sprite sprite = SpriteComponent.mapper.get(entity).sprite;
        AnimationComponent animationComponent = AnimationComponent.mapper.get(entity);
        animationComponent.time += deltaTime;

        TextureRegion region = animationComponent.animation.getKeyFrame(animationComponent.time);
        sprite.setRegion(region);
    }
}
