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

    public int getY() {
        return this.y;
    }

    public boolean check_collision(GameObject Obj) {
        return false;
    }

}
