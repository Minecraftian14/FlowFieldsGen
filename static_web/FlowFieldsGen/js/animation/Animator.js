class Animator extends Actor {

  static instance = new Animator();

  static getInstance() {
    return this.instance;
  }

  constructor() {
    super();

    // if (Animator.instance != null)
    //   throw Error('');

    this.fps = 16;

    this.fpms = this.fps / 1000;
    this.mspf = Math.floor(1 / this.fpms);
    console.log("mspf = " + this.mspf)

    this.timeLine = new TimeLine();
    this.globalActors = [];

    this.globalActors.push(this.timeLine);

    this.isRunning = false;
    this.isProcessing = false;
  }

  getTimeLine() {
    return this.timeLine;
  }

  addActor(actor) {
    this.globalActors.add(actor);
    return actor;
  }

  start() {
    this.isRunning = true;
    ThreadMan.getInstance().scheduleAtFixedRate(this.stepCheck, this.mspf);
  }

  stop() {
    this.isRunning = false;
  }

  stepCheck() {
    if (!Animator.getInstance().isRunning) return;
    if (Animator.getInstance().isProcessing) return;
    Animator.getInstance().step(Animator.getInstance().mspf);
  }

  step(delta) {
    this.isProcessing = true;
    for (let i = 0, s = Animator.getInstance().globalActors.length; i < s; i++)
      Animator.getInstance().globalActors[i].step(delta);
    this.isProcessing = false;
  }
}
