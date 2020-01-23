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

    public AttackState attackState = AttackState.None;
    public boolean facingRight = true;
    public boolean hasJumped = false;
    public boolean hasDoubleJumped = false;
}
