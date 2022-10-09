package gremlins;

import processing.core.PImage;

public class FireBall extends GameObject {
    public String direction;
    public Frame fm;
    public GameObject colliding_wall;
    private boolean destroyed = false;

    public FireBall(int x, int y, String d, Frame fm) {
        super(x, y);
        this.direction = d;
        this.fm = fm;
    }

    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    public void setDestroyed() {
        this.destroyed = true;
    }

    public void tick() {

        if (this.check_collision_wall()) {
            destroyed = true;
            if (this.colliding_wall.getClass() == BrickWall.class) {
                ((BrickWall) this.colliding_wall).crushed();
            }
        } else {
            if (direction == "Left") {
                this.x -= 4;
            } else if (direction == "Right") {
                this.x += 4;
            } else if (direction == "Up") {
                this.y -= 4;
            } else if (direction == "Down") {
                this.y += 4;
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

    public boolean collide(GameObject Obj) {
        if (Obj == null) {
            return false;
        }
        if (direction == "Right") {
            if (Obj != null) {
                return this.getX() + 20 > Obj.getX() && this.getY() == Obj.getY();
            }
        } else if (direction == "Left") {
            if (Obj != null) {
                return this.getX() < Obj.getX() + 20 && this.getY() == Obj.getY();
            }
        } else if (direction == "Up") {
            if (Obj != null) {
                return this.getY() < Obj.getY() + 20 && this.getX() == Obj.getX();
            }
        } else if (direction == "Down") {
            if (Obj != null) {
                return this.getY() + 20 > Obj.getY() && this.getX() == Obj.getX();
            }
        }
        if (this.getX() == Obj.getX() && this.getY() == Obj.getY()) {
            return true;
        }
        return false;
    }

    public boolean getDestroyed() {
        return this.destroyed;
    }

}
