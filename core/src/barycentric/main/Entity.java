package barycentric.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import barycentric.component.Component;
import barycentric.component.TransformComponent;

public class Entity
{
    private static int nextID = 1;


    public final int ID = nextID++;
    public final String NAME;

    private final Array<Component> components = new Array<>();

    public Entity(String name)
    {
        NAME = name;
        addComponent(new TransformComponent());
    }

    /**
     * Returns this entity for chaining.
     * ex. entity.addComponent(asd).addComponent(dsa);
     */
    public Entity addComponent(Component component)
    {
        //only during testing//////////////////////////////////////////
        for(Component c : components)
        {
            if(c.getClass() == component.getClass())
            {
                Gdx.app.error("ENTITY ID: " + ID, "Already has component " + component.getClass().toString());
                Gdx.app.exit();
            }
        }
        //only during testing//////////////////////////////////////////

        components.add(component);

        return this;
    }


    public Component getComponent(Class<? extends Component> cls)
    {
        Component result = null;

        for(Component c : components)
        {
            if(c.getClass() == cls)
            {
                result = c;
            }
        }


        return result;
    }
}
