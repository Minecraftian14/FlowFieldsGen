class UIComponentEvent extends AnimationEvent {

  constructor(container, remover, component, event) {
    super();
    this.container = container;
    this.remover = remover;
    this.component = component;
    this.event = event;
  }

  getName() {
    return this.event.getName();
  }

  hasTimeCome(time) {
    return this.event.hasTimeCome(time);
  }

  getActor() {
    return this.event.getActor();
  }

  start() {
    this.container(this.component);
  }

  stop() {
    this.remover(this.component);
  }
}
