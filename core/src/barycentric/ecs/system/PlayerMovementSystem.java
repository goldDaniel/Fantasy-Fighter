package barycentric.ecs.system;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

import barycentric.ecs.component.InputComponent;
import barycentric.ecs.component.MapCollisionComponent;
import barycentric.ecs.component.MovementComponent;
import barycentric.ecs.component.CharacterStateComponent;
import barycentric.ecs.component.TransformComponent;
import barycentric.main.Entity;
import barycentric.main.MapCollisionSystem;

/*
    When an entity begins processing, we set the global component references
    so we can use them in other functions. Instead of passing them around as parameters
 */


public class PlayerMovementSystem extends GameSystem
{
    private static final float PLAYER_SPEED = 192f;

    private static final float GRAVITY       = 1024f;
    private static final float JUMP_VELOCITY = 384f;

    private MapCollisionSystem s;

    private InputComponent in;
    private TransformComponent transform;
    private CharacterStateComponent state;
    private MovementComponent movement;
    private MapCollisionComponent col;

    public PlayerMovementSystem(Array<Entity> e, TiledMap map)
    {
        super(e, InputComponent.class, CharacterStateComponent.class, MovementComponent.class, MapCollisionComponent.class);

        this.s = new MapCollisionSystem(map);
    }

    private void processInAirState(float dt)
    {
        if(state.attackState == CharacterStateComponent.AttackState.Weak)
        {
            movement.velocityY = 0;
        }

        if(state.attackState == CharacterStateComponent.AttackState.Strong)
        {
            movement.velocityY -= GRAVITY * 2f * dt;
        }
        else
        {
            movement.velocityY -= GRAVITY * dt;
        }

        if(state.cooldownTimer <= 0)
        {
            if (in.isKeyDown(in.ATTACK_STRONG))
            {
                state.attackState = CharacterStateComponent.AttackState.Strong;

                if(movement.velocityY < 0) movement.velocityY = 0;
                movement.velocityY += 256;

                in.setKey(in.ATTACK_STRONG, false);
                state.cooldownTimer = state.COOLDOWN_TIME;
            }
            else if (in.isKeyDown(in.ATTACK_WEAK))
            {

                state.attackState = CharacterStateComponent.AttackState.Weak;
                in.setKey(in.ATTACK_WEAK, false);
                state.cooldownTimer = state.COOLDOWN_TIME;
            }
        }

        if(in.isKeyDown(in.JUMP) && state.attackState == CharacterStateComponent.AttackState.None)
        {
            if(!state.hasJumped)
            {
                state.hasJumped = true;
                in.setKey(in.JUMP, false);
                movement.velocityY = JUMP_VELOCITY;
            }
            else if(!state.hasDoubleJumped)
            {
                state.hasDoubleJumped = true;
                in.setKey(in.JUMP, false);
                movement.velocityY = JUMP_VELOCITY;
            }
        }
    }

    private void processOnGroundState(float dt)
    {
        movement.velocityY = -1;
        state.hasJumped = false;
        state.hasDoubleJumped = false;

        if(state.cooldownTimer <= 0)
        {
            if (in.isKeyDown(in.ATTACK_STRONG))
            {
                state.attackState = CharacterStateComponent.AttackState.Strong;
                state.cooldownTimer = state.COOLDOWN_TIME;
                in.setKey(in.ATTACK_STRONG, false);
            }
            else if (in.isKeyDown(in.ATTACK_WEAK))
            {
                state.attackState = CharacterStateComponent.AttackState.Weak;
                state.cooldownTimer = state.COOLDOWN_TIME;
                in.setKey(in.ATTACK_WEAK, false);
            }
        }

        if(in.isKeyDown(in.JUMP) && state.attackState == CharacterStateComponent.AttackState.None)
        {

            state.hasJumped = true;
            in.setKey(in.JUMP, false);
            state.currentState = CharacterStateComponent.State.InAir;
            movement.velocityY = JUMP_VELOCITY;
        }
    }

    @Override
    protected void process(Entity e, float dt)
    {
        in  = (InputComponent)e.getComponent(InputComponent.class);
        transform = (TransformComponent)e.getComponent(TransformComponent.class);
        state = (CharacterStateComponent)e.getComponent(CharacterStateComponent.class);
        movement = (MovementComponent)e.getComponent(MovementComponent.class);
        col = (MapCollisionComponent)e.getComponent(MapCollisionComponent.class);



        //If we are not attacking, we have full control over horizontal movement
        movement.velocityX = 0;
        if(state.attackState == CharacterStateComponent.AttackState.None)
        {
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
        }

        if(state.currentState == CharacterStateComponent.State.OnGround)
        {
            processOnGroundState(dt);
        }
        if(state.currentState == CharacterStateComponent.State.InAir)
        {
            processInAirState(dt);
        }


        //in case we are moving too fast, this way we will still collide with tiles
        int iterations = 4;
        for(int i = 0; i < iterations; i++)
        {
            transform.position.x += movement.velocityX * dt * (1f / iterations);
            s.processHorizontal(movement, col, transform, state);
        }

        for(int i = 0; i < iterations; i++)
        {
            transform.position.y += movement.velocityY * dt * (1f / iterations);;
            s.processVertical(movement, col, transform, state);
        }

        if(state.attackState == CharacterStateComponent.AttackState.None)
        {
            state.cooldownTimer -= dt;
        }
    }

    @Override
    public void dispose()
    {

    }
}
