package barycentric.component;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

public class ControllerInputComponent extends InputComponent
{
    Controller controller;

    public ControllerInputComponent(int left, int right, int jump, int attackWeak, int attackStrong)
    {
        super(left, right, jump, attackWeak, attackStrong);

        if(Controllers.getControllers().size > 0)
        {
            controller = Controllers.getControllers().first();
        }
    }
}
