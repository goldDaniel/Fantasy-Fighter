package barycentric.component;

public class PlayerStateComponent extends Component
{
    public enum State
    {
        OnGround,
        InAir,
        Attack1,
    }

    public State currentState = State.InAir;

    public boolean facingRight = true;
}
