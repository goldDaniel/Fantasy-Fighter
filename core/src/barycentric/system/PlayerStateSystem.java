package barycentric.system;

import com.badlogic.gdx.utils.Array;

import barycentric.component.AnimationComponent;
import barycentric.component.KeyboardInputComponent;
import barycentric.component.PlayerStateComponent;
import barycentric.main.Entity;

public class PlayerStateSystem extends GameSystem
{
    public PlayerStateSystem(Array<Entity> e)
    {
        super(e, PlayerStateComponent.class, KeyboardInputComponent.class, AnimationComponent.class);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        PlayerStateComponent state   = (PlayerStateComponent)e.getComponent(PlayerStateComponent.class);
        KeyboardInputComponent in    = (KeyboardInputComponent)e.getComponent(KeyboardInputComponent.class);


        if(state.currentState == PlayerStateComponent.State.Idle)
        {
            if(in.isKeyDown(in.LEFT))
            {
                state.currentState = PlayerStateComponent.State.Run;
            }
            if(in.isKeyDown(in.RIGHT))
            {
                state.currentState = PlayerStateComponent.State.Run;
            }

            if(in.isKeyDown(in.DUCK))
            {

                state.currentState = PlayerStateComponent.State.Duck;
            }
        }

        if(state.currentState == PlayerStateComponent.State.Run)
        {
            if(in.isKeyDown(in.LEFT))  state.facingRight = false;
            if(in.isKeyDown(in.RIGHT)) state.facingRight = true;


            if(!in.isKeyDown(in.LEFT) && !in.isKeyDown(in.RIGHT))
            {
                state.currentState = PlayerStateComponent.State.Idle;
            }
            if(in.isKeyDown(in.DUCK))
            {
                state.currentState = PlayerStateComponent.State.Duck;
            }
        }

        if(state.currentState == PlayerStateComponent.State.Duck)
        {
            if(!in.isKeyDown(in.DUCK))
            {
                state.currentState = PlayerStateComponent.State.Idle;
            }
        }
    }
}
