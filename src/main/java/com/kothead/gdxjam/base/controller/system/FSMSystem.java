package com.kothead.gdxjam.base.controller.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.kothead.gdxjam.base.model.component.FSMComponent;

public class FSMSystem extends IteratingSystem {

    public FSMSystem(int priority) {
        super(Family.all(FSMComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StateMachine fsm = FSMComponent.mapper.get(entity).fsm;
        fsm.update();
    }
}
