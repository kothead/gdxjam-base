package com.kothead.gdxjam.base.controller.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.kothead.gdxjam.base.model.component.CollidableComponent;
import com.kothead.gdxjam.base.model.component.CollisionBoxComponent;

public class SimpleCollisionResolutionSystem extends IteratingSystem {

    private static final float DEFAULT_CONTACT_THRESHOLD = 0.7f;

    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> collisionBoxes;

    public SimpleCollisionResolutionSystem(int priority, float contactThreshold) {
        super(Family.all(CollidableComponent.class, CollisionBoxComponent.class).get(), priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        collisionBoxes = engine.getEntitiesFor(Family.all(CollisionBoxComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        for (Entity box: collisionBoxes) {

        }
    }
}
