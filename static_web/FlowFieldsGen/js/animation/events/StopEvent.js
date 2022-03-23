class StopEvent extends AnimationEvent {

  constructor(actor, stoppingTime) {
    super();
    this.actor = actor;
    this.stoppingTime = stoppingTime;
  }

  getName() {
    return "StopEvent";
  }

  hasTimeCome(time) {
    return time > this.stoppingTime;
  }

  getActor() {
    return this.actor;
  }
}
