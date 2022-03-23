package in.mcxiv.animation.events;

import in.mcxiv.animation.Actor;
import in.mcxiv.animation.Event;

import java.awt.*;
import java.util.function.Consumer;

public class UIComponentEvent extends Event {

    private Consumer<Component> container;
    private Consumer<Component> remover;
    private Component component;
    private Event event;

    public UIComponentEvent(Container container, Component component, Event event) {
        this(container::add, container::remove, component, event);
    }

    public UIComponentEvent(Consumer<Component> container,Consumer<Component> remover, Component component, Event event) {
        this.container = container;
        this.remover = remover;
        this.component = component;
        this.event = event;
    }

    @Override
    public String getName() {
        return event.getName();
    }

    @Override
    public boolean hasTimeCome(double time) {
        return event.hasTimeCome(time);
    }

    @Override
    public Actor getActor() {
        return event.getActor();
    }

    @Override
    public void start() {
        container.accept(component);
    }

    @Override
    public void stop() {
        remover.accept(component);
    }
}
