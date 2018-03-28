package com.kothead.gdxjam.base.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {

    public static final ComponentMapper<PositionComponent> mapper = ComponentMapper.getFor(PositionComponent.class);

    public Vector2 position;

    public PositionComponent(float x, float y) {
        position = new Vector2(x, y);
    }

    public PositionComponent(Vector2 position) {
        this.position = new Vector2(position);
    }
}
