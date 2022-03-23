function MethodNotOverridedError(name) {
  throw new Error("subclass does not override method " + name + ".");
}

class Actor {

  constructor() {
  }

  step(delta) {
    MethodNotOverridedError('step(delta)');
  }
}
