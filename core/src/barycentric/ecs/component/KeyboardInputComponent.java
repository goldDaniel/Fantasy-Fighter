package barycentric.ecs.component;

public class KeyboardInputComponent extends InputComponent
{

    public KeyboardInputComponent(int left, int right, int down, int jump,  int attackWeak, int attackStrong)
    {
        super(left, right, down, jump, attackWeak, attackStrong);
    }
}
