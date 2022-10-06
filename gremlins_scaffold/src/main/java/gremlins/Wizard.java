package gremlins;

public class Wizard extends GameObject {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean released = false;
    private boolean collide_wall;
    public Frame fm;

    public Wizard(int x_cor, int y_cor, Frame fm) {
        super(x_cor, y_cor);
        this.fm = fm;

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
    }

    public void pressLeft() {
        released = false;
        moveLeft = true;
    }

    public void pressRight() {
        released = false;
        moveRight = true;
    }

    public void pressUp() {
        released = false;
        moveUp = true;
    }

    public void pressDown() {
        released = false;
        moveDown = true;
    }

    public void Released() {
        released = true;
    }

    public boolean check_collision_wall() {
        int x = this.x / 20;
        int y = this.y / 20;
        // System.out.println(this.getX() + " " + this.getY());
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
}