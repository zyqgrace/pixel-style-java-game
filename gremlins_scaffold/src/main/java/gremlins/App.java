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
    public String lives;
    public double wizardCoolDown;
    public double enemyCoolDown;
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
    public PImage[] crush_wall;

    public Frame fm;
    public Wizard player;
    public ArrayList<Gremlins> gremlins;
    public ArrayList<FireBall> fireballs;

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

        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.wizardUp = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.wizardDown = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        this.wizardRight = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.wizardLeft = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        // this.slime =
        // loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", "
        // "));
        this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
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
        this.lives = conf.get("lives").toString();
        this.total_level = conf.getJSONArray("levels").size();
        JSONObject cur_level = conf.getJSONArray("levels").getJSONObject(this.level);
        this.fm = new Frame(cur_level);
        this.fm.parseMap();
        fm.setSprite(this);
        this.player = fm.getWizard();
        this.gremlins = fm.getGremlins();
        fireballs = new ArrayList<FireBall>();
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
            System.out.println("new");
            FireBall b = new FireBall(player.getX(), player.getY(), player.direction, this.fm);
            System.out.println("set");
            b.setSprite(fireball);
            System.out.println("add");
            fireballs.add(b);
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
        background(191, 153, 114);
        player.tick();
        for (Gremlins g : gremlins) {
            g.tick();
        }
        if (fireballs != null) {
            for (FireBall b : fireballs) {
                if (b == null) {
                } else {
                    b.tick();
                    b.draw(this);
                    if (b.check_collision_wall()) {
                        b = null;
                        System.out.println("shoot!");
                    }
                }
            }
        }
        this.fm.tick();
        this.fm.draw(this);
        text("Lives: ", 10, HEIGHT - BOTTOMBAR + 40);
        text("Level: " + (level + 1) + "/" + total_level, 200, HEIGHT - BOTTOMBAR + 40);
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
