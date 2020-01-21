package barycentric.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;

import barycentric.component.Component;
import barycentric.component.TransformComponent;
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

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
        {
            c.setAnimationType(AnimationComponent.AnimationType.idle);
            c.setStateTime(0);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
        {
            c.setAnimationType(AnimationComponent.AnimationType.idleCrouch);
            c.setStateTime(0);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))
        {
            c.setAnimationType(AnimationComponent.AnimationType.run);
            c.setStateTime(0);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))
        {
            c.setAnimationType(AnimationComponent.AnimationType.attack1);
            c.setStateTime(0);
        }


        c.incrementStateTime(dt);
        r.setTextureRegion(c.getCurrentFrame());
    }
}
