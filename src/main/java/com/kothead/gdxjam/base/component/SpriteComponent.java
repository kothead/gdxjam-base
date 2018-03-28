package com.kothead.gdxjam.base.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component {

    public static final ComponentMapper<SpriteComponent> mapper = ComponentMapper.getFor(SpriteComponent.class);

    public Sprite sprite;

    public SpriteComponent() {
        sprite = new Sprite();
    }

    public SpriteComponent(Sprite sprite) {
        this.sprite = sprite;
    }
}
