class ThreadMan {

  static instance = new ThreadMan();

  static getInstance() {
    return this.instance;
  }

  constructor() {
    this.processors = 1;
  }

  getProcessors() {
    return this.processors;
  }

  executor() {
    return null;
  }

  scheduler() {
    return null;
  }

  submit(task) {
    return new Promise((resolve, reject) => {
      task();
    });
  }

  scheduleAtFixedRate(command, period) {
    let wrapped_command = () => this.__actHelper(command);

    function doChunk() {
      wrapped_command();
      setTimeout(doChunk, period);
    }

    doChunk();
  }

  __actHelper(command) {
    if (this.isRunning) return;
    this.isRunning = true;
    new Promise(resolve => {
      command();
      this.isRunning = false;
    });
  }
}

// ThreadMan.instance.scheduleAtFixedRate(() => {
//   console.log(".")
// }, 1000);

