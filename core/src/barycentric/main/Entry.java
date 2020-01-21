package barycentric.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import barycentric.component.AnimationComponent;
import barycentric.component.Component;
import barycentric.component.RenderableComponent;
import barycentric.system.AnimationSystem;
import barycentric.system.GameSystem;
import barycentric.system.RenderingSystem;

public class Entry extends ApplicationAdapter
{
	float stateTime = 0;

	Array<Entity> entities = new Array<>();
	Array<GameSystem> systems = new Array<>();



	@Override
	public void create ()
	{

		Entity e = new Entity("Player")
									 .addComponent(new AnimationComponent())
									 .addComponent(new RenderableComponent());
		entities.add(e);


		systems.add(new AnimationSystem(entities));
		systems.add(new RenderingSystem(entities));
	}

	@Override
	public void render ()
	{
		float dt = Gdx.graphics.getDeltaTime();
		stateTime += dt;

		for(GameSystem s : systems)
		{
			s.update(dt);
		}
	}

	@Override
	public void resize(int w, int h)
	{
//		viewport.update(w, h);
//		viewport.apply();
	}

}
