package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.CollisionBoxComponent;
import com.kothead.gdxjam.base.component.PositionComponent;

public abstract class CollisionDetectionSystem extends IteratingSystem {

    private Family family;
    private ComponentMapper<? extends CollisionBoxComponent> firstMapper;
    private ComponentMapper<? extends CollisionBoxComponent> secondMapper;
    private ImmutableArray<Entity> entities;

    public CollisionDetectionSystem(int priority, Family firstFamily, Family secondFamily) {
        this(priority, firstFamily, secondFamily,
                CollisionBoxComponent.mapper, CollisionBoxComponent.mapper);
    }

    public CollisionDetectionSystem(int priority,
                                    Family firstFamily, Family secondFamily,
                                    ComponentMapper<? extends CollisionBoxComponent> firstMapper,
                                    ComponentMapper<? extends CollisionBoxComponent> secondMapper) {
        super(firstFamily, priority);
        family = secondFamily;
        this.firstMapper = firstMapper;
        this.secondMapper = secondMapper;
    }

    protected abstract void onCollisionDetected(Entity first, Entity second);

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(family);
    }

    @Override
    protected void processEntity(Entity first, float deltaTime) {
        if (!firstMapper.has(first)) return;

        for (Entity second: entities) {
            if (first == second || !secondMapper.has(second)) continue;

            if (intersects(getCollisionBox(first, firstMapper),
                    getCollisionBox(second, secondMapper))) {
                onCollisionDetected(first, second);
            }
        }
    }

    private Polygon getCollisionBox(Entity entity,
                                    ComponentMapper<? extends CollisionBoxComponent> mapper) {
        Vector3 position = PositionComponent.mapper.get(entity).position;
        Polygon collisionBox = mapper.get(entity).collisionBox;
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
