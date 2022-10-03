package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import java.util.Random;
import java.io.*;

public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();

    public String configPath;

    public PImage brickwall;
    public PImage stonewall;
    public PImage gremlin;

    public FrameLoader fm;
    public Gremlins[] gremlins;
    // public Wizard wizard;

    public App() {
        this.configPath = "config.json";
        this.fm = new FrameLoader(this);
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
        this.stonewall = loadImage(
                this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(
                this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(
                this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        // this.slime =
        // loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", "
        // "));
        // this.fireball =
        // loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20",
        // " "));
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.fm.setMap();
        this.gremlins = this.fm.setGremlins();
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {

    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased() {

    }

    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        background(191, 153, 114);
        fm.draw();
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
