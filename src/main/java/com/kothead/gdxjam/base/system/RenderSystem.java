package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.component.SpriteComponent;

public class RenderSystem extends EntitySystem {

    private SpriteBatch batch;
    private ImmutableArray<Entity> entities;

    public RenderSystem(int priority, SpriteBatch batch) {
        super(priority);
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(
                SpriteComponent.class,
                PositionComponent.class)
                .get());
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        for (Entity entity: entities) {
            Vector2 position = PositionComponent.mapper.get(entity).position;
            Sprite sprite = SpriteComponent.mapper.get(entity).sprite;

            sprite.setPosition(position.x, position.y);
            sprite.draw(batch);
        }
        batch.end();
    }
}
