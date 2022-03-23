const REMOVE_TRANSPARENCY_MASK = ~(0xFF << 24);

class SimpleMutableFloatingColor {
  constructor(r, g, b, a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }

  intize(float) {
    return Math.floor(float * 255 + 0.5);
  }

  floatize(int) {
    return ((int - 0.5) / 255);
  }

  setTransparency(transparency) {
    console.assert(transparency >= 0 && transparency <= 1, "transparency >= 0 && transparency <= 1 but " + transparency);
    this.a = transparency;
  }

  getRed() {
    return this.intize(this.r);
  }

  getGreen() {
    return this.intize(this.g);
  }

  getBlue() {
    return this.intize(this.b);
  }

  getAlpha() {
    return this.intize(this.a);
  }

  getRGB() {
    // TODO: Is there a faster version?
    return "#" + (this.getRed()).toString(16) + (this.getGreen()).toString(16) + (this.getBlue()).toString(16);
  }

  equals(color) {
    return this.a === color.a;
  }
}
