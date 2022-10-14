package gremlins;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.PImage;

public class Gremlins extends GameObject {
    private String[] directions = new String[] { "LEFT", "RIGHT", "UP", "DOWN" };
    private Map<String, String> d = new HashMap<>();
    private String direction;
    public Frame fm;
    public int cool_down;
    public int tick;
    public PImage slime_image;
    public ArrayList<Slime> slimes;
    public Random rand;

    public Gremlins(int x_cor, int y_cor, Frame fm) {
        super(x_cor, y_cor);
        this.fm = fm;
        int ran = (int) (Math.random() * 4);
        this.direction = directions[ran];
        cool_down = (int) (fm.enemyCoolDown * 60);
        tick = 0;
        slimes = new ArrayList<Slime>();
        rand = new Random();
        d.put("LEFT", "RIGHT");
        d.put("RIGHT", "LEFT");
        d.put("UP", "DOWN");
        d.put("DOWN", "UP");

    }

    public String getDirection() {
        return this.direction;
    }

    public void tick() {
        if ((tick) % cool_down == 0) {
            slimes.add(this.createSlime());
        }
        for (int i = 0; i < slimes.size(); i++) {
            slimes.get(i).tick();
            if (slimes.get(i).getDestroyed()) {
                slimes.remove(i);
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
        boolean collision = this.check_collision_wall();
        if (collision) {
            this.x = original_x;
            this.y = original_y;
            this.chooseDirection();
        }
    }

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
                    || correct_directions.get(ran).equals(d.get(direction))) {
                ran = (int) (Math.random() * size);
            }
            this.direction = correct_directions.get(ran);
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

    public Slime createSlime() {
        Slime s = new Slime(this.x, this.y, direction, this.fm);
        s.setSprite(slime_image);
        return s;
    }

}
