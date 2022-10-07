package gremlins;

public class Slime extends GameObject {
    public String direction;

    public Slime(int x, int y, String d) {
        super(x, y);
        this.direction = d;
    }

    public void tick() {
    };

}
