package gremlins;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.PImage;

public class Gremlin extends MoveGameObject {
    private String[] directions = new String[] { "LEFT", "RIGHT", "UP", "DOWN" };
    /**
     * map to record the inverse direction of each direction
     */
    private Map<String, String> inverseDirection = new HashMap<>();
    private int coolDown;
    private int tick;
    private PImage slimeImage;
    private ArrayList<Slime> slimes;
    private Random rand;

    public Gremlin(int x_cor, int y_cor, Frame fm) {
        super(x_cor, y_cor, fm);
        tick = 0;
        int ran = (int) (Math.random() * 4);
        this.direction = directions[ran];
        coolDown = (int) (fm.getEnemyCoolDown() * 60);
        this.slimes = new ArrayList<>();
        rand = new Random();
        inverseDirection.put("LEFT", "RIGHT");
        inverseDirection.put("RIGHT", "LEFT");
        inverseDirection.put("UP", "DOWN");
        inverseDirection.put("DOWN", "UP");

    }

    public ArrayList<Slime> getSlimes() {
        return slimes;
    }

    public void tick() {
        if ((tick) % coolDown == 0) {
            this.createSlime();
        }
        for (int i = 0; i < getSlimes().size(); i++) {
            getSlimes().get(i).tick();
            if (getSlimes().get(i).getDestroyed()) {
                getSlimes().remove(i);
            }
        }
        tick++;
        int original_x = this.x;
        int original_y = this.y;
        if (direction == "LEFT") {
            this.x -= 1;
        } else if (direction == "RIGHT") {
            this.x += 1;
        } else if (direction == "UP") {
            this.y -= 1;
        } else if (direction == "DOWN") {
            this.y += 1;
        }
        boolean collision = this.checkCollisionWall();
        if (collision) {
            this.x = original_x;
            this.y = original_y;
            this.chooseDirection();
        }
    }

    /**
     * choose a new direction when the gremlin collide with the wall
     */
    public void chooseDirection() {
        int cur_x = this.x / 20;
        int cur_y = this.y / 20;
        ArrayList<String> correct_directions = new ArrayList<>();
        GameObject up = fm.get(cur_x, cur_y - 1);
        GameObject down = fm.get(cur_x, cur_y + 1);
        GameObject left = fm.get(cur_x - 1, cur_y);
        GameObject right = fm.get(cur_x + 1, cur_y);
        if (up == null) {
            correct_directions.add("UP");
        }
        if (down == null) {
            correct_directions.add("DOWN");
        }
        if (left == null) {
            correct_directions.add("LEFT");
        }
        if (right == null) {
            correct_directions.add("RIGHT");
        }
        int size = correct_directions.size();
        if (size == 0) {
        } else if (size == 1) {
            this.direction = correct_directions.get(0);
        } else {
            int ran = (int) (Math.random() * size);
            while (correct_directions.get(ran).equals(this.direction)
                    || correct_directions.get(ran).equals(inverseDirection.get(direction))) {
                ran = (int) (Math.random() * size);
            }
            this.direction = correct_directions.get(ran);
        }
    }

    /**
     * this method is used for relocate the position that is 10 tiles apart from the
     * wizard after it collide with wizard's fireball
     * 
     * @param w - current player
     */
    public void reborn(Wizard w) {
        // gremlin reborn in another random location at least 10 tile far
        int wiz_x = w.getX() / 20;
        int wiz_y = w.getY() / 20;
        int newX = 0;
        int newY = 0;
        boolean right_position = false;
        do {
            newX = (rand.nextInt(34) + 1);
            newY = (rand.nextInt(29) + 1);
            if (newX <= wiz_x - 10 || newX >= wiz_x + 10 && newY <= wiz_y - 10 || newY >= wiz_y + 10) {
                right_position = true;
            }
        } while (right_position && fm.get(newX, newY) == null);
        this.x = newX * 20;
        this.y = newY * 20;
    }

    public void createSlime() {
        Slime s = new Slime(this.x, this.y, direction, this.fm);
        s.setSprite(slimeImage);
        slimes.add(s);
    }

    public void setSlimeImgae(PImage slime) {
        this.slimeImage = slime;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
