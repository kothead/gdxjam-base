package com.kothead.gdxjam.base.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.kothead.gdxjam.base.component.DebugComponent;
import com.kothead.gdxjam.base.component.PositionComponent;

public class DebugRenderSystem extends EntitySystem {

    private Camera camera;
    private ShapeRenderer renderer;
    private ImmutableArray<Entity> entities;

    public DebugRenderSystem(int priority, Camera camera, ShapeRenderer renderer) {
        super(priority);
        this.camera = camera;
        this.renderer = renderer;
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
        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (Entity entity: entities) {
            Polygon polygon = DebugComponent.mapper.get(entity).polygon;
            Vector2 position = PositionComponent.mapper.get(entity).position;
            polygon = new Polygon(polygon.getVertices());
            polygon.translate(position.x - camera.position.x,
                    position.y - camera.position.y);
            renderer.polygon(polygon.getTransformedVertices());
        }
        renderer.end();
    }
}
