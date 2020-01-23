package barycentric.component;

public class MapCollisionComponent extends Component
{
    //X and Y are relative to transform X and Y
    public final float X;
    public final float Y;

    public final float WIDTH;
    public final float HEIGHT;


    public MapCollisionComponent(float x, float y, float w, float h)
    {
        X = x;
        Y = y;

        WIDTH = w;
        HEIGHT = h;
    }
}
