package barycentric.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

import barycentric.component.AnimationComponent;
import barycentric.component.KeyboardInputComponent;
import barycentric.component.MapCollisionComponent;
import barycentric.component.MovementComponent;
import barycentric.component.PlayerStateComponent;
import barycentric.component.RenderableComponent;
import barycentric.component.TransformComponent;
import barycentric.system.AnimationSystem;
import barycentric.system.CameraSystem;
import barycentric.system.DebugRenderingSystem;
import barycentric.system.GameSystem;
import barycentric.system.InputSystem;
import barycentric.system.MapCollisionSystem;
import barycentric.system.MovementSystem;
import barycentric.system.PlayerMovementSystem;
import barycentric.system.PlayerStateSystem;
import barycentric.system.RenderingSystem;

public class Entry extends ApplicationAdapter
{
	Array<Entity> entities = new Array<>();
	Array<GameSystem> systems = new Array<>();

	//TODO: find a better way to do the resize callback
	RenderingSystem s;

	@Override
	public void create ()
	{
		OrthographicCamera cam = new OrthographicCamera(640, 360);

		TiledMap map = new TmxMapLoader().load("Tiled/arena.tmx");

		Entity e = new Entity("Dan")
				.addComponent(new KeyboardInputComponent(Input.Keys.A, Input.Keys.D, Input.Keys.S, Input.Keys.W))
				.addComponent(new PlayerStateComponent())
				.addComponent(new MovementComponent())
				.addComponent(new MapCollisionComponent(-8, -16, 16, 32))
				.addComponent(new AnimationComponent())
				.addComponent(new RenderableComponent());
		entities.add(e);

		TransformComponent t = (TransformComponent)e.getComponent(TransformComponent.class);
		t.position.x = 128;
		t.position.y = 256;

		e = new Entity("Maz")
				.addComponent(new KeyboardInputComponent(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.DOWN, Input.Keys.UP))
				.addComponent(new PlayerStateComponent())
				.addComponent(new MovementComponent())
				.addComponent(new MapCollisionComponent(-8, -16, 16, 32))
				.addComponent(new AnimationComponent())
				.addComponent(new RenderableComponent());
		entities.add(e);

		t = (TransformComponent)e.getComponent(TransformComponent.class);
		t.position.x = 256;
		t.position.y = 256;

		systems.add(new InputSystem(entities));
		systems.add(new PlayerStateSystem(entities));
		systems.add(new MovementSystem(entities));
		systems.add(new PlayerMovementSystem(entities));
		systems.add(new MapCollisionSystem(entities, map));
		systems.add(new AnimationSystem(entities));
		systems.add(new CameraSystem(entities, cam));
		s = new RenderingSystem(entities, map, cam);
		systems.add(s);
		systems.add(new DebugRenderingSystem(entities, cam));
	}

	@Override
	public void render ()
	{
		float dt = Gdx.graphics.getDeltaTime();

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
