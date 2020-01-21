package barycentric.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;

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
        super(entities, AnimationComponent.class, RenderableComponent.class);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        AnimationComponent c = (AnimationComponent)e.getComponent(AnimationComponent.class);
        RenderableComponent r = (RenderableComponent)e.getComponent(RenderableComponent.class);

        c.stateTime += dt;

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
        {
            c.setAnimationType(AnimationComponent.AnimationType.idle);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
        {
            c.setAnimationType(AnimationComponent.AnimationType.IdleCrouch);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))
        {
            c.setAnimationType(AnimationComponent.AnimationType.run);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))
        {
            c.setAnimationType(AnimationComponent.AnimationType.attack1);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5))
        {
            c.setAnimationType(AnimationComponent.AnimationType.Death);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6))
        {
            c.setAnimationType(AnimationComponent.AnimationType.Jump);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_7))
        {
            c.setAnimationType(AnimationComponent.AnimationType.Fall);
        }

        if(c.isAnimationComplete())
        {
            if(c.getAnimationType() == AnimationComponent.AnimationType.attack1)
            {
                c.setAnimationType(AnimationComponent.AnimationType.attack2);
            }
            else if(c.getAnimationType() == AnimationComponent.AnimationType.attack2)
            {
                c.setAnimationType(AnimationComponent.AnimationType.attack3);
            }
        }

        r.setTextureRegion(c.getCurrentFrame());
    }
}
