package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.kothead.gdxjam.base.component.StateMachineComponent;

public class StateMachineSystem extends IteratingSystem {

    public StateMachineSystem(int priority) {
        super(Family.all(StateMachineComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StateMachine stateMachine = StateMachineComponent.mapper.get(entity).stateMachine;
        stateMachine.update();
    }
}
