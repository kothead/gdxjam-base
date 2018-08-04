package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.DebugComponent;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.screen.BaseScreen;

public class DebugRenderSystem extends EntitySystem {

    private BaseScreen screen;
    private ImmutableArray<Entity> entities;

    public DebugRenderSystem(int priority, BaseScreen screen) {
        super(priority);
        this.screen = screen;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(
                DebugComponent.class,
                PositionComponent.class)
                .get());
    }

    @Override
    public void update(float deltaTime) {
        ShapeRenderer shapes = screen.shapes();
        Camera camera = screen.getCamera();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        for (Entity entity: entities) {
            Polygon polygon = DebugComponent.mapper.get(entity).polygon;
            Vector3 position = PositionComponent.mapper.get(entity).position;
            polygon = new Polygon(polygon.getVertices());
            polygon.translate(position.x - camera.position.x + screen.getWorldWidth() / 2.0f,
                    position.y - camera.position.y + screen.getWorldHeight() / 2.0f);
            shapes.polygon(polygon.getTransformedVertices());
        }
        shapes.end();
    }
}
