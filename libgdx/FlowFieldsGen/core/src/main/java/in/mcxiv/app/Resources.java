package in.mcxiv.app;

import com.artemis.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cyphercove.gdxtween.TweenRunner;
import in.mcxiv.app.components.TextScreenComponent;
import in.mcxiv.app.systems.ForceFieldSystem;
import in.mcxiv.app.systems.RenderParticleSystem;
import in.mcxiv.app.systems.TextScreenSystem;

public class Resources implements Disposable {

    public final Texture tex_font;
    public final BitmapFont fnt_main;
    public final ShaderProgram shd_font;
    public final Camera camera;
    public final Viewport view;
    public final SpriteBatch bth_sprite;
    public final ImmediateModeRenderer20 bth_points;
    public final GlyphLayout layout;
    public final World world;
    public final TweenRunner tween;

    public final Archetype textScreenEntity;

    protected ComponentMapper<TextScreenComponent> mtextScreen;

    public Resources() {
        tex_font = new Texture(Gdx.files.internal("font.png"), true);
        tex_font.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);

        fnt_main = new BitmapFont(
                Gdx.files.internal("font.fnt"),
                new TextureRegion(tex_font),
                false
        );
        fnt_main.getData().scale(2);

        shd_font = new ShaderProgram(Gdx.files.internal("shaders/font.vert"), Gdx.files.internal("shaders/font.frag"));
        if (!shd_font.isCompiled())
            Gdx.app.error("fontShader", "compilation failed:\n" + shd_font.getLog());

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        view = new FitViewport(width, height, camera);

        bth_sprite = new SpriteBatch();
        bth_points = new ImmediateModeRenderer20(width * height, false, true, 0);
        layout = new GlyphLayout();

        world = new World(
                new WorldConfigurationBuilder()
                        .with(new TextScreenSystem())
                        .with(new ForceFieldSystem())
                        .with(new RenderParticleSystem(this))
                        .build()
        );
        world.getSystem(ForceFieldSystem.class).setEnabled(false);
        world.getSystem(RenderParticleSystem.class).setEnabled(false);

        tween = new TweenRunner();

        textScreenEntity = new ArchetypeBuilder()
                .add(TextScreenComponent.class)
                .build(world);

        world.inject(this);
    }

    @Override
    public void dispose() {
        tween.cancelAllTweens();

        tex_font.dispose();
        fnt_main.dispose();
        shd_font.dispose();
        bth_sprite.dispose();
        bth_points.dispose();
        world.dispose();
    }
}
