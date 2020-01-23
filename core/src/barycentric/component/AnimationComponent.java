package barycentric.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class AnimationComponent extends Component
{
    public enum State
    {
        Idle,
        Duck,
        Run,
        Attack1,
        Jumping,
        Falling,
    }

    static TextureAtlas atlas = null;

    float stateTime = 0;

    ArrayMap<AnimationComponent.State, Animation> animations = new ArrayMap<>();
    AnimationComponent.State currentAnimation = AnimationComponent.State.Idle;

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
        animations.put(State.Idle, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-crouch-00"));
        frames.add(atlas.findRegion("adventurer-crouch-01"));
        frames.add(atlas.findRegion("adventurer-crouch-02"));
        frames.add(atlas.findRegion("adventurer-crouch-03"));
        anim = new Animation<>(1f/8f, frames, Animation.PlayMode.LOOP);
        animations.put(State.Duck, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-run-00"));
        frames.add(atlas.findRegion("adventurer-run-01"));
        frames.add(atlas.findRegion("adventurer-run-02"));
        frames.add(atlas.findRegion("adventurer-run-03"));
        frames.add(atlas.findRegion("adventurer-run-04"));
        frames.add(atlas.findRegion("adventurer-run-05"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.LOOP);
        animations.put(State.Run, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-attack1-00"));
        frames.add(atlas.findRegion("adventurer-attack1-01"));
        frames.add(atlas.findRegion("adventurer-attack1-02"));
        frames.add(atlas.findRegion("adventurer-attack1-03"));
        frames.add(atlas.findRegion("adventurer-attack1-04"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.NORMAL);
        animations.put(State.Attack1, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-jump-00"));
        frames.add(atlas.findRegion("adventurer-jump-01"));
        frames.add(atlas.findRegion("adventurer-jump-02"));
        frames.add(atlas.findRegion("adventurer-jump-03"));
        anim = new Animation<>(1f/16f, frames, Animation.PlayMode.NORMAL);
        animations.put(State.Jumping, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-fall-00"));
        frames.add(atlas.findRegion("adventurer-fall-01"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.LOOP);
        animations.put(State.Falling, anim);
    }

    public void incrementStateTime(float dt)
    {
        stateTime += dt;
    }

    public void setAnimationState(AnimationComponent.State type)
    {
        if(this.currentAnimation != type)
        {
            this.currentAnimation = type;
            stateTime = 0;
        }
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
