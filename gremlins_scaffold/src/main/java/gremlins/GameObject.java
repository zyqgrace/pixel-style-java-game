package gremlins;

import processing.core.PImage;
import processing.core.PApplet;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected PImage sprite;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    public abstract void tick();

    public void draw(PApplet app) {
        app.image(this.sprite, this.x, this.y);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    /**
     * @param Obj - A game Object for checking intersection
     * @return boolean value of whether collide with the Object
     */
    public boolean intersection(GameObject Obj) {
        int self_x1 = this.x;
        int self_y1 = this.y;
        int Obj_x1 = Obj.getX();
        int Obj_y1 = Obj.getY();
        int self_x2 = self_x1 + 20;
        int self_y2 = self_y1 + 20;
        int Obj_x2 = Obj_x1 + 20;
        int Obj_y2 = Obj_y1 + 20;
        int x_L = Math.max(self_x1, Obj_x1);
        int x_R = Math.min(self_x2, Obj_x2);
        if (x_L < x_R) {
            int y_T = Math.max(self_y1, Obj_y1);
            int y_B = Math.min(self_y2, Obj_y2);
            if (y_T < y_B) {
                return true;
            }
        }
        return false;
    }
}
