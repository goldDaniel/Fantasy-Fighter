package barycentric.main;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import barycentric.ecs.component.MapCollisionComponent;
import barycentric.ecs.component.MovementComponent;
import barycentric.ecs.component.CharacterStateComponent;
import barycentric.ecs.component.TransformComponent;

public class MapCollisionSystem
{
    private class Tile
    {
        Rectangle rect;

        final boolean ONE_WAY;

        public Tile(Rectangle rect, boolean oneWay)
        {
            this.rect = rect;
            this.ONE_WAY = oneWay;
        }

    }

    private TiledMap map;

    private Array<Tile> collidableTiles = new Array<>();

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
                    boolean oneWay = collisionLayer.getCell(i,j).getTile().getProperties().get("OneWay", Boolean.class);

                    collidableTiles.add(new Tile(new Rectangle(x, y, w, h), oneWay));
                }
            }
        }
    }

    public void processVertical(MovementComponent movement, MapCollisionComponent col, TransformComponent transform, CharacterStateComponent state)
    {
        colRect.set(transform.position.x + col.X,
                transform.position.y + col.Y,
                col.WIDTH,
                col.HEIGHT);

        CharacterStateComponent.State prev = state.currentState;

        state.currentState = CharacterStateComponent.State.InAir;
        for (Tile tile : collidableTiles)
        {
            int side = getCollisionSide(colRect, tile.rect);

            if (side == 1)
            {
                if(prev == CharacterStateComponent.State.InAir)
                {
                    if(state.attackState == CharacterStateComponent.AttackState.Strong)
                    {
                        state.attackState = CharacterStateComponent.AttackState.None;
                        state.currentState = CharacterStateComponent.State.OnGround;
                    }
                }

                if(tile.ONE_WAY)
                {
                    if(movement.velocityY < 0)
                    {
                        transform.position.y = tile.rect.y + tile.rect.height - col.Y;
                        state.currentState = CharacterStateComponent.State.OnGround;
                    }
                }
                else
                {
                    transform.position.y = tile.rect.y + tile.rect.height - col.Y;
                    state.currentState = CharacterStateComponent.State.OnGround;
                }


            }

            if(!tile.ONE_WAY)
            {
                if (side == -1)
                {
                    transform.position.y = tile.rect.y - col.HEIGHT / 2f;
                }
            }
        }
    }

    public void processHorizontal(MovementComponent movement, MapCollisionComponent col, TransformComponent transform, CharacterStateComponent state)
    {
        colRect.set(transform.position.x + col.X,
                transform.position.y + col.Y,
                col.WIDTH,
                col.HEIGHT);

        for (Tile tile : collidableTiles)
        {
            if(!tile.ONE_WAY)
            {
                int side = getCollisionSide(colRect, tile.rect);

                if (side == -2)
                {
                    transform.position.x = tile.rect.x - col.WIDTH / 2;
                }
                if (side == 2)
                {
                    transform.position.x = tile.rect.x + tile.rect.width + col.WIDTH / 2;
                }
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
