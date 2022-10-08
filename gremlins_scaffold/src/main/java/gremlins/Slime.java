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
        } else if (direction == "Left") {
            this.x -= 4;
        } else if (direction == "Right") {
            this.x += 4;
        } else if (direction == "Up") {
            this.y -= 4;
        } else if (direction == "Down") {
            this.y += 4;
        }
    }

    public boolean check_collision_wall() {
        int x = this.x / 20;
        int y = this.y / 20;
        GameObject Obj;
        if (direction == "Right") {
            Obj = fm.get(x + 1, y);
            if (Obj != null) {
                return this.getX() + 20 > Obj.getX();
            }
        } else if (direction == "Left") {
            Obj = fm.get(x, y);
            if (Obj != null) {
                return this.getX() < Obj.getX() + 20;
            }
        } else if (direction == "Up") {
            Obj = fm.get(x, y);
            if (Obj != null) {
                return this.getY() < Obj.getY() + 20;
            }
        } else if (direction == "Down") {
            Obj = fm.get(x, y + 1);
            if (Obj != null) {
                return this.getY() + 20 > Obj.getY();
            }
        }
        return false;
    }

    public boolean getDestroyed() {
        return this.destroyed;
    }

}
