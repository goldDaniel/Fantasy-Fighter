package barycentric.system;

import com.badlogic.gdx.utils.Array;

import barycentric.component.MovementComponent;
import barycentric.component.TransformComponent;
import barycentric.main.Entity;

public class MovementSystem extends GameSystem
{
    public MovementSystem(Array<Entity> e)
    {
        super(e, MovementComponent.class);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        TransformComponent transform = (TransformComponent)e.getComponent(TransformComponent.class);
        MovementComponent  movement  = (MovementComponent)e.getComponent(MovementComponent.class);


        transform.position.x += movement.velocityX * dt;
        transform.position.y += movement.velocityY * dt;
    }
}