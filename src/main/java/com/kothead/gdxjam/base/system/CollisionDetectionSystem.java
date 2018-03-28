package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.kothead.gdxjam.base.component.CollisionBoxComponent;
import com.kothead.gdxjam.base.component.PositionComponent;

public abstract class CollisionDetectionSystem extends IteratingSystem {

    private Family family;
    private ImmutableArray<Entity> entities;

    public CollisionDetectionSystem(int priority, Family first, Family second) {
        super(first, priority);
        family = second;
    }

    protected abstract void onCollisionDetected(Entity first, Entity second);

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(family);
    }

    @Override
    protected void processEntity(Entity first, float deltaTime) {
        if (!CollisionBoxComponent.mapper.has(first)) return;

        for (Entity second: entities) {
            if (first == second || !CollisionBoxComponent.mapper.has(second)) continue;

            if (intersects(getCollisionBox(first), getCollisionBox(second))) {
                onCollisionDetected(first, second);
            }
        }
    }

    private Polygon getCollisionBox(Entity entity) {
        Vector2 position = PositionComponent.mapper.get(entity).position;
        Polygon collisionBox = CollisionBoxComponent.mapper.get(entity).collisionBox;
        collisionBox = new Polygon(collisionBox.getTransformedVertices());
        collisionBox.translate(position.x, position.y);
        return collisionBox;
    }

    private boolean intersects(Polygon first, Polygon second) {
        Intersector.MinimumTranslationVector vector = new Intersector.MinimumTranslationVector();
        Intersector.overlapConvexPolygons(first, second, vector);
        return vector.depth != 0;
    }
}
