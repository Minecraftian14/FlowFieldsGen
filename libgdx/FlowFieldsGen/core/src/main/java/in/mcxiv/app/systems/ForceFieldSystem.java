package in.mcxiv.app.systems;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import in.mcxiv.app.components.ParticleComponent;
import pd.OpenSimplex2;

import java.awt.*;
import java.util.Random;

@All(ParticleComponent.class)
public class ForceFieldSystem extends IteratingSystem {

    private static final int FORCE_POINTS_X = 192;
    private static final int FORCE_POINTS_Y = 108;

    private static final float DENSITY_FACTOR = 0.01f;

    private static final int PARTICLES = 1000;
    private static final float VARIANCE = 3;
    private static final float AMPLITUDE = 2;
    private static final Color COLOR = new Color(0, 0, 0, 0.1f);

    private static final long SEED = new Random().nextLong();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected ComponentMapper<ParticleComponent> mParticle;

    protected Archetype particleArchetype;

    private final Vector2[][] forceField = new Vector2[FORCE_POINTS_Y][FORCE_POINTS_X];

    @Override
    protected void initialize() {
        super.initialize();
        for (int i = 0; i < FORCE_POINTS_Y; i++) {
            for (int j = 0; j < FORCE_POINTS_X; j++) {
                float vector = OpenSimplex2.noise2(SEED, i * DENSITY_FACTOR, j * DENSITY_FACTOR) * VARIANCE;
                forceField[i][j] = new Vector2(
                        MathUtils.sin(vector) * AMPLITUDE / 41 * 1000,
                        MathUtils.cos(vector) * AMPLITUDE / 41 * 1000
                );
            }
        }

        particleArchetype = new ArchetypeBuilder()
                .add(ParticleComponent.class)
                .build(world);
        for (int i = 0; i < PARTICLES; i++)
            world.create(particleArchetype);
    }

    @Override
    protected void process(int entityId) {
        var particle = mParticle.get(entityId);
        var position = particle.position;
        float height = Gdx.graphics.getHeight(); // TODO: move to begin
        float width = Gdx.graphics.getWidth();
        float delta = Gdx.graphics.getDeltaTime();
        if (particle.outOfBoundsFor(width, height)) {
            world.delete(entityId);
            world.create(particleArchetype);
            return;
        }
        position.mulAdd(forceField
                        [(int) (position.y / height * FORCE_POINTS_Y)]
                        [(int) (position.x / width * FORCE_POINTS_X)],
                delta);
    }
}