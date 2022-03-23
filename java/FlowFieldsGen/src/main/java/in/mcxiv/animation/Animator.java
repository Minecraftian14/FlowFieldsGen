package in.mcxiv.animation;

import in.mcxiv.threads.ThreadMan;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Animator implements Actor {

    public float fpms; // frames per ms
    public long mspf; // ms per frame

    private final TimeLine timeLine = new TimeLine();
    private final ArrayList<Actor> globalActors = new ArrayList<>();

    // Is the animation running, ie, start was called.
    private boolean isRunning = false;

    // step(delta) was called, and it's still being evaluated.
    private boolean isProcessing = false;

    public Animator() {
        this(24);
    }

    public Animator(int fps) {
        this(fps / 1000f);
    }

    public Animator(float fpms) {
        this(fpms, (long) (1L / fpms));
    }

    public Animator(float fpms, long mspf) {
        this.fpms = fpms;
        this.mspf = mspf;
        System.out.println("mspf = " + mspf);
        this.globalActors.add(this.timeLine);
    }

    public TimeLine getTimeLine() {
        return timeLine;
    }

    public Actor addActor(Actor actor) {
        globalActors.add(actor);
        return actor;
    }

    public void start() {
        isRunning = true;
        ThreadMan.getInstance().scheduleAtFixedRate(this::stepCheck, 0, mspf, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        isRunning = false;
    }

    private void stepCheck() {
        if (!isRunning) return;
        if (isProcessing) return;
        step(/* ToDo: account for ignored calls (due to still being processed) */ mspf);
    }

    @Override
    public void step(float delta) {
        isProcessing = true;
        for (int i = 0, s = globalActors.size(); i < s; i++) globalActors.get(i).step(delta);
        isProcessing = false;
    }
}
