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
            this.setSprite(crush_wall[transission]);
            transission++;
        }
        if (transission == 4) {
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
