package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.ComponentMapper;
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
import com.kothead.gdxjam.base.component.VelocityComponent;

// Collision resolution from https://www.defold.com/tutorials/runner/
public abstract class CollisionResolutionSystem extends IteratingSystem {

    private Family family;
    private ComponentMapper<? extends CollisionBoxComponent> firstMapper;
    private ComponentMapper<? extends CollisionBoxComponent> secondMapper;
    private ImmutableArray<Entity> entities;

    public CollisionResolutionSystem(int priority, Family firstFamily, Family secondFamily) {
        this(priority, firstFamily, secondFamily,
                CollisionBoxComponent.mapper, CollisionBoxComponent.mapper);
    }

    public CollisionResolutionSystem(int priority,
                                     Family firstFamily, Family secondFamily,
                                     ComponentMapper<? extends CollisionBoxComponent> firstMapper,
                                     ComponentMapper<? extends CollisionBoxComponent> secondMapper) {
        super(firstFamily, priority);
        family = secondFamily;
        this.firstMapper = firstMapper;
        this.secondMapper = secondMapper;
    }

    protected abstract void onCollisionResolution(Entity entity, Vector2 correction);

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(family);
    }

    @Override
    protected void processEntity(Entity first, float deltaTime) {
        if (!firstMapper.has(first)) return;

        Vector2 position = PositionComponent.mapper.get(first).position;
        Vector2 velocity = VelocityComponent.mapper.get(first).velocity;
        Vector2 correction = new Vector2();

        for (Entity second: entities) {
            if (first == second || !secondMapper.has(second)) continue;

            handleContact(getCollisionBox(first, firstMapper),
                    getCollisionBox(second, secondMapper),
                    position, velocity, correction);
        }

        if (!correction.isZero()) onCollisionResolution(first, correction);
    }

    private Polygon getCollisionBox(Entity entity,
                                    ComponentMapper<? extends CollisionBoxComponent> mapper) {
        Vector2 position = PositionComponent.mapper.get(entity).position;
        Polygon collisionBox = mapper.get(entity).collisionBox;
        collisionBox = new Polygon(collisionBox.getTransformedVertices());
        collisionBox.translate(position.x, position.y);
        return collisionBox;
    }

    /**
     * Handles contact between two polygons. Updates position, velocity and correction vectors
     * @param polygon1 collision box of first object
     * @param polygon2 collision box of second object
     * @param position of first object
     * @param velocity of first object
     * @param correction of collision translations
     */
    private void handleContact(Polygon polygon1, Polygon polygon2,
                                  Vector2 position, Vector2 velocity,
                                  Vector2 correction) {
        Intersector.MinimumTranslationVector translation = new Intersector.MinimumTranslationVector();
        Intersector.overlapConvexPolygons(polygon1, polygon2, translation);
        if (translation.normal.isZero()) return;

        float projection = correction.dot(translation.normal);

        float difference = translation.depth - projection;
        Vector2 compensation = new Vector2(
                translation.normal.x * difference,
                translation.normal.y * difference);
        correction.add(compensation);
        position.add(compensation);

        projection = velocity.dot(translation.normal);
        if (projection < 0) {
            compensation = new Vector2(
                    translation.normal.x * projection,
                    translation.normal.y * projection);
            velocity.sub(compensation);
        }
    }
}
