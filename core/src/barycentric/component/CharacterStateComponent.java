package barycentric.component;

public class CharacterStateComponent extends Component
{
    public enum State
    {
        OnGround,
        InAir,
        GroundAttackForward,
        GroundAttackUp,
    }

    public State currentState = State.InAir;

    public boolean facingRight = true;

    public boolean hasJumped = false;
    public boolean hasDoubleJumped = false;
}
