package in.mcxiv.ui;

import in.mcxiv.animation.Animator;
import in.mcxiv.animation.TimeLine;
import in.mcxiv.animation.events.StartEvent;
import in.mcxiv.animation.events.StopEvent;
import in.mcxiv.animation.events.UIComponentEvent;
import in.mcxiv.music.MusicBox;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    public static void main(String[] args) {
        new Display();
    }

    private final Animator animator = new Animator();
    private final TimeLine timeLine;
    private int timeLine_sch = 0;

    public Display() throws HeadlessException {
        super("Flow Fields in Java");
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setBackground(Color.BLACK);
        pack();// ?
        setVisible(true);

        int anim_pad = 2000;
        int anim_scope = 4000;

        timeLine = animator.getTimeLine();

        timeLine_sch += anim_pad;
        addToTimeline(new LargeTextPane("Chill, and Watch!", anim_scope), anim_scope);
        addToTimeline(new LargeTextPane("And don't drag...", anim_scope), anim_scope);
        addToTimeline(new LargeTextPane("Presenting Flow Fields...", anim_scope), anim_scope);

        {
            FlowFieldsPane pane = new FlowFieldsPane();
            StartEvent startEvent = new StartEvent(pane, timeLine_sch);
            timeLine.addEvent(new UIComponentEvent(c -> this.addComponent(pane), this::remove, pane, startEvent));
        }

        animator.start();
        new MusicBox();
    }

    private void addToTimeline(LargeTextPane pane, int length) {
        StartEvent startEvent = new StartEvent(pane, timeLine_sch);
        StopEvent stopEvent = new StopEvent(pane, timeLine_sch + length);
        timeLine_sch += length + /*pad*/ 1000;
        timeLine.addEvent(new UIComponentEvent(c -> this.addComponent(pane), this::remove, pane, startEvent));
        timeLine.addEvent(new UIComponentEvent(c -> this.addComponent(pane), this::remove, pane, stopEvent));
    }

    private Component addComponent(Component mat) {
        add(mat);
        mat.setBounds(0, 0, getWidth(), getHeight());
        mat.revalidate();
        mat.repaint();
        return mat;
    }
}
