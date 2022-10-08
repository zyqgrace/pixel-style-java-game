package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.Random;
import java.io.*;
import java.util.ArrayList;

public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;
    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();

    public String configPath;
    public boolean win;
    public int lives;
    public int wizardCoolDown;
    public int enemyCoolDown;
    public int level = 0;
    public int total_level;

    public PImage brickwall;
    public PImage stonewall;
    public PImage wizardUp;
    public PImage wizardDown;
    public PImage wizardRight;
    public PImage wizardLeft;
    public PImage gremlin;
    public PImage fireball;
    public PImage slime;
    public PImage door;
    public PImage[] crush_wall;
    public int wizard_cooldown;
    public int tick;

    public Frame fm;
    public Wizard player;
    public ArrayList<Gremlins> gremlins;
    public ArrayList<FireBall> fireballs;
    public ArrayList<Slime> slimes;
    public Door exit;

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the
     * player, enemies and map elements.
     */
    public void setup() {
        frameRate(FPS);
        tick = 0;
        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.wizardUp = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.wizardDown = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        this.wizardRight = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.wizardLeft = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        this.door = loadImage(this.getClass().getResource("door.png").getPath().replace("%20", " "));
        this.crush_wall = new PImage[4];
        this.crush_wall[0] = loadImage(
                this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
        this.crush_wall[1] = loadImage(
                this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
        this.crush_wall[2] = loadImage(
                this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
        this.crush_wall[3] = loadImage(
                this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
        textSize(20);
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.lives = Integer.parseInt(conf.get("lives").toString());
        this.total_level = conf.getJSONArray("levels").size();
        JSONObject cur_level = conf.getJSONArray("levels").getJSONObject(this.level);
        this.fm = new Frame(cur_level);
        this.fm.parseMap();
        fm.setSprite(this);
        this.player = fm.getWizard();
        this.gremlins = fm.getGremlins();
        this.exit = fm.getDoor();
        fireballs = new ArrayList<FireBall>();
        slimes = new ArrayList<>();
        this.wizardCoolDown = (int) (fm.wizardCoolDown * 60);
        this.enemyCoolDown = (int) (fm.enemyCoolDown * 60);
        this.win = false;
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {
        if (this.keyCode == 37) {
            player.setSprite(this.wizardLeft);
            player.pressLeft();
        } else if (this.keyCode == 38) {
            player.setSprite(this.wizardUp);
            player.pressUp();
        } else if (this.keyCode == 39) {
            player.setSprite(this.wizardRight);
            player.pressRight();
        } else if (this.keyCode == 40) {
            player.setSprite(this.wizardDown);
            player.pressDown();
        } else if (this.keyCode == 32) {
            if (this.wizard_cooldown == 0) {
                FireBall b = new FireBall(player.getX(), player.getY(), player.direction, this.fm);
                b.setSprite(fireball);
                fireballs.add(b);
                this.wizard_cooldown++;
            }
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased() {
        player.Released();
    }

    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        if (check_win()) {
            this.level++;
            this.setup();
        }
        tick++;
        background(191, 153, 114);
        if (this.wizard_cooldown > this.wizardCoolDown) {
            this.wizard_cooldown = 0;
        } else if (this.wizard_cooldown > 0) {
            this.wizard_cooldown++;
        }
        player.tick();
        for (Gremlins g : gremlins) {
            g.tick();
            if (player.collide(g)) {
                lives--;
                this.reset();
            }
            for (Slime s : g.slimes) {
                s.draw(this);
            }
        }

        this.fm.tick();
        this.exit.tick();
        this.fm.draw(this);
        this.exit.draw(this);
        if (fireballs != null) {
            for (int i = 0; i < fireballs.size(); i++) {
                fireballs.get(i).tick();
                fireballs.get(i).draw(this);
                if (fireballs.get(i).getDestroyed()) {
                    fireballs.remove(i);
                }
            }
        }
        text("Lives: ", 10, HEIGHT - BOTTOMBAR + 40);
        for (int i = 0; i < lives; i++) {
            image(this.wizardRight, 70 + i * 20, HEIGHT - BOTTOMBAR + 22);
        }
        text("Level: " + (level + 1) + "/" + total_level, 200, HEIGHT - BOTTOMBAR + 40);
    }

    public boolean check_win() {
        if (player.collide(this.exit)) {
            return true;
        }
        return false;
    }

    public void reset() {
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.total_level = conf.getJSONArray("levels").size();
        JSONObject cur_level = conf.getJSONArray("levels").getJSONObject(this.level);
        this.fm = new Frame(cur_level);
        this.fm.parseMap();
        fm.setSprite(this);
        this.player = fm.getWizard();
        this.gremlins = fm.getGremlins();
        this.exit = fm.getDoor();
        fireballs = new ArrayList<FireBall>();
        slimes = new ArrayList<>();
        this.wizardCoolDown = (int) (fm.wizardCoolDown * 60);
        this.enemyCoolDown = (int) (fm.enemyCoolDown * 60);
        this.win = false;
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
