package barycentric.component;

public class PlayerStateComponent extends Component
{
    public enum State
    {
        Idle,
        Duck,
        Run,
        Attack1,
    }

    public State currentState = State.Idle;

    public boolean facingRight = true;
}