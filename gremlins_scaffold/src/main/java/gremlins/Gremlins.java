package gremlins;

import java.util.Random;
import java.util.ArrayList;

import processing.core.PImage;

public class Gremlins extends GameObject {
    private String[] directions = new String[] { "Left", "Right", "Up", "Down" };
    private String direction;
    public Frame fm;
    public int cool_down;
    public int frequent;
    public int tick;
    public PImage slime_image;
    public ArrayList<Slime> slimes;

    public Gremlins(int x_cor, int y_cor, Frame fm) {
        super(x_cor, y_cor);
        this.fm = fm;
        int ran = (int) (Math.random() * 4);
        this.direction = directions[ran];
        frequent = (int) (Math.random() * 10 + 1);
        cool_down = (int) (fm.enemyCoolDown * 60);
        tick = 0;
        slimes = new ArrayList<Slime>();
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

        if (direction == "Left") {
            this.x -= 1;
        } else if (direction == "Right") {
            this.x += 1;
        } else if (direction == "Up") {
            this.y -= 1;
        } else if (direction == "Down") {
            this.y += 1;
        }
        boolean collision = this.check_collision_wall();
        if (collision) {
            this.x = original_x;
            this.y = original_y;
            int ran = (int) (Math.random() * 4);
            while (directions[ran] == this.direction) {
                ran = (int) (Math.random() * 4);
            }
            this.direction = directions[ran];
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

    public Slime createSlime() {
        Slime s = new Slime(this.x, this.y, direction, this.fm);
        s.setSprite(slime_image);
        return s;
    }

}
