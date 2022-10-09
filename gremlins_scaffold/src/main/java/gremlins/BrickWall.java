package gremlins;

import processing.core.PImage;

public class BrickWall extends GameObject {
    private boolean crush = false;
    public PImage[] crush_wall;
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

    public int getTrans() {
        return this.transission;
    }

    public void crushed() {
        crush = true;
        transission = 0;
    }

}
