package barycentric.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderableComponent extends Component
{
    private TextureRegion renderable;

    public TextureRegion getRenderable()
    {
        return renderable;
    }

    public void setTextureRegion(TextureRegion r)
    {
        renderable = r;
    }
}
