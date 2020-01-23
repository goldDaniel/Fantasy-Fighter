package barycentric.system;

import com.badlogic.gdx.utils.Array;

import barycentric.component.MovementComponent;
import barycentric.component.CharacterStateComponent;
import barycentric.main.Entity;
import barycentric.component.AnimationComponent;
import barycentric.component.RenderableComponent;

public class AnimationSystem extends GameSystem
{
    /**
     * @param entities
     */
    public AnimationSystem(Array<Entity> entities)
    {
        super(entities, CharacterStateComponent.class, AnimationComponent.class, RenderableComponent.class);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        CharacterStateComponent state  = (CharacterStateComponent)e.getComponent(CharacterStateComponent.class);
        MovementComponent m         = (MovementComponent)e.getComponent(MovementComponent.class);
        AnimationComponent c        = (AnimationComponent)e.getComponent(AnimationComponent.class);
        RenderableComponent r       = (RenderableComponent)e.getComponent(RenderableComponent.class);


        if(state.currentState == CharacterStateComponent.State.OnGround)
        {
            if(state.attackState == CharacterStateComponent.AttackState.Neutral)
            {
                c.setAnimationState(AnimationComponent.State.AttackGround1);

                if(c.isAnimationComplete())
                {
                    c.setAnimationState(AnimationComponent.State.Idle);
                    state.attackState = CharacterStateComponent.AttackState.None;
                }
            }
            else if(state.attackState == CharacterStateComponent.AttackState.Forward)
            {
                c.setAnimationState(AnimationComponent.State.AttackGround2);

                if(c.isAnimationComplete())
                {
                    c.setAnimationState(AnimationComponent.State.Idle);
                    state.attackState = CharacterStateComponent.AttackState.None;
                }
            }
            else
            {
                float vel = m.velocityX > 0 ? m.velocityX : -m.velocityX;
                if (vel > 0)
                {
                    c.setAnimationState(AnimationComponent.State.Run);
                }
                else
                {
                    c.setAnimationState(AnimationComponent.State.Idle);
                }
            }
        }
        if(state.currentState == CharacterStateComponent.State.InAir)
        {
            if(state.attackState == CharacterStateComponent.AttackState.Neutral)
            {
                c.setAnimationState(AnimationComponent.State.AttackAir2);
            }
            else if(state.attackState == CharacterStateComponent.AttackState.Forward)
            {
                c.setAnimationState(AnimationComponent.State.AttackAir1);

                if(c.isAnimationComplete())
                {
                    c.setAnimationState(AnimationComponent.State.Falling);
                    state.attackState = CharacterStateComponent.AttackState.None;
                }
            }
            else if(m.velocityY > 0)
            {
                if(state.hasDoubleJumped)
                {
                    c.setAnimationState(AnimationComponent.State.JumpSecond);
                }
                else
                {
                    c.setAnimationState(AnimationComponent.State.JumpFirst);
                }

            }
            else
            {
                if(!state.hasDoubleJumped)
                {
                    c.setAnimationState(AnimationComponent.State.Falling);
                }
            }
        }


        c.incrementStateTime(dt);

        r.setFacingRight(state.facingRight);
        r.setTextureRegion(c.getCurrentFrame());
    }

    @Override
    public void dispose()
    {

    }
}
