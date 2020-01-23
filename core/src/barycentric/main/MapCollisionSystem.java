package barycentric.main;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import barycentric.component.MapCollisionComponent;
import barycentric.component.MovementComponent;
import barycentric.component.PlayerStateComponent;
import barycentric.component.TransformComponent;

public class MapCollisionSystem
{
    private TiledMap map;

    private Array<Rectangle> collidableTiles = new Array<>();

    //this is here so we don't allocate a rectangle every iteration
    private Rectangle colRect = new Rectangle();

    public MapCollisionSystem(TiledMap map)
    {
        this.map = map;

        TiledMapTileLayer collisionLayer = (TiledMapTileLayer)map.getLayers().get("Collision");

        for(int i = 0; i < collisionLayer.getWidth(); i++)
        {
            for(int j = 0; j < collisionLayer.getHeight(); j++)
            {
                if(collisionLayer.getCell(i,j) != null)
                {
                    float x = i * collisionLayer.getTileWidth();
                    float y = j * collisionLayer.getTileHeight();
                    float w = collisionLayer.getTileWidth();
                    float h = collisionLayer.getTileHeight();

                    collidableTiles.add(new Rectangle(x, y, w, h));
                }
            }
        }
    }

    public void processVertical(Entity e, TransformComponent transform, PlayerStateComponent state)
    {
        MovementComponent movement = (MovementComponent)e.getComponent(MovementComponent.class);
        MapCollisionComponent col    = (MapCollisionComponent)e.getComponent(MapCollisionComponent.class);

        colRect.set(transform.position.x + col.X,
                transform.position.y + col.Y,
                col.WIDTH,
                col.HEIGHT);

        state.currentState = PlayerStateComponent.State.InAir;
        for (Rectangle tile : collidableTiles)
        {
            int side = getCollisionSide(colRect, tile);

            if (side == 1)
            {
                transform.position.y = tile.y + tile.height - col.Y;
                state.currentState = PlayerStateComponent.State.OnGround;
            }
            if (side == -1)
            {
                transform.position.y = tile.y - col.HEIGHT / 2f;
                movement.velocityY = 0;
            }
        }
    }

    public void processHorizontal(Entity e, TransformComponent transform, PlayerStateComponent state)
    {
        MapCollisionComponent col = (MapCollisionComponent) e.getComponent(MapCollisionComponent.class);

        colRect.set(transform.position.x + col.X,
                transform.position.y + col.Y,
                col.WIDTH,
                col.HEIGHT);

        for (Rectangle tile : collidableTiles)
        {
            int side = getCollisionSide(colRect, tile);

            if (side == -2)
            {
                transform.position.x = tile.x - col.WIDTH / 2;
            }
            if (side == 2)
            {
                transform.position.x = tile.x + tile.width + col.WIDTH / 2;
            }
        }
    }

    /**
     * Returns side entity is colliding with relative to this.
     *
     *  1 for top
     * -1 for bottom
     * -2 for left
     * 2 for right
     *
     * @param r2
     * @return
     */
    private int getCollisionSide(Rectangle r1, Rectangle r2)
    {
        int result = 0;
        //decide what side the object is on relative to the tile
        if (r1.overlaps(r2))
        {
            //horizontal side
            boolean left = r1.x < r2.x;
            //vertical side
            boolean above = r1.y > r2.y;

            //holds how deep the object is inside the tile on each axis
            float horizontalDif;
            float verticalDif;

            //determine the differences for depth
            if (left)
            {
                horizontalDif = r1.x + r1.width - r2.x;
            }
            else
            {
                horizontalDif = r2.x + r2.width - r1.x;
            }

            if (above)
            {
                verticalDif = r2.y + r2.height - r1.y;
            }
            else
            {
                verticalDif = r1.y + r1.height - r2.y;
            }

            if (horizontalDif < verticalDif)
            {
                if (left)
                {
                    result = -2;
                }
                else
                {
                    result = 2;
                }
            }
            else if (above)
            {
                result = 1;
            }
            else
            {
                result = -1;
            }
        }
        return result;
    }
}
