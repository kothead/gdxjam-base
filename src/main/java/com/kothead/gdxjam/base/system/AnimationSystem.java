package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.component.AnimationComponent;
import com.kothead.gdxjam.base.component.SpriteComponent;

public class AnimationSystem extends IteratingSystem {

    public AnimationSystem(int priority) {
        super(Family.all(AnimationComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = AnimationComponent.mapper.get(entity);
        animationComponent.time += deltaTime;

        TextureRegion region = animationComponent.animation.getKeyFrame(animationComponent.time);
        SpriteComponent spriteComponent = SpriteComponent.mapper.get(entity);
        if (spriteComponent != null) {
            Sprite sprite = spriteComponent.sprite;
            boolean flipX = sprite.isFlipX();
            boolean flipY = sprite.isFlipY();

            sprite.setRegion(region);
            sprite.setSize(region.getRegionWidth(), region.getRegionHeight());
            sprite.flip(flipX, flipY);
        } else {
            entity.add(new SpriteComponent(new Sprite(region)));
        }
    }
}
