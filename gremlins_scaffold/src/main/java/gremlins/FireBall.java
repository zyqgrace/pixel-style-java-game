package gremlins;

import processing.core.PImage;

public class FireBall extends GameObject {
    public String direction;
    public Frame fm;
    public GameObject colliding_wall;

    public FireBall(int x, int y, String d, Frame fm) {
        super(x, y);
        this.direction = d;
        this.fm = fm;
    }

    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    public void tick() {
        if (direction == "Left") {
            this.x -= 4;
        } else if (direction == "Right") {
            this.x += 4;
        } else if (direction == "Up") {
            this.y -= 4;
        } else if (direction == "Down") {
            this.y += 4;
        }
        if (this.check_collision_wall()) {
            if (this.colliding_wall.getClass() == BrickWall.class) {
                ((BrickWall) this.colliding_wall).crushed();
            }
        }
    }

    public boolean check_collision_wall() {
        int x = this.x / 20;
        int y = this.y / 20;
        GameObject Obj;
        if (direction == "Right") {
            Obj = fm.get(x + 1, y);
            if (Obj != null) {
                this.colliding_wall = Obj;
                return this.getX() + 20 > Obj.getX();
            }
        } else if (direction == "Left") {
            Obj = fm.get(x, y);
            if (Obj != null) {
                this.colliding_wall = Obj;
                return this.getX() < Obj.getX() + 20;
            }
        } else if (direction == "Up") {
            Obj = fm.get(x, y);
            if (Obj != null) {
                this.colliding_wall = Obj;
                return this.getY() < Obj.getY() + 20;
            }
        } else if (direction == "Down") {
            Obj = fm.get(x, y + 1);
            if (Obj != null) {
                this.colliding_wall = Obj;
                return this.getY() + 20 > Obj.getY();
            }
        }
        return false;
    }

}
