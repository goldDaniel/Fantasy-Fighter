package barycentric.system;

import com.badlogic.gdx.utils.Array;

import barycentric.component.KeyboardInputComponent;
import barycentric.component.MovementComponent;
import barycentric.component.PlayerStateComponent;
import barycentric.component.TransformComponent;
import barycentric.main.Entity;
import barycentric.main.MapCollisionSystem;

public class PlayerMovementSystem extends GameSystem
{
    static final float PLAYER_SPEED = 128f;

    MapCollisionSystem s;

    public PlayerMovementSystem(Array<Entity> e, MapCollisionSystem s)
    {
        super(e, KeyboardInputComponent.class, PlayerStateComponent.class, MovementComponent.class);

        this.s = s;
    }

    @Override
    protected void process(Entity e, float dt)
    {
        TransformComponent transform = (TransformComponent)e.getComponent(TransformComponent.class);
        KeyboardInputComponent in  = (KeyboardInputComponent)e.getComponent(KeyboardInputComponent.class);
        PlayerStateComponent state = (PlayerStateComponent)e.getComponent(PlayerStateComponent.class);
        MovementComponent movement = (MovementComponent)e.getComponent(MovementComponent.class);

        if(state.currentState == PlayerStateComponent.State.InAir)
        {
            movement.velocityY -= 256f * dt;
        }

        if(state.currentState == PlayerStateComponent.State.OnGround)
        {
            movement.velocityY = -1;
            if(in.isKeyDown(in.JUMP))
            {
                state.currentState = PlayerStateComponent.State.InAir;
                movement.velocityY = 256;
            }
        }

        movement.velocityX = 0;
        if(in.isKeyDown(in.LEFT))
        {
            state.facingRight = false;
            movement.velocityX += -PLAYER_SPEED;
        }
        if(in.isKeyDown(in.RIGHT))
        {
            state.facingRight = true;
            movement.velocityX += PLAYER_SPEED;
        }


        transform.position.x += movement.velocityX * dt;
        s.processHorizontal(e, transform, state);
        transform.position.y += movement.velocityY * dt;
        s.processVertical(e, transform, state);
    }
}
