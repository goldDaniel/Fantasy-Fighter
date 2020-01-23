package barycentric.component;

public class CharacterStateComponent extends Component
{
    public enum State
    {
        OnGround,
        InAir,
    }

    public State currentState = State.InAir;

    public enum AttackState
    {
        None,
        Forward,
        Neutral,
    }

    public final float COOLDOWN_TIME = 0.25f;
    public float cooldownTimer = 0;

    public AttackState attackState = AttackState.None;
    public boolean facingRight = true;
    public boolean hasJumped = false;
    public boolean hasDoubleJumped = false;
}
