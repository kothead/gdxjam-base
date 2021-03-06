package com.kothead.gdxjam.base.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Polygon;

public class CollisionBoxComponent implements Component {

    public static final ComponentMapper<CollisionBoxComponent> mapper = ComponentMapper.getFor(CollisionBoxComponent.class);

    public Polygon collisionBox;

    public CollisionBoxComponent(Polygon collisionBox) {
        this.collisionBox = collisionBox;
    }
}
