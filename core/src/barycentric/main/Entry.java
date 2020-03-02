package barycentric.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

import barycentric.ecs.component.AnimationComponent;
import barycentric.ecs.component.CharacterStateComponent;
import barycentric.ecs.component.ControllerInputComponent;
import barycentric.ecs.component.HurtboxComponent;
import barycentric.ecs.component.KeyboardInputComponent;
import barycentric.ecs.component.MapCollisionComponent;
import barycentric.ecs.component.MovementComponent;
import barycentric.ecs.component.RenderableComponent;
import barycentric.ecs.component.TransformComponent;
import barycentric.ecs.system.AnimationSystem;
import barycentric.ecs.system.CameraSystem;
import barycentric.ecs.system.ControllerInputSystem;
import barycentric.ecs.system.DebugRenderingSystem;
import barycentric.ecs.system.GameSystem;
import barycentric.ecs.system.KeyboardInputSystem;
import barycentric.ecs.system.PlayerMovementSystem;
import barycentric.ecs.system.RenderingSystem;

public class Entry extends ApplicationAdapter
{
	Array<Entity> entities = new Array<>();
	Array<GameSystem> systems = new Array<>();

	//TODO: find a better way to do the resize callback
	RenderingSystem s;

	@Override
	public void create ()
	{
		Controllers.addListener(new ControllerAdapter());

		entities.clear();
		systems.clear();

		OrthographicCamera cam = new OrthographicCamera(640, 360);
		TiledMap map = new TmxMapLoader().load("Tiled/arena.tmx");

		Entity e = new Entity("Dan")
				.addComponent(new ControllerInputComponent(104,105, 103, 1,0,3))
				.addComponent(new CharacterStateComponent())
				.addComponent(new MovementComponent())
				.addComponent(new MapCollisionComponent(-8, -16, 16, 32))
				.addComponent(new HurtboxComponent())
				.addComponent(new AnimationComponent())
				.addComponent(new RenderableComponent());
		entities.add(e);

		TransformComponent t = (TransformComponent)e.getComponent(TransformComponent.class);
		t.position.x = 16*16;
		t.position.y = 16*40;
		RenderableComponent r = (RenderableComponent)e.getComponent(RenderableComponent.class);
		r.setColor(Color.WHITE);

		e = new Entity("Maz")
				.addComponent(new KeyboardInputComponent(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.DOWN, Input.Keys.UP, Input.Keys.PERIOD, Input.Keys.SLASH))
				.addComponent(new CharacterStateComponent())
				.addComponent(new MovementComponent())
				.addComponent(new MapCollisionComponent(-8, -16, 16, 32))
				.addComponent(new AnimationComponent())
				.addComponent(new RenderableComponent());
		entities.add(e);

		t = (TransformComponent)e.getComponent(TransformComponent.class);
		t.position.x = 16*48;
		t.position.y = 16*40;
		r = (RenderableComponent)e.getComponent(RenderableComponent.class);
		r.setColor(Color.GRAY);






		s = new RenderingSystem(entities, map, cam);

		systems.add(new ControllerInputSystem(entities));
		systems.add(new KeyboardInputSystem(entities));
		systems.add(new PlayerMovementSystem(entities,map));
		systems.add(new AnimationSystem(entities));
		systems.add(new CameraSystem(entities, cam));
		systems.add(s);
		systems.add(new DebugRenderingSystem(entities, cam));
	}


	@Override
	public void render ()
	{
		float dt = Gdx.graphics.getDeltaTime();

		//restart
		if(Gdx.input.isKeyJustPressed(Input.Keys.R))
		{
			dispose();
			create();
		}

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

	@Override
	public void dispose()
	{
		for(GameSystem s : systems)
		{
			s.dispose();
		}
	}
}
