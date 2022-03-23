package in.mcxiv.ui;

import java.awt.*;
import java.awt.color.ColorSpace;

public class SimpleMutableFloatingColor extends Color {

    private static final int REMOVE_TRANSPARENCY_MASK = ~(0xFF << 24);
    private float transparency;

    public SimpleMutableFloatingColor(float r, float g, float b, float a) {
        super(r, g, b, a);
        transparency = a;

        // int_a = (int)(float_a*255+0.5)
        // float_a = (((float)int_a)-0.5f)/255f
    }

    public void setTransparency(float transparency) {
        assert transparency >= 0 && transparency <= 1 : "transparency >= 0 && transparency <= 1 but " + transparency;
        this.transparency = transparency;
    }

    @Override
    public int getAlpha() {
        return (int) (transparency * 255 + 0.5);
    }

    @Override
    public int getRGB() {
        return (super.getRGB() & REMOVE_TRANSPARENCY_MASK) | (getAlpha() << 24);
    }

    @Override
    public float[] getRGBComponents(float[] compArray) {
        float[] rgbComponents = super.getRGBComponents(compArray);
        rgbComponents[3] = transparency;
        return rgbComponents;
    }

    @Override
    public float[] getComponents(float[] compArray) {
        // TODO: do i have to put out transparency again?
        return super.getComponents(compArray);
    }
}
