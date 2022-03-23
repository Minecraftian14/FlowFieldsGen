const IS_DEBUGGING = false;


const OPTIMISATION_FACTOR = 3;

const FORCE_POINTS_X = Math.ceil(192 / OPTIMISATION_FACTOR);
const FORCE_POINTS_Y = Math.ceil(108 / OPTIMISATION_FACTOR);

const DENSITY_FACTOR = 0.01 * OPTIMISATION_FACTOR;

const PARTICLES = 1000;
const VARIANCE = 3;
const AMPLITUDE = 2;
const COLOR = "#00000019";

const SEED = Math.floor(Math.random() * 100000000);


class FlowFieldsPane extends Actor {

  constructor() {
    super();
    this.q = 0;
    this.forceField = [];

    const simplex = new SimplexNoise(SEED);

    for (let i = 0; i < FORCE_POINTS_Y; i++) {
      let row = [];
      for (let j = 0; j < FORCE_POINTS_X; j++) {
        let vector = simplex.noise2D(i * DENSITY_FACTOR, j * DENSITY_FACTOR) * VARIANCE;
        row.push([Math.sin(vector) * AMPLITUDE, Math.cos(vector) * AMPLITUDE]);
      }
      this.forceField.push(row);
    }
    console.assert(this.forceField.length === FORCE_POINTS_Y, "" + this.forceField.length + " === " + FORCE_POINTS_Y);
    console.assert(this.forceField[0].length === FORCE_POINTS_X, "" + this.forceField[0].length + " === " + FORCE_POINTS_X);
    console.assert(this.forceField[0][0].length === 2);

    this.particles = new Array(PARTICLES)
      .fill(() => new Array(2))
      .map(value => value());
  }

  setBounds(width, height) {
    ctx.canvas.width = width;
    ctx.canvas.height = height;
    for (let i = 0; i < PARTICLES; i++) {
      this.particles[i][0] = Math.floor(Math.random() * height);
      this.particles[i][1] = Math.floor(Math.random() * width);
    }
  }

  step(delta) {
    for (let i = 0; i < PARTICLES; i++) {
      const force = this.forceField
        [Math.floor(this.particles[i][0] / this.getHeight() * FORCE_POINTS_Y)]
        [Math.floor(this.particles[i][1] / this.getWidth() * FORCE_POINTS_Y)];
      this.particles[i][0] = this.clamp(0, this.getHeight(), this.particles[i][0] + force[0]);
      this.particles[i][1] = this.clamp(0, this.getWidth(), this.particles[i][1] + force[1]);
    }
    this.repaint();
  }

  clamp(a, b, v) {
    if (v < a || v > b) return Math.random() * (b - a) + a;
    return v;
  }

  getWidth() {
    return canvas.offsetWidth;
  }

  getHeight() {
    return canvas.offsetHeight;
  }

  repaint() {
    if (IS_DEBUGGING) {
      ctx.color = "#FF0000";
      for (let i = 0; i < FORCE_POINTS_Y; i++) {
        for (let j = 0; j < FORCE_POINTS_X; j++) {
          let y = (i / FORCE_POINTS_Y * this.getHeight());
          let x = (j / FORCE_POINTS_X * this.getWidth());
          let y_r = y + this.forceField[i][j][0];
          let x_r = x + this.forceField[i][j][1];
          ctx.moveTo(x, y);
          ctx.lineTo(x_r, y_r);
          ctx.stroke();
        }
      }
    }
    ctx.fillStyle = COLOR;
    for (let i = 0; i < PARTICLES; i++)
      ctx.fillRect(Math.floor(this.particles[i][1]), Math.floor(this.particles[i][0]), 1, 1);
  }
}
