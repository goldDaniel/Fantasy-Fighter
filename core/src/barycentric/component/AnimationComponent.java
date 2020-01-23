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
        Run,
        JumpFirst,
        JumpSecond,
        Falling,

        AttackGround1,
        AttackGround2,
        AttackGround3,

        AttackAir1,
        AttackAir2,
        AttackAir3,
    }
    static TextureAtlas atlas = null;
    float stateTime = 0;

    ArrayMap<State, Animation> animations = new ArrayMap<>();
    State currentAnimation = State.Idle;

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
        animations.put(State.AttackGround1, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-attack2-00"));
        frames.add(atlas.findRegion("adventurer-attack2-01"));
        frames.add(atlas.findRegion("adventurer-attack2-02"));
        frames.add(atlas.findRegion("adventurer-attack2-03"));
        frames.add(atlas.findRegion("adventurer-attack2-04"));
        frames.add(atlas.findRegion("adventurer-attack2-05"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.NORMAL);
        animations.put(State.AttackGround2, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-attack3-00"));
        frames.add(atlas.findRegion("adventurer-attack3-01"));
        frames.add(atlas.findRegion("adventurer-attack3-02"));
        frames.add(atlas.findRegion("adventurer-attack3-03"));
        frames.add(atlas.findRegion("adventurer-attack3-04"));
        frames.add(atlas.findRegion("adventurer-attack3-05"));
        anim = new Animation<>(1f/16f, frames, Animation.PlayMode.NORMAL);
        animations.put(State.AttackGround3, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-air-attack1-00"));
        frames.add(atlas.findRegion("adventurer-air-attack1-01"));
        frames.add(atlas.findRegion("adventurer-air-attack1-02"));
        frames.add(atlas.findRegion("adventurer-air-attack1-03"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.NORMAL);
        animations.put(State.AttackAir1, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-air-attack3-loop-00"));
        frames.add(atlas.findRegion("adventurer-air-attack3-loop-01"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.LOOP);
        animations.put(State.AttackAir2, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-jump-00"));
        frames.add(atlas.findRegion("adventurer-jump-01"));
        frames.add(atlas.findRegion("adventurer-jump-02"));
        frames.add(atlas.findRegion("adventurer-jump-03"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.NORMAL);
        animations.put(State.JumpFirst, anim);

        frames = new Array<>();
        frames.add(atlas.findRegion("adventurer-smrslt-00"));
        frames.add(atlas.findRegion("adventurer-smrslt-01"));
        frames.add(atlas.findRegion("adventurer-smrslt-02"));
        frames.add(atlas.findRegion("adventurer-smrslt-03"));
        anim = new Animation<>(1f/12f, frames, Animation.PlayMode.LOOP);
        animations.put(State.JumpSecond, anim);

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

    public void setAnimationState(State type)
    {
        if(currentAnimation != type)
        {
            currentAnimation = type;
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
