package barycentric.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;

import barycentric.component.KeyboardInputComponent;
import barycentric.component.PlayerStateComponent;
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
        super(entities, PlayerStateComponent.class, AnimationComponent.class, RenderableComponent.class);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        PlayerStateComponent state  = (PlayerStateComponent)e.getComponent(PlayerStateComponent.class);
        AnimationComponent c        = (AnimationComponent)e.getComponent(AnimationComponent.class);
        RenderableComponent r       = (RenderableComponent)e.getComponent(RenderableComponent.class);

        c.setAnimatonState(state.currentState);
        c.incrementStateTime(dt);

        r.setFacingRight(state.facingRight);
        r.setTextureRegion(c.getCurrentFrame());
    }
}
