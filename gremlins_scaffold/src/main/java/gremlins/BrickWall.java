package gremlins;

import processing.core.PImage;

public class BrickWall extends GameObject {
    private boolean crush = false;

    /**
     * four PImage for the animation of breaking wall
     */
    private PImage[] crushWall;
    /**
     * the index of transission status when the brickwall get destroyed
     */
    private int transission;

    public BrickWall(int x_cor, int y_cor) {
        super(x_cor, y_cor);
    }

    public void setDestroyed(PImage[] destroyedWall) {
        this.crushWall = destroyedWall;
    }

    public void tick() {
        if (crush) {
            if (transission % 4 == 0) {
                this.setSprite(crushWall[transission / 4]);
            }

            transission++;
        }
        if (transission == 13) {
            crush = false;
        }
    }

    /**
     * @return the index of transission in the breaking wall animation
     */
    public int getTrans() {
        return this.transission;
    }

    public void crushed() {
        crush = true;
        transission = 0;
    }

    public boolean getCrushed() {
        return this.crush;
    }

}
