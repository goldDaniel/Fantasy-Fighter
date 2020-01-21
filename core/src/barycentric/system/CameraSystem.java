package barycentric.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import barycentric.component.TransformComponent;
import barycentric.main.Entity;

public class CameraSystem extends GameSystem
{


    private OrthographicCamera cam;
    private final Vector2 averagePos = new Vector2();
    private int count;

    /**
     * @param entities
     */
    public CameraSystem(Array<Entity> entities, OrthographicCamera cam)
    {
        super(entities, TransformComponent.class);
        this.cam = cam;
    }

    @Override
    protected void preUpdate()
    {
        averagePos.setZero();
        count = 0;
    }

    @Override
    protected void process(Entity e, float dt)
    {
        TransformComponent t = (TransformComponent)e.getComponent(TransformComponent.class);
        averagePos.add(t.position);

        count++;
    }

    @Override
    protected void postUpdate()
    {
        if(count > 0) averagePos.scl(1f/(float)count);

        cam.position.set(averagePos, 0);
        cam.update();
    }
}
