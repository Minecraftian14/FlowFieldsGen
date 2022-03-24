package in.mcxiv.app.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import in.mcxiv.app.Resources;
import in.mcxiv.app.components.ParticleComponent;

@All(ParticleComponent.class)
public class RenderParticleSystem extends IteratingSystem {

    private static final boolean IS_DEBUGGING = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final Resources res;
    private FrameBuffer buffer;
    private int buffer_code = 0;

    public RenderParticleSystem(Aspect.Builder aspect, Resources res) {
        super(aspect);
        this.res = res;
        getBuffer();
    }

    protected ComponentMapper<ParticleComponent> mParticle;

    public RenderParticleSystem(Resources res) {
        this.res = res;
        getBuffer();
    }

    private FrameBuffer getBuffer() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int new_buffer_code = width << 16 | height;
        if (new_buffer_code != buffer_code) {
            buffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
            buffer.begin();
            Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            buffer.end();
            buffer_code = new_buffer_code;
        }
        return buffer;
    }

    @Override
    protected void begin() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        FrameBuffer buffer = getBuffer();
        buffer.begin();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendEquation(GL20.GL_FUNC_ADD);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        res.bth_points.begin(res.camera.combined, GL20.GL_POINTS);
    }

    @Override
    protected void process(int entityId) {
        var particle = mParticle.get(entityId).position;
        res.bth_points.color(0f, 0f, 0f, 0.1f);
        res.bth_points.vertex(particle.x, particle.y, 0f);
    }

    @Override
    protected void end() {
        res.bth_points.end();
        buffer.end();

        Texture texture = buffer.getColorBufferTexture();
        res.bth_sprite.begin();
        res.bth_sprite.disableBlending();
        res.bth_sprite.draw(texture, 0, 0, texture.getWidth(), texture.getHeight(), 0, 0, 1, 1);
        res.bth_sprite.enableBlending();
        res.bth_sprite.end();
    }
}