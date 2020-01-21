package barycentric.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;

import barycentric.component.AnimationComponent;
import barycentric.component.RenderableComponent;
import barycentric.component.TransformComponent;
import barycentric.system.AnimationSystem;
import barycentric.system.CameraSystem;
import barycentric.system.GameSystem;
import barycentric.system.RenderingSystem;

public class Entry extends ApplicationAdapter
{
	float stateTime = 0;

	Array<Entity> entities = new Array<>();
	Array<GameSystem> systems = new Array<>();


	RenderingSystem s;

	@Override
	public void create ()
	{
		OrthographicCamera cam = new OrthographicCamera(360, 240);

		Entity e = new Entity("Player")
				.addComponent(new AnimationComponent())
				.addComponent(new RenderableComponent());
		entities.add(e);

		((TransformComponent)e.getComponent(TransformComponent.class)).position.x += 64;

		e = new Entity("Player")
				.addComponent(new AnimationComponent())
				.addComponent(new RenderableComponent());
		entities.add(e);

		((TransformComponent)e.getComponent(TransformComponent.class)).position.x -= 64;

		e = new Entity("Player")
				.addComponent(new AnimationComponent())
				.addComponent(new RenderableComponent());
		entities.add(e);

		((TransformComponent)e.getComponent(TransformComponent.class)).position.x += 64;
		((TransformComponent)e.getComponent(TransformComponent.class)).position.y += 64;

		e = new Entity("Player")
				.addComponent(new AnimationComponent())
				.addComponent(new RenderableComponent());
		entities.add(e);

		((TransformComponent)e.getComponent(TransformComponent.class)).position.x -= 64;
		((TransformComponent)e.getComponent(TransformComponent.class)).position.y += 64;

		s = new RenderingSystem(entities, cam);

		systems.add(new AnimationSystem(entities));
		systems.add(new CameraSystem(entities, cam));
		systems.add(s);
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
		s.updateViewport(w, h);
	}
}
