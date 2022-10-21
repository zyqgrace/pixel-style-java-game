package gremlins;

public abstract class MoveGameObject extends GameObject {
    protected String direction;
    protected Frame fm;

    public MoveGameObject(int x, int y, Frame fm) {
        super(x, y);
        this.fm = fm;
    }

    public boolean checkCollisionWall() {
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

    public String getDirection() {
        return this.direction;
    }
}
