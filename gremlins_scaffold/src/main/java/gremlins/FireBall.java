package gremlins;

import processing.core.PImage;

public class FireBall extends GameObject {
    private String direction;
    private Frame fm;
    private GameObject colliding_wall;
    private boolean destroyed = false;

    public FireBall(int x, int y, String d, Frame fm) {
        super(x, y);
        this.direction = d;
        if (direction == null) {
            this.direction = "RIGHT";
        }
        this.fm = fm;
    }

    /**
     * @return the wall that the fireball crashed with
     */
    public GameObject getColliding_wall() {
        return colliding_wall;
    }

    public void setColliding_wall(GameObject colliding_wall) {
        this.colliding_wall = colliding_wall;
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
            if (this.getColliding_wall().getClass() == BrickWall.class) {
                ((BrickWall) this.getColliding_wall()).crushed();
            }
        } else {
            if (direction == "LEFT") {
                this.x -= 4;
            } else if (direction == "RIGHT") {
                this.x += 4;
            } else if (direction == "UP") {
                this.y -= 4;
            } else if (direction == "DOWN") {
                this.y += 4;
            }
        }
    }

    /**
     * @return whether the fireball collide with the walls
     */
    public boolean check_collision_wall() {
        int x = this.x / 20;
        int y = this.y / 20;
        GameObject Obj;
        if (direction == "RIGHT") {
            Obj = fm.get(x + 1, y);
            if (Obj != null) {
                this.setColliding_wall(Obj);
                return this.getX() + 20 > Obj.getX();
            }
        } else if (direction == "LEFT") {
            Obj = fm.get(x, y);
            if (Obj != null) {
                this.setColliding_wall(Obj);
                return this.getX() < Obj.getX() + 20;
            }
        } else if (direction == "UP") {
            Obj = fm.get(x, y);
            if (Obj != null) {
                this.setColliding_wall(Obj);
                return this.getY() < Obj.getY() + 20;
            }
        } else if (direction == "DOWN") {
            Obj = fm.get(x, y + 1);
            if (Obj != null) {
                this.setColliding_wall(Obj);
                return this.getY() + 20 > Obj.getY();
            }
        }
        return false;
    }

    public boolean getDestroyed() {
        return this.destroyed;
    }

}
