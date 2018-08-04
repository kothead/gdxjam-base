package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.component.SpriteComponent;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {

    private SpriteBatch batch;
    private ImmutableArray<Entity> entities;

    public RenderSystem(int priority, SpriteBatch batch) {
        super(Family.all(SpriteComponent.class, PositionComponent.class).get(),
                new ZComparator(), priority);
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector3 position = PositionComponent.mapper.get(entity).position;
        Sprite sprite = SpriteComponent.mapper.get(entity).sprite;

        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    private static class ZComparator implements Comparator<Entity> {

        @Override
        public int compare(Entity entity1, Entity entity2) {
            return (int) Math.signum(PositionComponent.mapper.get(entity1).position.z
                    - PositionComponent.mapper.get(entity2).position.z);
        }
    }
}
