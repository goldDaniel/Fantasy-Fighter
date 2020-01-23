package barycentric.system;

import com.badlogic.gdx.utils.Array;

import barycentric.component.MovementComponent;
import barycentric.component.PlayerStateComponent;
import barycentric.main.Entity;

public class PlayerMovementSystem extends GameSystem
{
    public PlayerMovementSystem(Array<Entity> e)
    {
        super(e, PlayerStateComponent.class, MovementComponent.class);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        PlayerStateComponent state = (PlayerStateComponent)e.getComponent(PlayerStateComponent.class);
        MovementComponent movement = (MovementComponent)e.getComponent(MovementComponent.class);

        if(state.currentState == PlayerStateComponent.State.Idle)
        {
            movement.velocityX = 0;
            movement.velocityY = 0;
        }
        if(state.currentState == PlayerStateComponent.State.Duck)
        {
            movement.velocityX = 0;
            movement.velocityY = 0;
        }
        if(state.currentState == PlayerStateComponent.State.Run)
        {
            if(state.facingRight) movement.velocityX = 72;
            else                  movement.velocityX = -72;
        }
    }
}
