package barycentric.component;

public class CharacterStateComponent extends Component
{
    public enum State
    {
        OnGround,
        InAir,
    }



    public enum AttackState
    {
        None,
        Forward,
        Neutral,
    }

    public State currentState = State.InAir;
    public AttackState attackState = AttackState.None;

    public final float COOLDOWN_TIME = 0.1f;
    public float cooldownTimer = 0;

    public boolean facingRight = true;
    public boolean hasJumped = true;
    public boolean hasDoubleJumped = true;
}
