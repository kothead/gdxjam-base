package com.kothead.gdxjam.base.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Polygon;

public class DebugComponent implements Component {

    public static final ComponentMapper<DebugComponent> mapper = ComponentMapper.getFor(DebugComponent.class);

    public Polygon polygon;

    public DebugComponent() {
        polygon = new Polygon();
    }

    public DebugComponent(Polygon polygon) {
        this.polygon = polygon;
    }
}
