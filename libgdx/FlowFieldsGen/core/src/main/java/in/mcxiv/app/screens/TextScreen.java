package in.mcxiv.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import in.mcxiv.app.FlowFieldsGen;
import in.mcxiv.app.Resources;
import in.mcxiv.app.components.TextScreenComponent;

public class TextScreen implements Screen {

    private final FlowFieldsGen gen;
    private TextScreenComponent comp;

    public TextScreen(FlowFieldsGen gen) {
        this.gen = gen;
    }

    public void setComponentForReference(TextScreenComponent comp) {
        this.comp = comp;
    }

    @Override
    public void show() {
        assert comp != null;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        var font = gen.res.fnt_main;
        var text = comp.text;
        var layt = gen.res.layout;
        var bath = gen.res.bth_sprite;

        layt.setText(font, text);

        var W = Gdx.graphics.getWidth();
        var H = Gdx.graphics.getHeight();
        var w = layt.width;
        var h = layt.height;

        bath.setProjectionMatrix(gen.res.camera.combined);
        bath.begin();
        bath.setShader(gen.res.shd_font);
        font.setColor(0f, 0f, 0f, comp.opacity);
        font.draw(
                bath,
                comp.text,
                (W - w) / 2,
                (H + h) / 2);
        bath.setShader(null);
        bath.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        this.comp = null;
    }
}
