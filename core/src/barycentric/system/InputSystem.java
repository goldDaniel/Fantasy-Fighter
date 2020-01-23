package barycentric.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

import barycentric.component.KeyboardInputComponent;
import barycentric.component.CharacterStateComponent;
import barycentric.main.Entity;

public class InputSystem extends GameSystem
{

    InputMultiplexer multiplexer = new InputMultiplexer();
    IntMap<InputProcessor> processors = new IntMap<>();

    public InputSystem(Array<Entity> e)
    {
        super(e, KeyboardInputComponent.class, CharacterStateComponent.class);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        if(!processors.containsKey(e.ID))
        {
            KeyboardInputComponent in = (KeyboardInputComponent)e.getComponent(KeyboardInputComponent.class);
            addInputListener(in);
        }
    }

    private void addInputListener(final KeyboardInputComponent in)
    {
        InputProcessor processor = new InputAdapter()
        {
            @Override
            public boolean keyDown(int keycode)
            {
                if(keycode == in.LEFT || keycode == in.RIGHT ||
                   keycode == in.DUCK || keycode == in.JUMP)
                {
                    in.setKey(keycode, true);
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode)
            {
                if(keycode == in.LEFT || keycode == in.RIGHT ||
                   keycode == in.DUCK || keycode == in.JUMP)
                {
                    in.setKey(keycode, false);
                    return true;
                }
                return false;
            }
        };

        multiplexer.addProcessor(processor);
    }
}
