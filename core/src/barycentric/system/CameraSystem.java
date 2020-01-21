package barycentric.system;

import com.badlogic.gdx.utils.Array;

import barycentric.component.TransformComponent;
import barycentric.main.Entity;

public class CameraSystem extends GameSystem
{
    /**
     * @param entities
     */
    public CameraSystem(Array<Entity> entities)
    {
        super(entities, TransformComponent.class);
    }

    @Override
    protected void process(Entity e, float dt)
    {

    }
}
