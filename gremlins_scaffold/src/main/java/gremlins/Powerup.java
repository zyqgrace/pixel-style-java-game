package gremlins;

import java.util.Random;

public class Powerup extends GameObject {
    public int spawn;
    public boolean effect_on;
    public int effect_time;
    public Random rand;
    private boolean visible;
    public Frame fm;

    public Powerup(int x, int y, Frame fm) {
        super(x, y);
        this.fm = fm;
        rand = new Random();
        int spawn = (rand.nextInt(10 * 60) + 1);
        this.spawn = spawn;
        visible = false;
    }

    public void tick() {
        spawn++;
        if (spawn >= 10 * 60) {
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

    public void set_again() {
        effect_on = true;
        effect_time = 0;
        int spawn = (rand.nextInt(10 * 60) + 1);
        this.spawn = spawn;
        visible = false;
        int newX = (rand.nextInt(34) + 1);
        int newY = (rand.nextInt(29) + 1);
        System.out.println("nexX: " + newX + "newY" + newY);
        System.out.println(fm);
        while (fm.get(newX, newY) != null) {
            newX = (rand.nextInt(34) + 1);
            newY = (rand.nextInt(29) + 1);
        }
        this.x = newX * 20;
        this.y = newY * 20;
    }
}
