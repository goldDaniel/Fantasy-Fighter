package barycentric.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderableComponent extends Component
{
    private TextureRegion renderable;

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
