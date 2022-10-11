package gremlins;

import processing.core.PImage;

public class Wizard extends GameObject {
    private boolean released = false;
    private boolean collide_wall;
    public String direction;
    public FireBall ball;
    public Frame fm;
    public boolean adjusted = false;

    public Wizard(int x_cor, int y_cor, Frame fm) {
        super(x_cor, y_cor);
        this.fm = fm;
        this.direction = "RIGHT";
    }

    public void tick() {
        int original_x = this.x;
        int original_y = this.y;
        if (released && this.x % 20 == 0 && this.y % 20 == 0) {
            adjusted = true;
        } else {
            adjusted = false;
            if (direction == "LEFT") {
                this.x = this.x - 2;
            } else if (direction == "RIGHT") {
                this.x = this.x + 2;
            } else if (direction == "UP") {
                this.y = this.y - 2;
            } else if (direction == "DOWN") {
                this.y = this.y + 2;
            }
        }
        collide_wall = this.check_collision_wall();
        if (collide_wall) {
            this.x = original_x;
            this.y = original_y;
        }
        if (this.ball != null) {
            if (ball.check_collision_wall()) {
                if (ball.colliding_wall.getClass() == BrickWall.class) {
                    ((BrickWall) ball.colliding_wall).crushed();
                }
                ball = null;
            }
        }
    }

    public void setDirection(String d) {
        this.direction = d;
        released = false;
    }

    public void Released() {
        released = true;
    }

    public boolean check_collision_wall() {
        int x = this.x / 20;
        int y = this.y / 20;
        GameObject Obj = fm.get(x, y);
        if (direction == "RIGHT") {
            Obj = fm.get(x + 1, y);
        } else if (direction == "LEFT") {
            Obj = fm.get(x, y);
        } else if (direction == "UP") {
            Obj = fm.get(x, y);
        } else if (direction == "DOWN") {
            Obj = fm.get(x, y + 1);
        }
        if (Obj == null) {
            return false;
        }
        return this.intersection(Obj);
    }

    public FireBall CreateFireBall(PImage b) {
        ball = new FireBall(this.getX(), this.getY(), direction, this.fm);
        ball.setSprite(b);
        return ball;
    }
}