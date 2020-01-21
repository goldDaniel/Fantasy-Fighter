package barycentric.system;

import com.badlogic.gdx.utils.Array;

import barycentric.main.Entity;
import barycentric.component.AnimationComponent;
import barycentric.component.RenderableComponent;

public class AnimationSystem extends GameSystem
{
    public AnimationSystem(Array<Entity> entities)
    {
        super(entities);
    }

    @Override
    public void process(Entity e, float dt)
    {
        AnimationComponent c = (AnimationComponent)e.getComponent(AnimationComponent.class);
        RenderableComponent r = (RenderableComponent)e.getComponent(RenderableComponent.class);

        c.incrementStateTime(dt);
        r.setTextureRegion(c.getCurrentFrame());
    }
}
