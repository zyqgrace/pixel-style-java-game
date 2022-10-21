package gremlins;

public class Wizard extends MoveGameObject {
    private boolean released = false;
    private boolean collideWall;
    private int speed;
    /**
     * this boolean value is for check whether wizard is now on the middle of a tile
     */
    private boolean adjusted = false;

    public Wizard(int x, int y, Frame fm) {
        super(x, y, fm);
        this.direction = null;
        this.speed = 2;
    }

    public boolean isAdjusted() {
        return adjusted;
    }

    public void setAdjusted(boolean adjusted) {
        this.adjusted = adjusted;
    }

    public void tick() {
        int original_x = this.x;
        int original_y = this.y;
        int i = 0;
        while (i < speed) {
            if (released && this.x % 20 == 0 && this.y % 20 == 0) {
                setAdjusted(true);
            } else {
                setAdjusted(false);
                if (direction == "LEFT") {
                    this.x = this.x - 1;
                } else if (direction == "RIGHT") {
                    this.x = this.x + 1;
                } else if (direction == "UP") {
                    this.y = this.y - 1;
                } else if (direction == "DOWN") {
                    this.y = this.y + 1;
                }
            }
            i++;
        }
        collideWall = this.checkCollisionWall();
        if (collideWall) {
            this.x = original_x;
            this.y = original_y;
        }
    }

    public void setDirection(String d) {
        this.direction = d;
        released = false;
    }

    public boolean getAdjusted() {
        return this.isAdjusted();
    }

    public void powerup() {
        speed = 4;
    }

    /**
     * set back to the orginal speed after power up time finish
     */
    public void setBack() {
        speed = 2;
    }

    public void Released() {
        released = true;
    }

    public boolean getReleased() {
        return this.released;
    }

    public int getSpeed() {
        return this.speed;
    }
}