package com.kothead.gdxjam.base.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class FollowCameraComponent implements Component {

    public static final ComponentMapper<FollowCameraComponent> mapper = ComponentMapper.getFor(FollowCameraComponent.class);
}
