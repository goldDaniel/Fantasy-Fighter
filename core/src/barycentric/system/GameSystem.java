package barycentric.system;

import com.badlogic.gdx.utils.Array;

import barycentric.main.Entity;
import barycentric.component.Component;

public abstract class GameSystem
{
    //entities the system can query for processing
    private final Array<Entity> entities;
    //the components an entity to process will contain
    private final Array<Class<? extends Component>> systemComponents;


    /**
     *
     */
    public GameSystem(Array<Entity> entities, Class<? extends Component>... clss)
    {
        this.entities = entities;
        systemComponents = new Array<>(clss);
    }

    /**
     * This is called before the system processes every entity
     */
    protected void preUpdate()
    {

    }

    /**
     * @param dt delta time between frames
     */
    public final void update(float dt)
    {
        preUpdate();

        Array<Entity> entities = getEntitiesWithComponents(systemComponents.toArray());

        for(Entity e : entities)
        {
            process(e, dt);
        }

        postUpdate();
    }

    /**
     * This is called after the system processes every entity
     */
    protected void postUpdate()
    {

    }

    protected final Array<Entity> getEntitiesWithComponents(Class<? extends Component>... components)
    {
        Array<Entity> result = new Array<>();

        for(Entity e : entities)
        {
            boolean valid = true;

            for(Class c : components)
            {
                if(e.getComponent(c) == null)
                {
                    valid = false;
                }
            }

            if(valid)
            {
                result.add(e);
            }
        }

        return result;
    }

    protected abstract void process(Entity e, float dt);
}
