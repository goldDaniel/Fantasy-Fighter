package barycentric.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import barycentric.main.Entity;
import barycentric.component.RenderableComponent;
import barycentric.component.TransformComponent;

public class RenderingSystem extends GameSystem
{
    ExtendViewport viewport;
    OrthographicCamera cam;

    SpriteBatch s;
    ShapeRenderer sh;

    BitmapFont font;

    TiledMap map;
    OrthogonalTiledMapRenderer tmr;

    public RenderingSystem(Array<Entity> entities, TiledMap map, OrthographicCamera cam)
    {
        super(entities, RenderableComponent.class);
        this.map = map;
        this.cam = cam;

        viewport = new ExtendViewport(cam.viewportWidth, cam.viewportHeight, cam);

        s = new SpriteBatch();
        sh = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(0.5f);

        tmr = new OrthogonalTiledMapRenderer(map, s);
    }

    public void updateViewport(int w, int h)
    {
        viewport.update(w, h);
        viewport.apply();
    }

    @Override
    protected void preUpdate()
    {
        Gdx.gl.glClearColor(0.2f, 0.1f, 0.2f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        tmr.setView(cam);
        tmr.render();

        s.begin();
        s.setProjectionMatrix(cam.combined);
    }

    @Override
    protected void process(Entity e, float dt)
    {
        TransformComponent tr = (TransformComponent)e.getComponent(TransformComponent.class);
        RenderableComponent r = (RenderableComponent)e.getComponent(RenderableComponent.class);

        TextureRegion tex = r.getRenderable();

        if(r.isFacingRight())
        {
            s.draw(tex,
                    tr.position.x - tex.getRegionWidth() / 2f,
                    tr.position.y - tex.getRegionHeight() / 2f,
                    tex.getRegionWidth(),
                    tex.getRegionHeight());
        }
        else
        {
            s.draw(tex,
                    tr.position.x + tex.getRegionWidth() / 2f,
                    tr.position.y - tex.getRegionHeight() / 2f,
                    -tex.getRegionWidth(),
                    tex.getRegionHeight());
        }

        font.draw(s,
                  e.NAME,
                  tr.position.x - 8,
                tr.position.y + tex.getRegionHeight() / 2f);
    }

    @Override
    protected void postUpdate()
    {
        s.end();
    }
}
