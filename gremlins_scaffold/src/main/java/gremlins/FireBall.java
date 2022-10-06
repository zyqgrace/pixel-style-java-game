package gremlins;

import processing.core.PImage;

public class FireBall extends GameObject {
    public String direction;

    public FireBall(int x, int y, String d) {
        super(x, y);
        this.direction = d;
    }

    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    public void tick() {
        if (direction == "Left") {
            this.x -= 4;
        } else if (direction == "Right") {
            this.x += 4;
        } else if (direction == "Up") {
            this.y -= 4;
        } else if (direction == "Down") {
            this.y += 4;
        }
    }

}
