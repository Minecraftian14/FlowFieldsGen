package in.mcxiv.ui;

import in.mcxiv.animation.Actor;

import javax.swing.*;
import java.awt.*;

public class LargeTextPane extends JPanel implements Actor {

    private final int length;
    private final int halfLength;
    private float time = 0;
    private float transparency = 1f;
    private final SimpleMutableFloatingColor color = new SimpleMutableFloatingColor(1, 1, 1, 1);
    private final JLabel label;

    public  LargeTextPane(String text, int length) {
        setLayout(new GridBagLayout());
        this.length = length;
        this.halfLength = length / 2;
        setBackground(Color.WHITE);
        add(label = new JLabel(text) {{
            setFont(getFont().deriveFont(getFont().getSize() * 10f));
        }}, new GridBagConstraints() {{
            weightx = weighty = 1;
            anchor = CENTER;
        }});
    }

    @Override
    public void step(float delta) {
        if (time < halfLength) {
            transparency = 1f - time / halfLength;
            repaint();
        } else if (time < length) {
            transparency = (time - halfLength) / halfLength;
            repaint();
        } else getParent().remove(this);
        time += delta;
    }

    private final Rectangle temp = new Rectangle();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        label.getBounds(temp);
        label.paint(g.create(temp.x, temp.y, temp.width, temp.height));

        color.setTransparency(transparency);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void paintChildren(Graphics g) {
    }
}
