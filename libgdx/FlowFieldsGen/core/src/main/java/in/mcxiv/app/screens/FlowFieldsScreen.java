package in.mcxiv.app.screens;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import in.mcxiv.app.FlowFieldsGen;
import in.mcxiv.app.components.TextScreenComponent;
import in.mcxiv.app.systems.ForceFieldSystem;
import in.mcxiv.app.systems.RenderParticleSystem;

public class FlowFieldsScreen extends ScreenAdapter {

    private final FlowFieldsGen gen;

    public FlowFieldsScreen(FlowFieldsGen gen) {
        this.gen = gen;
    }

    @Override
    public void show() {
        World world = gen.res.world;
        world.getSystem(ForceFieldSystem.class).setEnabled(true);
        world.getSystem(RenderParticleSystem.class).setEnabled(true);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void hide() {
        World world = gen.res.world;
        world.getSystem(ForceFieldSystem.class).setEnabled(false);
        world.getSystem(RenderParticleSystem.class).setEnabled(false);
    }
}
