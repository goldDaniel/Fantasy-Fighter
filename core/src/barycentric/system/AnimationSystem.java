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
            float vel = m.velocityX > 0 ? m.velocityX : -m.velocityX;
            if(vel > 0)
            {
                c.setAnimationState(AnimationComponent.State.Run);
            }
            else
            {
                c.setAnimationState(AnimationComponent.State.Idle);
            }
        }
        if(state.currentState == CharacterStateComponent.State.InAir)
        {
            if(m.velocityY > 0)
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
                c.setAnimationState(AnimationComponent.State.Falling);
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
