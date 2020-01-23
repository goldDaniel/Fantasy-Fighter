package barycentric.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import barycentric.component.MapCollisionComponent;
import barycentric.component.TransformComponent;
import barycentric.main.Entity;

public class DebugRenderingSystem extends GameSystem
{

    ShapeRenderer sh;
    OrthographicCamera cam;

    public DebugRenderingSystem(Array<Entity> e, OrthographicCamera cam)
    {
        super(e, MapCollisionComponent.class);

        sh = new ShapeRenderer();
        this.cam = cam;
    }


    @Override
    protected void preUpdate()
    {
        sh.begin(ShapeRenderer.ShapeType.Line);
        sh.setProjectionMatrix(cam.combined);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        TransformComponent transform = (TransformComponent)e.getComponent(TransformComponent.class);
        MapCollisionComponent col   = (MapCollisionComponent)e.getComponent(MapCollisionComponent.class);

        sh.rect(transform.position.x + col.X,
                transform.position.y + col.Y,
                col.WIDTH,
                col.HEIGHT);
    }

    @Override
    protected void postUpdate()
    {
        sh.end();
    }
}
