package barycentric.system;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

import barycentric.component.ControllerInputComponent;
import barycentric.main.Entity;

public class ControllerInputSystem extends GameSystem
{


    IntMap<ControllerListener> processors = new IntMap<>();

    public ControllerInputSystem(Array<Entity> e)
    {
        super(e, ControllerInputComponent.class);
    }

    public IntMap<ControllerListener> getListeners()
    {
        return processors;
    }


    @Override
    protected void process(Entity e, float dt)
    {
        if(!processors.containsKey(e.ID))
        {
            ControllerInputComponent in = (ControllerInputComponent)e.getComponent(ControllerInputComponent.class);
            addInputListener(e.ID, in);
        }
    }

    private void addInputListener(int ID, final ControllerInputComponent in)
    {
        ControllerListener processor = new ControllerAdapter()
        {
            @Override
            public boolean buttonDown(Controller controller, int buttonCode)
            {
                if(buttonCode == in.JUMP || buttonCode == in.ATTACK_WEAK || buttonCode == in.ATTACK_STRONG)
                {
                    in.setKey(buttonCode, true);
                    return true;
                }
                return false;
            }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode)
            {
                if(buttonCode == in.JUMP || buttonCode == in.ATTACK_WEAK || buttonCode == in.ATTACK_STRONG)
                {
                    in.setKey(buttonCode, false);
                    return true;
                }
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value)
            {
                if(axisCode == 0)
                {
                    if(value > 0.25) in.setKey(in.RIGHT, true);
                    else             in.setKey(in.RIGHT, false);

                    if(value < -0.25) in.setKey(in.LEFT, true);
                    else              in.setKey(in.LEFT, false);
                }

                return false;
            }

            @Override
            public boolean povMoved(Controller controller, int povCode, PovDirection value)
            {
                return false;
            }
        };

        Controllers.addListener(processor);
        processors.put(ID, processor);
    }

    @Override
    public void dispose()
    {

    }
}
