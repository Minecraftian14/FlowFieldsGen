package in.mcxiv.app.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ParticleComponent extends PooledComponent {

    public Vector2 position = new Vector2();

    public ParticleComponent() {
        reset();
    }

    @Override
    public void reset() {
        position.set(
                Gdx.graphics.getWidth() * MathUtils.random(),
                Gdx.graphics.getWidth() * MathUtils.random()
        );
    }

    public boolean outOfBoundsFor(float width, float height) {
        return position.x < 0 || position.y < 0 || position.x >= width || position.y >= height;
    }
}
