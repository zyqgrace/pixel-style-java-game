
package gremlins;

import java.util.Random;

public class PowerUp extends StaticGameObject {
    private Frame fm;
    private Random rand;
    /**
     * a integer of time that powerup will show up
     */
    private int spawn;
    private int tick;

    /**
     * the remaining time of wizard collect the power up.
     */
    private int effectTime;
    private boolean effectOn;
    private boolean visible;

    public PowerUp(int x, int y, Frame fm) {
        super(x, y);
        this.fm = fm;
        rand = new Random();
        this.spawn = (rand.nextInt(3 * 60) + 8 * 60);
        this.tick = 0;
        effectOn = false;
        visible = false;
    }

    public void setVisible() {
        visible = true;
    }

    public void tick() {
        tick++;
        if (tick >= spawn) {
            visible = true;
        }
        if (effectOn) {
            effectTime++;
            if (effectTime >= 3 * 60) {
                effectOn = false;
            }
        }
    }

    public boolean getVisible() {
        return visible;
    }

    public boolean effectOn() {
        return effectOn;
    }

    public int getEffectTime() {
        return this.effectTime;
    }

    /**
     * assign a new position for power up after being collected
     */
    public void setAgain() {
        effectOn = true;
        effectTime = 0;
        tick = 0;
        this.spawn = (rand.nextInt(3 * 60) + 8 * 60);
        System.out.println(this.spawn);
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
