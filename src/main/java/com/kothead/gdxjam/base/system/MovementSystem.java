package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.component.VelocityComponent;

public class MovementSystem extends IteratingSystem {

    public MovementSystem(int priority) {
        super(Family.all(PositionComponent.class,VelocityComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 position = PositionComponent.mapper.get(entity).position;
        Vector2 velocity = VelocityComponent.mapper.get(entity).velocity;

        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }
}
