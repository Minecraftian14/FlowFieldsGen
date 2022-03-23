package in.mcxiv.animation.events;

import in.mcxiv.animation.Actor;
import in.mcxiv.animation.Event;

public class StartEvent extends Event {

    private final Actor actor;
    private final double startingTime;

    public StartEvent(Actor actor, double startingTime) {
        this.actor = actor;
        this.startingTime = startingTime;
    }

    @Override
    public String getName() {
        return "StartEvent";
    }

    @Override
    public boolean hasTimeCome(double time) {
        return time > startingTime;
    }

    @Override
    public Actor getActor() {
        return actor;
    }
}
