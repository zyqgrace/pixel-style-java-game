package gremlins;

public class Wizard extends GameObject {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean released = false;

    public Wizard(int x_cor, int y_cor) {
        super(x_cor, y_cor);
    }

    public void tick() {
        if (released) {
            this.check_stop();
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
    };

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

    public void check_stop() {
        if (moveRight) {
            if (this.x % 20 == 0) {
                moveRight = false;
            }
        } else if (moveLeft) {
            if (this.x % 20 == 0) {
                moveLeft = false;
            }
        } else if (moveUp) {
            if (this.y % 20 == 0) {
                moveUp = false;
            }
        } else if (moveDown) {
            if (this.y % 20 == 0) {
                moveDown = false;
            }
        }
    }

    public boolean check_collide_wall() {
        if (moveRight) {
            if (this.x % 20 == 0) {
                moveRight = false;
            }
        } else if (moveLeft) {
            if (this.x % 20 == 0) {
                moveLeft = false;
            }
        } else if (moveUp) {
            if (this.y % 20 == 0) {
                moveUp = false;
            }
        } else if (moveDown) {
            if (this.y % 20 == 0) {
                moveDown = false;
            }
        }
        return false;
    }

}