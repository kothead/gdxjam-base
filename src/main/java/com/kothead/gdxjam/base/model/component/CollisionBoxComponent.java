package com.kothead.gdxjam.base.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Polygon;
import com.kothead.gdxjam.base.data.CollisionBoxes;

public class CollisionBoxComponent implements Component {

    public static final ComponentMapper<CollisionBoxComponent> mapper = ComponentMapper.getFor(CollisionBoxComponent.class);

    public Polygon collisionBox;

    public CollisionBoxComponent(Polygon collisionBox) {
        this.collisionBox = collisionBox;
    }
}
