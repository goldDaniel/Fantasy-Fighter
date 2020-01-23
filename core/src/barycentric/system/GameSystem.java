package barycentric.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import barycentric.main.Entity;
import barycentric.component.Component;

public abstract class GameSystem
{
    //entities the system can query for processing
    private final Array<Entity> entities;
    //the components an entity to process will contain
    private final Array<Class<? extends Component>> systemComponents;


    public GameSystem(Array<Entity> e)
    {
        this.entities = null;
        systemComponents = null;
        Gdx.app.error("GameSystem", "System created without components. Shutting down");
        Gdx.app.exit();
    }
    /**
     *
     */
    protected GameSystem(Array<Entity> entities, Class<? extends Component> cls, Class<? extends Component>... classArr)
    {
        this.entities = entities;
        systemComponents = new Array<>();
        systemComponents.add(cls);
        systemComponents.addAll(classArr);
    }

    protected abstract void process(Entity e, float dt);
    public abstract void dispose();

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

        Array<Entity> iter = getEntitiesWithComponents(systemComponents);


        for(Entity e : iter)
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


    private final Array<Entity> getEntitiesWithComponents(Array<Class<? extends Component>> components)
    {

        Array<Entity> result = new Array<>();

        if(components.size > 0)
        {
            for(Entity e : entities)
            {
                boolean validEntity = true;

                for(Class c : components)
                {
                    if(e.getComponent(c) == null)
                    {
                        validEntity = false;
                    }
                }

                if(validEntity)
                {
                    result.add(e);
                }
            }
        }
        return result;
    }


}
