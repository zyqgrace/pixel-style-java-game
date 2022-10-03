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
    public PImage wizardRight;
    public PImage wizardLeft;
    public PImage wizardUp;
    public PImage wizardDown;

    public FrameLoader fm;
    public Gremlins[] gremlins;
    public Wizard player;

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
        this.wizardLeft = loadImage(
                this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.wizardRight = loadImage(
                this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.wizardUp = loadImage(
                this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.wizardDown = loadImage(
                this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        // this.slime =
        // loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", "
        // "));
        // this.fireball =
        // loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20",
        // " "));
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.fm.setUp();
        this.gremlins = this.fm.setGremlins();
        this.player = this.fm.setWizard();
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
        fm.draw();
        for (int i = 0; i < gremlins.length; i++) {
            gremlins[i].draw(this);
        }
        player.tick();
        player.draw(this);
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
