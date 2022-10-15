package gremlins;

import java.util.Random;

public class Powerup extends GameObject {
    private Frame fm;
    private Random rand;
    private int spawn;
    private int tick;
    private int effect_time;
    private boolean effect_on;
    private boolean visible;

    public Powerup(int x, int y, Frame fm) {
        super(x, y);
        this.fm = fm;
        rand = new Random();
        this.spawn = (rand.nextInt(3 * 60) + 8 * 60);
        this.tick = 0;
        visible = false;

    }

    public void tick() {
        tick++;
        if (tick >= spawn) {
            visible = true;
        }
        if (effect_on) {
            effect_time++;
            if (effect_time >= 3 * 60) {
                effect_on = false;
            }
        }
    }

    public boolean getVisible() {
        return visible;
    }

    public boolean effectOn() {
        return effect_on;
    }

    public int getEffect_time() {
        return this.effect_time;
    }

    public void set_again() {
        effect_on = true;
        effect_time = 0;
        tick = 0;
        this.spawn = (rand.nextInt(3 * 60) + 8 * 60);
        visible = false;
        int newX = (rand.nextInt(34) + 1);
        int newY = (rand.nextInt(29) + 1);
        while (fm.get(newX, newY) != null) {
            newX = (rand.nextInt(34) + 1);
            newY = (rand.nextInt(29) + 1);
        }
        this.x = newX * 20;
        this.y = newY * 20;
    }
}
