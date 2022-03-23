package in.mcxiv.animation.events;

import in.mcxiv.animation.Actor;
import in.mcxiv.animation.Event;

public class StopEvent extends Event {
    private final Actor actor;
    private final double stoppingTime;

    public StopEvent(Actor actor, double stoppingTime) {
        this.actor = actor;
        this.stoppingTime = stoppingTime;
    }

    @Override
    public String getName() {
        return "StopEvent";
    }

    @Override
    public boolean hasTimeCome(double time) {
        return time > stoppingTime;
    }

    @Override
    public Actor getActor() {
        return actor;
    }
}
