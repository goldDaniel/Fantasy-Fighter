package barycentric.component;

public class CharacterStateComponent extends Component
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
