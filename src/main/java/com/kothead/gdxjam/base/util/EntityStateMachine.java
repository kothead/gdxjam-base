package com.kothead.gdxjam.base.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;

public class EntityStateMachine extends DefaultStateMachine<Entity, State<Entity>> {

    public EntityStateMachine(Entity owner, State<Entity> initialState) {
        super(owner, initialState);
    }

    public EntityStateMachine(Entity owner, State<Entity> initialState, State<Entity> globalState) {
        super(owner, initialState, globalState);
    }

    @Override
    public void setInitialState(State<Entity> state) {
        super.setInitialState(state);
        if (currentState != null) state.enter(owner);
    }
}
