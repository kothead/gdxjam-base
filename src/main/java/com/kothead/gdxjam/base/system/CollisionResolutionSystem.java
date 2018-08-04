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
import com.badlogic.gdx.math.Vector3;
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

    protected abstract void onCollisionResolution(Entity entity,
                                                  Vector2 positionCorrection,
                                                  Vector2 velocityCorrection);

    protected abstract void onCollisionDetection(Entity first, Entity second,
                                                 Vector2 positionCorrection,
                                                 Vector2 velocityCorrection);

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(family);
    }

    @Override
    protected void processEntity(Entity first, float deltaTime) {
        if (!firstMapper.has(first)) return;

        Vector3 position = PositionComponent.mapper.get(first).position;
        Vector2 velocity = VelocityComponent.mapper.get(first).velocity;
        Vector2 positionCorrection = new Vector2();
        Vector2 velocityCorrection = new Vector2(velocity);

        for (Entity second: entities) {
            if (first == second || !secondMapper.has(second)) continue;

            handleContact(first, second, position, velocity, positionCorrection);
        }

        velocityCorrection.sub(velocity);
        if (!positionCorrection.isZero()) onCollisionResolution(first, positionCorrection, velocityCorrection);
    }

    private void handleContact(Entity entity1, Entity entity2,
                               Vector3 position, Vector2 velocity,
                               Vector2 correction) {
        Vector2 positionCorrection = new Vector2();
        Vector2 velocityCorrection = new Vector2();
        handleContact(
                getCollisionBox(entity1, firstMapper),
                getCollisionBox(entity2, secondMapper),
                position, velocity, correction,
                positionCorrection, velocityCorrection
        );
        correction.add(positionCorrection);
        if (!positionCorrection.isZero()) {
            onCollisionDetection(
                    entity1, entity2,
                    positionCorrection, velocityCorrection
            );
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

    /**
     * Handles contact between two polygons. Updates position, velocity and correction vectors
     * @param polygon1 collision box of first object
     * @param polygon2 collision box of second object
     * @param position of first object
     * @param velocity of first object
     * @param correction of all collision translations
     * @param positionCorrection of this specific collision
     * @param velocityCorrection of this specific collision
     */
    private void handleContact(Polygon polygon1, Polygon polygon2,
                                  Vector3 position, Vector2 velocity, Vector2 correction,
                                  Vector2 positionCorrection, Vector2 velocityCorrection) {
        Intersector.MinimumTranslationVector translation = new Intersector.MinimumTranslationVector();
        Intersector.overlapConvexPolygons(polygon1, polygon2, translation);
        if (translation.normal.isZero()) return;

        float projection = correction.dot(translation.normal);

        float difference = translation.depth - projection;
        positionCorrection.set(
                translation.normal.x * difference,
                translation.normal.y * difference
        );
        position.add(positionCorrection.x, positionCorrection.y, 0);

        projection = velocity.dot(translation.normal);
        if (projection < 0) {
            velocityCorrection.set(
                    translation.normal.x * projection,
                    translation.normal.y * projection
            );
            velocity.sub(velocityCorrection);
        }
    }
}
