let canvas = null;
let ctx = null;

class Display extends HTMLElement {
  constructor() {
    super();
    this.innerHTML = '<canvas class="base" id="base"></canvas>'
    canvas = document.getElementById('base');
    ctx = canvas.getContext("2d");

    this.animator = Animator.getInstance();
    this.timeLine = this.animator.getTimeLine();
    this.timeLine_sch = 0;
    this.components = [];

    let anim_pad = 2000;
    let anim_scope = 4000;

    this.timeLine_sch += anim_pad;
    this.addToTimeline(new LargeTextPane("Chill, and Watch!", 2*anim_scope), anim_scope);
    this.addToTimeline(new LargeTextPane("And don't drag...", 2*anim_scope), anim_scope);
    this.addToTimeline(new LargeTextPane("Presenting Flow Fields...", 2*anim_scope), anim_scope);

    {
      let pane = new FlowFieldsPane();
      let startEvent = new StartEvent(pane, this.timeLine_sch);
      this.timeLine.addSimpleEvent(new UIComponentEvent(c => this.addComponent(pane), this.remove, pane, startEvent));
    }

    this.animator.start();
  }

  addToTimeline(pane, length) {
    let startEvent = new StartEvent(pane, this.timeLine_sch);
    let stopEvent = new StopEvent(pane, this.timeLine_sch + length);
    this.timeLine_sch += length + /*pad*/ 1000;
    this.timeLine.addSimpleEvent(new UIComponentEvent(c => this.addComponent(pane), this.remove, pane, startEvent));
    this.timeLine.addSimpleEvent(new UIComponentEvent(c => this.addComponent(pane), this.remove, pane, stopEvent));
  }

  addComponent(mat) {
    this.add(mat);
    mat.setBounds(canvas.offsetWidth, canvas.offsetHeight);
    mat.repaint();
    return mat;
  }

  add(mat) {
    this.components.push(mat);
    if(mat instanceof LargeTextPane)
    document.getElementById('p').innerHTML = mat.label;
  }

  remove(mat) {
    let index = display.components.indexOf(mat);
    if (index !== -1)
      this.components.splice(index, 1);
    document.getElementById('p').innerHTML = '';
  }
}

window.customElements.define('display-canvas', Display);

// new LargeTextPane("Hello", 1000).repaint();
// new FlowFieldsPane().repaint();
const display = new Display();
