package com.kothead.gdxjam.base.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.kothead.gdxjam.base.util.EntityStateMachine;

public class FSMComponent implements Component {

    public static final ComponentMapper<FSMComponent> mapper = ComponentMapper.getFor(FSMComponent.class);

    public EntityStateMachine fsm;

    public FSMComponent(EntityStateMachine fsm) {
        this.fsm = fsm;
    }
}
