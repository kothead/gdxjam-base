package com.kothead.gdxjam.base.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.kothead.gdxjam.base.util.EntityStateMachine;

public class StateMachineComponent implements Component {

    public static final ComponentMapper<StateMachineComponent> mapper = ComponentMapper.getFor(StateMachineComponent.class);

    public EntityStateMachine stateMachine;

    public StateMachineComponent(EntityStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
}
