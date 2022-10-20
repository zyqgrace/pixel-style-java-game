package gremlins;

public class Wizard extends GameObject {
    private boolean released = false;
    private boolean collide_wall;
    private String direction;
    private int speed;
    /**
     * this boolean value is for check whether wizard is now on the middle of a tile
     */
    private boolean adjusted = false;
    private Frame fm;

    public Wizard(int x, int y, Frame fm) {
        super(x, y);
        this.fm = fm;
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
        collide_wall = this.check_collision_wall();
        if (collide_wall) {
            this.x = original_x;
            this.y = original_y;
        }
    }

    public void setDirection(String d) {
        this.direction = d;
        released = false;
    }

    public String getDirection() {
        return this.direction;
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
    public void setback() {
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
}