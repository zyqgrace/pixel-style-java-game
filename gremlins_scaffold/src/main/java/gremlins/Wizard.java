package gremlins;

import processing.core.PImage;

public class Wizard extends GameObject {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean released = false;
    private boolean collide_wall;
    public String direction;
    public FireBall ball;
    public Frame fm;

    public Wizard(int x_cor, int y_cor, Frame fm) {
        super(x_cor, y_cor);
        this.fm = fm;
        this.direction = "Right";
    }

    public void tick() {
        int original_x = this.x;
        int original_y = this.y;
        if (released && this.x % 20 == 0 && this.y % 20 == 0) {
            this.moveLeft = false;
            this.moveRight = false;
            this.moveUp = false;
            this.moveDown = false;
        }
        if (moveLeft) {
            this.x = this.x - 2;
        } else if (moveRight) {
            this.x = this.x + 2;
        } else if (moveUp) {
            this.y = this.y - 2;
        } else if (moveDown) {
            this.y = this.y + 2;
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

    public void pressLeft() {
        released = false;
        moveLeft = true;
        direction = "Left";
    }

    public void pressRight() {
        released = false;
        moveRight = true;
        direction = "Right";
    }

    public void pressUp() {
        released = false;
        moveUp = true;
        direction = "Up";
    }

    public void pressDown() {
        released = false;
        moveDown = true;
        direction = "Down";
    }

    public void Released() {
        released = true;
    }

    public boolean check_collision_wall() {
        int x = this.x / 20;
        int y = this.y / 20;
        GameObject Obj;
        if (moveRight) {
            Obj = fm.get(x + 1, y);
            if (Obj != null) {
                moveRight = false;
                return this.getX() + 20 > Obj.getX();
            }
        } else if (moveLeft) {
            Obj = fm.get(x, y);
            if (Obj != null) {
                moveLeft = false;
                return this.getX() < Obj.getX() + 20;
            }
        } else if (moveUp) {
            Obj = fm.get(x, y);
            if (Obj != null) {
                moveUp = false;
                return this.getY() < Obj.getY() + 20;
            }
        } else if (moveDown) {
            Obj = fm.get(x, y + 1);
            if (Obj != null) {
                moveDown = false;
                return this.getY() + 20 > Obj.getY();
            }
        }
        return false;
    }

    public boolean collide(GameObject Obj) {
        System.out.println("door is at" + Obj.getX() + Obj.getY());
        System.out.println("you are at" + this.getX() + this.getY());
        if (moveRight) {
            if (Obj != null) {
                return this.getX() + 20 > Obj.getX() && this.getY() == Obj.getY();
            }
        } else if (moveLeft) {
            if (Obj != null) {
                return this.getX() < Obj.getX() + 20 && this.getY() == Obj.getY();
            }
        } else if (moveUp) {
            if (Obj != null) {
                return this.getY() < Obj.getY() + 20 && this.getX() == Obj.getX();
            }
        } else if (moveDown) {
            if (Obj != null) {
                return this.getY() + 20 > Obj.getY() && this.getX() == Obj.getX();
            }
        }
        return false;
    }

    public FireBall CreateFireBall(PImage b) {
        ball = new FireBall(this.getX(), this.getY(), direction, this.fm);
        ball.setSprite(b);
        return ball;
    }
}