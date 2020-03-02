package barycentric.ecs.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderableComponent extends Component
{
    private static TextureRegion defaultTex = new TextureRegion(new Texture("Textures/Default.png"));

    private Color color = new Color(Color.WHITE);
    private TextureRegion renderable = defaultTex;
    private boolean facingRight = true;





    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color.set(color);
    }

    public void setFacingRight(boolean value)
    {
        facingRight = value;
    }

    public boolean isFacingRight()
    {
        return facingRight;
    }

    public TextureRegion getRenderable()
    {
        return renderable;
    }

    public void setTextureRegion(TextureRegion r)
    {
        renderable = r;
    }
}
