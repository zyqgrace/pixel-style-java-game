package gremlins;

import processing.core.PImage;

public class BrickWall extends GameObject {
    private boolean crush = false;

    /**
     * four PImage for the animation of breaking wall
     */
    private PImage[] crush_wall;
    /**
     * the index of transission status when the brickwall get destroyed
     */
    private int transission;

    public BrickWall(int x_cor, int y_cor) {
        super(x_cor, y_cor);
    }

    public void setDestroyed(PImage[] destroyed_wall) {
        this.crush_wall = destroyed_wall;
    }

    public void tick() {
        if (crush) {
            if (transission % 4 == 0) {
                this.setSprite(crush_wall[transission / 4]);
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
