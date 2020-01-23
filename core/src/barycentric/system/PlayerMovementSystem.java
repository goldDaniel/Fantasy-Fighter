package barycentric.system;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

import barycentric.component.KeyboardInputComponent;
import barycentric.component.MovementComponent;
import barycentric.component.CharacterStateComponent;
import barycentric.component.TransformComponent;
import barycentric.main.Entity;
import barycentric.main.MapCollisionSystem;

public class PlayerMovementSystem extends GameSystem
{
    static final float PLAYER_SPEED = 192f;

    MapCollisionSystem s;

    public PlayerMovementSystem(Array<Entity> e, TiledMap map)
    {
        super(e, KeyboardInputComponent.class, CharacterStateComponent.class, MovementComponent.class);

        this.s = new MapCollisionSystem(map);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        TransformComponent transform = (TransformComponent)e.getComponent(TransformComponent.class);
        KeyboardInputComponent in  = (KeyboardInputComponent)e.getComponent(KeyboardInputComponent.class);
        CharacterStateComponent state = (CharacterStateComponent)e.getComponent(CharacterStateComponent.class);
        MovementComponent movement = (MovementComponent)e.getComponent(MovementComponent.class);

        if(state.currentState == CharacterStateComponent.State.InAir)
        {
            movement.velocityY -= 1024f * dt;
        }

        if(state.currentState == CharacterStateComponent.State.OnGround)
        {
            movement.velocityY = -1;

            if(in.isKeyDown(in.JUMP))
            {
                in.setKey(in.JUMP, false);
                state.currentState = CharacterStateComponent.State.InAir;
                movement.velocityY = 384f;
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
