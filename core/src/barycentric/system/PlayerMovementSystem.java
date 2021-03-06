package barycentric.system;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

import barycentric.component.InputComponent;
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
        super(e, InputComponent.class, CharacterStateComponent.class, MovementComponent.class, MapCollisionComponent.class);

        this.s = new MapCollisionSystem(map);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        final float GRAVITY       = 1024f;
        final float JUMP_VELOCITY = 384f;

        TransformComponent transform = (TransformComponent)e.getComponent(TransformComponent.class);
        InputComponent in  = (InputComponent)e.getComponent(InputComponent.class);
        CharacterStateComponent state = (CharacterStateComponent)e.getComponent(CharacterStateComponent.class);
        MovementComponent movement = (MovementComponent)e.getComponent(MovementComponent.class);
        MapCollisionComponent col = (MapCollisionComponent)e.getComponent(MapCollisionComponent.class);

        movement.velocityX = 0;


        //Incoming giant state machine/////////////////////////////////////////////

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
        if(state.currentState == CharacterStateComponent.State.InAir)
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

        if(state.attackState == CharacterStateComponent.AttackState.None) state.cooldownTimer -= dt;
    }

    @Override
    public void dispose()
    {

    }
}
