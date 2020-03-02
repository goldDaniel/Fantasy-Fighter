package barycentric.ecs.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import barycentric.ecs.component.AnimationComponent;
import barycentric.ecs.component.CharacterStateComponent;
import barycentric.ecs.component.HurtboxComponent;
import barycentric.ecs.component.TransformComponent;
import barycentric.main.Entity;

public class DebugRenderingSystem extends GameSystem
{

    ShapeRenderer sh;
    OrthographicCamera cam;

    public DebugRenderingSystem(Array<Entity> e, OrthographicCamera cam)
    {
        super(e, AnimationComponent.class, CharacterStateComponent.class, HurtboxComponent.class);

        sh = new ShapeRenderer();
        this.cam = cam;
    }


    @Override
    protected void preUpdate()
    {
        sh.begin(ShapeRenderer.ShapeType.Line);
        sh.setProjectionMatrix(cam.combined);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        CharacterStateComponent state = (CharacterStateComponent)e.getComponent(CharacterStateComponent.class);
        TransformComponent transform = (TransformComponent)e.getComponent(TransformComponent.class);
        AnimationComponent anim = (AnimationComponent)e.getComponent(AnimationComponent.class);
        HurtboxComponent box   = (HurtboxComponent)e.getComponent(HurtboxComponent.class);

        if(box.getHurtboxes(anim.getAnimationState()) != null)
        {
            Rectangle r = box.getHurtboxes(anim.getAnimationState()).getKeyFrame(anim.getStateTime());

            if(r != null)
            {
                if(state.facingRight)
                {
                    sh.rect(transform.position.x + r.x,
                            transform.position.y + r.y,
                            r.width,
                            r.height);
                }
                else
                {
                    sh.rect(transform.position.x - r.x,
                            transform.position.y + r.y,
                            -r.width,
                            r.height);
                }
            }
        }
    }

    @Override
    public void dispose()
    {
        sh.dispose();
    }

    @Override
    protected void postUpdate()
    {
        sh.end();
    }
}
