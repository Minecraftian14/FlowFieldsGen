class TimeLine extends Actor {
  constructor() {
    super();
    this.events = [];
    this.actors = [];

    this.time = 0;
  }

  addSimpleEvent(event) {
    this.events.push(event);
  }

  addEvent(actor, start, stop) {
    this.addSimpleEvent(new StartEvent(actor, start));
    this.addSimpleEvent(new StopEvent(actor, stop));
  }

  step(delta) {
    this.time += delta;
    this.stepEvents(delta);
    this.actors.forEach(actor => actor.step(delta))
  }

  stepEvents(delta) {
    let consumedEvents = [];
    for (let i = 0; i < this.events.length; i++) {
      let event = this.events[i];
      if (event.hasTimeCome(this.time)) {
        this.stepEvent(event, delta);
        consumedEvents.push(this.events.indexOf(event));
      }
    }
    consumedEvents.forEach(value => this.events.splice(value, 1));
  }

  stepEvent(event, delta) {
    switch (event.getName()) {
      case "StartEvent":
        this.actors.push(event.getActor());
        event.start();
        break;
      case "StopEvent":
        this.actors.splice(this.actors.indexOf(event.getActor()), 1);
        event.stop();
        break;
    }
  }
}
