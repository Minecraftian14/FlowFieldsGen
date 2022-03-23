package in.mcxiv.animation;

import in.mcxiv.animation.events.StartEvent;
import in.mcxiv.animation.events.StopEvent;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class TimeLine implements Actor {

    private final ArrayList<Event> events = new ArrayList<>();
    private final ArrayList<Actor> actors = new ArrayList<>();

    private double time = 0;

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addEvent(Actor actor, double start, double stop) {
        addEvent(new StartEvent(actor, start));
        addEvent(new StopEvent(actor, stop));
    }

    @Override
    public void step(float delta) {
        time += delta;
        stepEvents(delta);
        for (Actor actor : actors) actor.step(delta);
    }

    private void stepEvents(float delta) {
        ArrayDeque<Event> consumedEvents = new ArrayDeque<>();
        for (Event event : events)
            if (event.hasTimeCome(time)) {
                stepEvent(event, delta);
                consumedEvents.add(event);
            }
        events.removeAll(consumedEvents); // TODO: welp, check if it's not removing two values of the same object? We might be reusing events in future.
    }

    private void stepEvent(Event event, float delta) {
        switch (event.getName()) {
            case "StartEvent":
                actors.add(event.getActor());
                event.start();
                break;
            case "StopEvent":
                actors.remove(event.getActor());
                event.stop();
                break;
        }
    }
}
