package com.kothead.gdxjam.base.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector3;

public class PositionComponent implements Component {

    public static final ComponentMapper<PositionComponent> mapper = ComponentMapper.getFor(PositionComponent.class);

    public Vector3 position;

    public PositionComponent(float x, float y, float z) {
        position = new Vector3(x, y, z);
    }

    public PositionComponent(Vector3 position) {
        this.position = new Vector3(position);
    }
}
