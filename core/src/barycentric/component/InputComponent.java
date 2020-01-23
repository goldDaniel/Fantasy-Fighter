package barycentric.component;

import com.badlogic.gdx.utils.IntMap;

public class InputComponent extends Component
{
    public final int LEFT;
    public final int RIGHT;
    public final int JUMP;
    public final int ATTACK_WEAK;
    public final int ATTACK_STRONG;

    private IntMap<Boolean> keyPressed = new IntMap<>(4);

    public InputComponent(int left, int right, int jump, int attackWeak, int attackStrong)
    {
        LEFT = left;
        RIGHT = right;
        JUMP = jump;
        ATTACK_WEAK = attackWeak;
        ATTACK_STRONG = attackStrong;


        keyPressed.put(LEFT, false);
        keyPressed.put(RIGHT, false);
        keyPressed.put(JUMP, false);
        keyPressed.put(ATTACK_WEAK, false);
        keyPressed.put(ATTACK_STRONG, false);
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
