package gremlins;

public class Slime extends MoveGameObject {
    private boolean destroyed;

    public Slime(int x, int y, String d, Frame fm) {
        super(x, y, fm);
        this.direction = d;
        this.fm = fm;
    }

    public void tick() {
        if (this.checkCollisionWall()) {
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

    public boolean getDestroyed() {
        return this.destroyed;
    }

    public void setDestroyed() {
        this.destroyed = true;
    }
}
