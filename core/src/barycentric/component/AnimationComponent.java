package barycentric.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class AnimationComponent extends Component
{
    static TextureAtlas atlas = null;

    public enum AnimationType
    {
        idle,
        IdleCrouch,
        run,
        Jump,
        Fall,
        attack1,
        attack2,
        attack3,
        Death,
    }

    public float stateTime = 0;

    ArrayMap<AnimationType, Animation> animations = new ArrayMap<>();
    AnimationType currentAnimation = AnimationType.idle;

    public AnimationComponent()
    {
        if(atlas == null)
        {
            atlas = new TextureAtlas(Gdx.files.internal("Textures/Adventurer.atlas"));
        }


        Array<TextureRegion> frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-idle-00"));
        frames.add(atlas.findRegion("adventurer-idle-01"));
        frames.add(atlas.findRegion("adventurer-idle-02"));
        frames.add(atlas.findRegion("adventurer-idle-03"));
        Animation<TextureRegion> anim = new Animation<>(1f/8f, frames, Animation.PlayMode.LOOP);
        animations.put(AnimationType.idle, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-crouch-00"));
        frames.add(atlas.findRegion("adventurer-crouch-01"));
        frames.add(atlas.findRegion("adventurer-crouch-02"));
        frames.add(atlas.findRegion("adventurer-crouch-03"));
        anim = new Animation<>(1f/8f, frames, Animation.PlayMode.LOOP);
        animations.put(AnimationType.IdleCrouch, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-run-00"));
        frames.add(atlas.findRegion("adventurer-run-01"));
        frames.add(atlas.findRegion("adventurer-run-02"));
        frames.add(atlas.findRegion("adventurer-run-03"));
        frames.add(atlas.findRegion("adventurer-run-04"));
        frames.add(atlas.findRegion("adventurer-run-05"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.LOOP);
        animations.put(AnimationType.run, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-attack1-00"));
        frames.add(atlas.findRegion("adventurer-attack1-01"));
        frames.add(atlas.findRegion("adventurer-attack1-02"));
        frames.add(atlas.findRegion("adventurer-attack1-03"));
        frames.add(atlas.findRegion("adventurer-attack1-04"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.NORMAL);
        animations.put(AnimationType.attack1, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-attack2-00"));
        frames.add(atlas.findRegion("adventurer-attack2-01"));
        frames.add(atlas.findRegion("adventurer-attack2-02"));
        frames.add(atlas.findRegion("adventurer-attack2-03"));
        frames.add(atlas.findRegion("adventurer-attack2-04"));
        frames.add(atlas.findRegion("adventurer-attack2-05"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.NORMAL);
        animations.put(AnimationType.attack2, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-attack3-00"));
        frames.add(atlas.findRegion("adventurer-attack3-01"));
        frames.add(atlas.findRegion("adventurer-attack3-02"));
        frames.add(atlas.findRegion("adventurer-attack3-03"));
        frames.add(atlas.findRegion("adventurer-attack3-04"));
        frames.add(atlas.findRegion("adventurer-attack3-05"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.NORMAL);
        animations.put(AnimationType.attack3, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-die-00"));
        frames.add(atlas.findRegion("adventurer-die-01"));
        frames.add(atlas.findRegion("adventurer-die-02"));
        frames.add(atlas.findRegion("adventurer-die-03"));
        frames.add(atlas.findRegion("adventurer-die-04"));
        frames.add(atlas.findRegion("adventurer-die-05"));
        frames.add(atlas.findRegion("adventurer-die-06"));
        anim = new Animation<>(1f/8f, frames, Animation.PlayMode.NORMAL);
        animations.put(AnimationType.Death, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-jump-00"));
        frames.add(atlas.findRegion("adventurer-jump-01"));
        frames.add(atlas.findRegion("adventurer-jump-02"));
        frames.add(atlas.findRegion("adventurer-jump-03"));
        anim = new Animation<>(1f/8f, frames, Animation.PlayMode.NORMAL);
        animations.put(AnimationType.Jump, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-fall-00"));
        frames.add(atlas.findRegion("adventurer-fall-01"));
        anim = new Animation<>(1f/8f, frames, Animation.PlayMode.LOOP);
        animations.put(AnimationType.Fall, anim);
    }

    public void setAnimationType(AnimationType type)
    {
        this.currentAnimation = type;
        stateTime = 0;
    }

    public AnimationType getAnimationType()
    {
        return currentAnimation;
    }

    public TextureRegion getCurrentFrame()
    {
        return (TextureRegion)animations.get(currentAnimation).getKeyFrame(stateTime);
    }

    public boolean isAnimationComplete()
    {
        return animations.get(currentAnimation).isAnimationFinished(stateTime);
    }
}
