package barycentric.system;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

import barycentric.component.KeyboardInputComponent;
import barycentric.component.MapCollisionComponent;
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
        super(e, KeyboardInputComponent.class, CharacterStateComponent.class, MovementComponent.class, MapCollisionComponent.class);

        this.s = new MapCollisionSystem(map);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        final float GRAVITY       = 1024f;
        final float JUMP_VELOCITY = 384f;

        TransformComponent transform = (TransformComponent)e.getComponent(TransformComponent.class);
        KeyboardInputComponent in  = (KeyboardInputComponent)e.getComponent(KeyboardInputComponent.class);
        CharacterStateComponent state = (CharacterStateComponent)e.getComponent(CharacterStateComponent.class);
        MovementComponent movement = (MovementComponent)e.getComponent(MovementComponent.class);
        MapCollisionComponent col = (MapCollisionComponent)e.getComponent(MapCollisionComponent.class);

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
            movement.velocityY = -1;
            state.hasJumped = false;
            state.hasDoubleJumped = false;

            if(state.cooldownTimer <= 0)
            {
                if (in.isKeyDown(in.ATTACK) && (!in.isKeyDown(in.LEFT) && !in.isKeyDown(in.RIGHT)))
                {
                    state.attackState = CharacterStateComponent.AttackState.Neutral;
                    state.cooldownTimer = state.COOLDOWN_TIME;
                    in.setKey(in.ATTACK, false);
                }
                else if (in.isKeyDown(in.ATTACK) && in.isKeyDown(in.LEFT))
                {
                    state.attackState = CharacterStateComponent.AttackState.Forward;
                    state.facingRight = false;
                    state.cooldownTimer = state.COOLDOWN_TIME;
                    in.setKey(in.ATTACK, false);
                }
                else if (in.isKeyDown(in.ATTACK) && in.isKeyDown(in.RIGHT))
                {
                    state.attackState = CharacterStateComponent.AttackState.Forward;
                    state.facingRight = true;
                    state.cooldownTimer = state.COOLDOWN_TIME;
                    in.setKey(in.ATTACK, false);
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
        if(state.currentState == CharacterStateComponent.State.InAir)
        {

            if(state.attackState == CharacterStateComponent.AttackState.Forward)
            {
                movement.velocityY = 0;
            }

            if(state.attackState == CharacterStateComponent.AttackState.Neutral)
            {
                movement.velocityY -= GRAVITY * 2f * dt;
            }
            else
            {
                movement.velocityY -= GRAVITY * dt;
            }

            if(state.cooldownTimer <= 0)
            {
                if (in.isKeyDown(in.ATTACK) && (!in.isKeyDown(in.LEFT) && !in.isKeyDown(in.RIGHT)))
                {
                    state.attackState = CharacterStateComponent.AttackState.Neutral;
                    in.setKey(in.ATTACK, false);
                    state.cooldownTimer = state.COOLDOWN_TIME;
                }
                else if (in.isKeyDown(in.ATTACK) && in.isKeyDown(in.LEFT))
                {

                    state.attackState = CharacterStateComponent.AttackState.Forward;
                    state.facingRight = false;
                    in.setKey(in.ATTACK, false);
                    state.cooldownTimer = state.COOLDOWN_TIME;
                }
                else if (in.isKeyDown(in.ATTACK) && in.isKeyDown(in.RIGHT))
                {
                    state.attackState = CharacterStateComponent.AttackState.Forward;
                    state.facingRight = true;
                    in.setKey(in.ATTACK, false);
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






        int iterations = 16;
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

        if(state.attackState == CharacterStateComponent.AttackState.None) state.cooldownTimer -= dt;
    }

    @Override
    public void dispose()
    {

    }
}
