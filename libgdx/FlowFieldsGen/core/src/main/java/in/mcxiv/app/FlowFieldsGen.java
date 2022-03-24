package in.mcxiv.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import in.mcxiv.app.components.TextScreenComponent;
import in.mcxiv.app.screens.FlowFieldsScreen;
import in.mcxiv.app.screens.TextScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class FlowFieldsGen extends Game {

    private static final int
            anim_pad = 2000,
            anim_scope = 4000;

    public Resources res = null;

    private final TextScreen
            chill = new TextScreen(this),
            drag = new TextScreen(this),
            presenting = new TextScreen(this);
    private final FlowFieldsScreen
            flowFields = new FlowFieldsScreen(this);

    private Action action;

    @Override
    public void create() {
        res = new Resources();

        action = Actions.sequence(
                Actions.delay(2),
                Actions.run(() -> setTextScreen(chill, "Chill, and Watch!")),
                Actions.delay(5),
                Actions.run(() -> setTextScreen(drag, "And don't drag...")),
                Actions.delay(5),
                Actions.run(() -> setTextScreen(presenting, "Presenting Flow Fields...")),
                Actions.delay(5),
                Actions.run(() -> setScreen(flowFields))
        );
        action.setActor(new Actor());
    }

    public void setTextScreen(TextScreen screen, String text) {
        super.setScreen(screen);

        int entity = res.world.create(res.textScreenEntity);
        TextScreenComponent component = res.mtextScreen.get(entity);

        component.text = text;
        component.screen = screen;
        screen.setComponentForReference(component);
    }

    @Override
    public void render() {
        res.view.apply(true);
        super.render();

        float deltaTime = Gdx.graphics.getDeltaTime();
        res.world.setDelta(deltaTime);
        res.world.process();
        res.tween.step(deltaTime);
        action.act(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        res.view.update(width, height);
    }

    @Override
    public void dispose() {
        res.dispose();
    }
}