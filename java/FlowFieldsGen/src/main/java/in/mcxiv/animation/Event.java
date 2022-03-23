package in.mcxiv.animation;

public abstract class Event {

    public abstract String getName();

    public abstract boolean hasTimeCome(double time);

    public abstract Actor getActor();

    public void start() {
    }

    public void stop() {
    }
}
