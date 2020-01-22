package barycentric.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderableComponent extends Component
{
    private static TextureRegion defaultTex = new TextureRegion(new Texture("Textures/Default.png"));

    private TextureRegion renderable = defaultTex;

    private boolean facingRight;

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
