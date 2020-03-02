package barycentric.ecs.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

import barycentric.ecs.component.KeyboardInputComponent;
import barycentric.main.Entity;

public class KeyboardInputSystem extends GameSystem
{
    InputMultiplexer multiplexer = new InputMultiplexer();
    IntMap<InputProcessor> processors = new IntMap<>();

    public KeyboardInputSystem(Array<Entity> e)
    {
        super(e, KeyboardInputComponent.class);

        Gdx.input.setInputProcessor(multiplexer);
    }

    public InputMultiplexer getMultiplexer()
    {
        return multiplexer;
    }

    @Override
    protected void process(Entity e, float dt)
    {
        if(!processors.containsKey(e.ID))
        {
            KeyboardInputComponent in = (KeyboardInputComponent)e.getComponent(KeyboardInputComponent.class);
            addInputListener(e.ID, in);
        }
    }

    @Override
    public void dispose()
    {
        for(IntMap.Entry<InputProcessor> p : processors)
        {
            multiplexer.removeProcessor(p.value);
        }
        Gdx.input.setInputProcessor(null);
    }

    private void addInputListener(int ID, final KeyboardInputComponent in)
    {
        InputProcessor processor = new InputAdapter()
        {
            @Override
            public boolean keyDown(int keycode)
            {
                if(keycode == in.LEFT || keycode == in.RIGHT || keycode == in.JUMP || keycode == in.ATTACK_WEAK || keycode == in.ATTACK_STRONG)
                {
                    in.setKey(keycode, true);
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode)
            {
                if(keycode == in.LEFT || keycode == in.RIGHT || keycode == in.JUMP || keycode == in.ATTACK_WEAK || keycode == in.ATTACK_STRONG)
                {
                    in.setKey(keycode, false);
                    return true;
                }
                return false;
            }
        };

        multiplexer.addProcessor(processor);
        processors.put(ID, processor);
    }
}
