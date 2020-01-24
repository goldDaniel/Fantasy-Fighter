package barycentric.component;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class HurtboxComponent extends Component
{
    ArrayMap<AnimationComponent.State, Animation<Rectangle>> hurtBoxes = new ArrayMap<>();

    public Animation<Rectangle> getHurtboxes(AnimationComponent.State state)
    {
        return hurtBoxes.get(state);
    }

    public HurtboxComponent()
    {
        //null means no hurtbox on that frame

        Array<Rectangle> frames = new Array();
        frames.add(null);
        frames.add(null);
        frames.add(null);
        frames.add(null);
        frames.add(new Rectangle(16, -8, 12,24));
        frames.add(new Rectangle(16, -8, 12,24));
        frames.add(new Rectangle(16, -8, 12,24));
        Animation<Rectangle> anim = new Animation<>(1f/16f, frames, Animation.PlayMode.NORMAL);

        hurtBoxes.put(AnimationComponent.State.AttackGround1, anim);

        frames = new Array<>();
        frames.add(null);
        frames.add(null);
        frames.add(null);
        frames.add(null);
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(null);
        frames.add(null);
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        anim = new Animation<>(1f/24f, frames, Animation.PlayMode.NORMAL);
        hurtBoxes.put(AnimationComponent.State.AttackGround2, anim);


        frames = new Array<>();
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        frames.add(new Rectangle(16, -8, 12,12));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.NORMAL);
        hurtBoxes.put(AnimationComponent.State.AttackAir1, anim);



        frames = new Array<>();
        frames.add(new Rectangle(4, -16, 8,16));
        frames.add(new Rectangle(4, -16, 8,16));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.LOOP);
        hurtBoxes.put(AnimationComponent.State.AttackAir2, anim);
    }
}
