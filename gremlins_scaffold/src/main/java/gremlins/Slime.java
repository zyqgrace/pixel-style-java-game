package gremlins;

public class Slime extends GameObject {
    public String direction;
    public boolean collide;
    public Frame fm;
    private boolean destroyed;

    public Slime(int x, int y, String d, Frame fm) {
        super(x, y);
        this.direction = d;
        this.fm = fm;
    }

    public void tick() {
        if (this.check_collision_wall()) {
            destroyed = true;
        } else if (direction == "LEFT") {
            this.x -= 4;
        } else if (direction == "RIGHT") {
            this.x += 4;
        } else if (direction == "UP") {
            this.y -= 4;
        } else if (direction == "DOWN") {
            this.y += 4;
        }
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

    public boolean getDestroyed() {
        return this.destroyed;
    }

}
