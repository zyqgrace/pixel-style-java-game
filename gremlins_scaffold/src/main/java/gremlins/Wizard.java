package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class Wizard extends GameObject {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;

    public Wizard(int x_cor, int y_cor) {
        super(x_cor, y_cor);
    }

    public void tick() {
        if (moveLeft) {
            this.x--;
        } else if (moveRight) {
            this.x++;
        } else if (moveUp) {
            this.y--;
        } else if (moveDown) {
            this.y++;
        }
    };

    public void pressLeft() {
        moveLeft = true;
    }

    public void pressRight() {
        moveRight = true;
    }

    public void pressUp() {
        moveUp = true;
    }

    public void pressDown() {
        moveDown = true;
    }

    public void Released() {
        moveLeft = false;
        moveRight = false;
        moveUp = false;
        moveDown = false;
    }

}