class StartEvent extends AnimationEvent {

  constructor(actor, startingTime) {
    super();
    this.actor = actor;
    this.startingTime = startingTime;
  }

  getName() {
    return "StartEvent";
  }

  hasTimeCome(time) {
    return time > this.startingTime;
  }

  getActor() {
    return this.actor;
  }
}
