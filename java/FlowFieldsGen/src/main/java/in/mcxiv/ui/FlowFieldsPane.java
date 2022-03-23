package in.mcxiv.ui;

import in.mcxiv.animation.Actor;
import pd.OpenSimplex2;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FlowFieldsPane extends JPanel implements Actor {

    private static final boolean IS_DEBUGGING = false;


    private static final int FORCE_POINTS_X = 192;
    private static final int FORCE_POINTS_Y = 108;

    private static final float DENSITY_FACTOR = 0.01f;

    private static final int PARTICLES = 1000;
    private static final float VARIANCE = 3;
    private static final float AMPLITUDE = 2;
    private static final Color COLOR = new Color(0, 0, 0, 0.1f);

    private static final long SEED = new Random().nextLong();

    private final float[][][] forceField;
    private final float[][] particles;

    public FlowFieldsPane() {
        setBackground(new Color(0, true));
        forceField = new float[FORCE_POINTS_Y][FORCE_POINTS_X][2];
        for (int i = 0; i < FORCE_POINTS_Y; i++) {
            for (int j = 0; j < FORCE_POINTS_X; j++) {
                float vector = OpenSimplex2.noise2(SEED, i * DENSITY_FACTOR, j * DENSITY_FACTOR) * VARIANCE;
                forceField[i][j][0] = (float) Math.sin(vector) * AMPLITUDE;
                forceField[i][j][1] = (float) Math.cos(vector) * AMPLITUDE;
            }
        }
        particles = new float[PARTICLES][2];
        // IMPLEMENTATION SPECIFIC FLOW OF INSTRUCTIONS
        // Note, to initialise the particle positions, I require the width and height
        // But, it's usually not available in the constructor, so I can't use getW/getH
        // However, the only place it is used in this project, ie, Display class
        // calls the setBounds function, which also has the w and h param
        // Therefore I can just hijack the super impl to initialise my particle positions.
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        Random random = new Random();
        for (int i = 0; i < PARTICLES; i++) {
            particles[i][0] = random.nextFloat() * height;
            particles[i][1] = random.nextFloat() * width;
        }
    }

    @Override
    public void step(float delta) {
        for (int i = 0; i < PARTICLES; i++) {
            float[] force = forceField
                    [(int) (particles[i][0] / getHeight() * FORCE_POINTS_Y)]
                    [(int) (particles[i][1] / getWidth() * FORCE_POINTS_Y)];
            particles[i][0] = clamp(0, getHeight(), particles[i][0] + force[0]);
            particles[i][1] = clamp(0, getWidth(), particles[i][1] + force[1]);
        }
        repaint();
    }

    private float clamp(float a, float b, float v) {
        if (v < a || v > b) return (float) (Math.random() * (b - a) + a);
        return v;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (IS_DEBUGGING) {
            g.setColor(Color.RED);
            for (int i = 0; i < FORCE_POINTS_Y; i++) {
                for (int j = 0; j < FORCE_POINTS_X; j++) {
                    int y = (int) (i * 1f / FORCE_POINTS_Y * getHeight());
                    int x = (int) (j * 1f / FORCE_POINTS_X * getWidth());
                    int y_r = y + (int) (forceField[i][j][0]);
                    int x_r = x + (int) (forceField[i][j][1]);
                    g.drawLine(x, y, x_r, y_r);
                }
            }
        }
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(COLOR);
        for (int i = 0; i < PARTICLES; i++)
            g.fillRect((int) particles[i][1], (int) particles[i][0], 1, 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
