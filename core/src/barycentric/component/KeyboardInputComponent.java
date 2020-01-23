package barycentric.component;

import com.badlogic.gdx.utils.IntMap;

public class KeyboardInputComponent extends Component
{
    public final int LEFT;
    public final int RIGHT;
    public final int JUMP;

    private IntMap<Boolean> keyPressed = new IntMap<>(4);

    public KeyboardInputComponent(int left, int right, int duck, int jump)
    {
        LEFT = left;
        RIGHT = right;
        JUMP = jump;

        keyPressed.put(LEFT, false);
        keyPressed.put(RIGHT, false);
        keyPressed.put(JUMP, false);
    }

    public void setKey(int key, boolean value)
    {
        if(keyPressed.containsKey(key))
        {
            keyPressed.put(key, value);
        }
    }

    public boolean isKeyDown(int key)
    {
        if(keyPressed.containsKey(key))
        {
            return keyPressed.get(key);
        }

        return false;
    }
}
