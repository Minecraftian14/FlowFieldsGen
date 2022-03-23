class LargeTextPane extends Actor {
  constructor(text, length) {
    super();

    this.length = length;
    this.halfLength = length / 2;
    this.time = 0;
    this.transparency = 0;
    this.label = text;
    this.p_tag_lol = document.getElementById('p');
  }

  step(delta) {
    if (this.time < this.halfLength) {
      this.transparency = this.time / this.halfLength;
      this.repaint();
    } else if (this.time < this.length) {
      this.transparency = 1 - (this.time - this.halfLength) / this.halfLength;
      this.repaint();
    }
    this.time += delta;
  }

  repaint() {
    this.p_tag_lol.style.opacity = this.transparency;
  }

  setBounds(width, height) {
  }
}
