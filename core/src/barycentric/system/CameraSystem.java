package barycentric.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import barycentric.component.TransformComponent;
import barycentric.main.Entity;

public class CameraSystem extends GameSystem
{


    private OrthographicCamera cam;
    private final Vector2 averagePos = new Vector2();


    private Array<Vector2> points = new Array<>();

    public CameraSystem(Array<Entity> entities, OrthographicCamera cam)
    {
        super(entities, TransformComponent.class);
        this.cam = cam;
    }

    @Override
    protected void preUpdate()
    {
        points.clear();
        averagePos.setZero();
    }

    @Override
    protected void process(Entity e, float dt)
    {
        TransformComponent t = (TransformComponent)e.getComponent(TransformComponent.class);
        averagePos.add(t.position);
        points.add(t.position);
    }

    @Override
    protected void postUpdate()
    {
        if(points.size > 0) averagePos.scl(1f/(float)points.size);

        cam.position.set(averagePos, 0);

        Vector2 v1 = new Vector2();
        Vector2 v2 = new Vector2();
        float largestDistance = 0;

        for(Vector2 p : points)
        {
            for(int i = 0; i < points.size; i++)
            {
                float distance = p.dst(points.get(i));

                if(distance > largestDistance)
                {
                    largestDistance = distance;
                    v1.set(p);
                    v2.set(points.get(i));
                }
            }
        }

        largestDistance /= 256f;

        cam.zoom = MathUtils.clamp(largestDistance, 1f, 1.5f);

        cam.update();



    }
}
