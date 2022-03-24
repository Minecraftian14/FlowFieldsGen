package in.mcxiv.app.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.Screen;

public class TextScreenComponent extends PooledComponent {

    private static final int
            anim_pad = 2000,
            anim_scope = 4000;

    public String text;
    public int length;
    public int halfLength;
    public float time;
    public float opacity;

    public Screen screen;

    public TextScreenComponent() {
        reset();
    }

    @Override
    public void reset() {
        text = null;
        length = anim_scope;
        halfLength = length / 2;
        time = 0;
        opacity = 0;
        if (screen != null) {
            screen.dispose();
            screen = null;
        }
    }
}
