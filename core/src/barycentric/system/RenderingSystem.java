package barycentric.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
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


    Array<Texture> backgroundTextures = new Array<>();

    public RenderingSystem(Array<Entity> entities, TiledMap map, OrthographicCamera cam)
    {
        super(entities, RenderableComponent.class);
        this.map = map;
        this.cam = cam;

        viewport = new ExtendViewport(cam.viewportWidth, cam.viewportHeight, cam);

        s = new SpriteBatch();
        sh = new ShapeRenderer();
        font = new BitmapFont();


        tmr = new OrthogonalTiledMapRenderer(map, s);

        for (int i = 2; i <= 6; i++)
        {
            Texture t = new Texture("Textures/Background/Hills Layer 0" + i + ".png");
            backgroundTextures.add(t);
        }
    }

    public void updateViewport(int w, int h)
    {
        viewport.update(w, h);
        viewport.apply();
    }

    @Override
    protected void preUpdate()
    {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.8f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        s.begin();
        s.setColor(Color.WHITE);

        s.setProjectionMatrix(new Matrix4().idt());
        for (Texture t : backgroundTextures)
        {
            s.draw(t,
                    -1, -1,
                    2, 2);
        }
        s.end();

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

        s.setColor(r.getColor());
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
    }

    @Override
    public void dispose()
    {
        s.dispose();
        sh.dispose();
        font.dispose();
        tmr.dispose();

        map.dispose();
    }

    @Override
    protected void postUpdate()
    {
        s.end();
    }
}
