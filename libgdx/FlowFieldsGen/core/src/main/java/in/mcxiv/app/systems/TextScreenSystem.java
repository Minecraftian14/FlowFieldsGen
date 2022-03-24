package in.mcxiv.app.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import in.mcxiv.app.components.TextScreenComponent;

@All(TextScreenComponent.class)
public class TextScreenSystem extends IteratingSystem {

    protected ComponentMapper<TextScreenComponent> mTestScreen;

    @Override
    protected void process(int entityId) {
        TextScreenComponent textScreen = mTestScreen.get(entityId);
        updateTransparency(textScreen);
    }

    private void updateTransparency(TextScreenComponent ts) {
        if (ts.time < ts.halfLength) {
            ts.opacity = ts.time / ts.halfLength;
        } else if (ts.time < ts.length) {
            ts.opacity = 1f - (ts.time - ts.halfLength) / ts.halfLength;
        } // else getParent().remove(this);
        ts.time += this.world.getDelta() * 1000;
    }
}
