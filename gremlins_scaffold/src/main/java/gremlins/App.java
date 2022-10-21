package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.data.JSONObject;
import java.util.Random;
import java.io.*;
import java.util.ArrayList;

public class App extends PApplet {

    /**
     * Gremlins App, the main file that generate the game. This public class is
     * extended from PApplet
     */
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;
    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();

    public String configPath;
    public boolean win;
    public boolean lose;
    public int lives;
    public int wizardCoolDown;

    /**
     * A counter to count the number of tick after wizard fire on
     */
    public int wizardCoolDownCounter;
    public int enemyCoolDown;
    public int level = 0;
    public int totalLevel;
    public PowerUp magic;

    public PImage brickwall;
    public PImage stonewall;
    public PImage wizardUp;
    public PImage blackhole;
    public PImage wizardDown;
    public PImage wizardRight;
    public PImage wizardLeft;
    public PImage gremlin;
    public PImage fireball;
    public PImage slime;
    public PImage door;
    public PImage powerup;
    /**
     * This PImage list contains four image to show the animation when wall
     * crushing.
     */
    public PImage[] crushWall;
    public int tick;
    public PShape progressbar;
    public PShape filledProgressBar;

    /**
     * the map that remember every position of the wall
     */
    public Frame fm;
    public Wizard player;
    public Door exit;
    public ArrayList<Gremlin> gremlins;
    public ArrayList<FireBall> fireballs;
    public ArrayList<Slime> slimes;
    public ArrayList<BlackHole> blackholes;

    /**
     * boolean value of whether wizard is on fire
     */
    public boolean fireOn;

    /**
     * boolean value of whether the wizard is transferred to another blackhole
     */
    public boolean transferred;

    /**
     * the index of the blackhole in Arraylist that the wizard come from.
     */
    public int originalBlackHole = 0;

    /**
     * This is the constructor method that will assign the configPath to a json file
     */
    public App() {
        this.configPath = "config.json";
    }

    /*
     * (non-Javadoc)
     * This methods will set the size (width and height) for the screen that display
     * the game.
     * 
     * @see processing.core.PApplet#settings()
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images that required for the game to run.
     * Initialise the elements such as the player, enemies and map elements.
     * parsing the live, cooldownspeed and map from conf.
     */
    public void setup() {
        frameRate(FPS);
        this.tick = 0;
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
        this.powerup = loadImage(this.getClass().getResource("powerup.png").getPath().replace("%20", " "));
        this.blackhole = loadImage(this.getClass().getResource("blackhole.png").getPath().replace("%20", " "));
        this.crushWall = new PImage[4];
        this.crushWall[0] = loadImage(
                this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
        this.crushWall[1] = loadImage(
                this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
        this.crushWall[2] = loadImage(
                this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
        this.crushWall[3] = loadImage(
                this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
        textSize(20);
        // initilize all the objects
        this.progressbar = createShape(RECT, 0, 0, 100, 5);
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.lives = Integer.parseInt(conf.get("lives").toString());
        this.totalLevel = conf.getJSONArray("levels").size();
        this.setFrame();
    }

    /**
     * Receive key pressed signal from the keyboard. This will decide the movement
     * of the wizard or reset the game.
     */
    public void keyPressed() {
        player.Released();
        if ((win || lose) && tick > 60) {
            this.level = 0;
            this.setup();
        }
        if (this.keyCode == 37) {
            if (this.player.getAdjusted()) {
                player.setSprite(this.wizardLeft);
                player.setDirection("LEFT");
            }
        } else if (this.keyCode == 38) {
            if (this.player.getAdjusted()) {
                player.setSprite(this.wizardUp);
                player.setDirection("UP");
            }
        } else if (this.keyCode == 39) {
            if (this.player.getAdjusted()) {
                player.setSprite(this.wizardRight);
                player.setDirection("RIGHT");
            }
        } else if (this.keyCode == 40) {
            if (this.player.getAdjusted()) {
                player.setSprite(this.wizardDown);
                player.setDirection("DOWN");
            }
        } else if (this.keyCode == 32) {
            if (fireOn == false) {
                FireBall b = new FireBall(player.getX(), player.getY(), player.getDirection(), this.fm);
                b.setSprite(fireball);
                fireballs.add(b);
                fireOn = true;
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
     * This method will set the map frame for specific level, assign all important
     * Gameobject to app's constructor.
     */
    public void setFrame() {
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.totalLevel = conf.getJSONArray("levels").size();
        JSONObject cur_level = conf.getJSONArray("levels").getJSONObject(this.level);
        this.fm = new Frame(cur_level);
        this.fm.parseMap();
        this.fm.setSprite(this);
        this.player = fm.getWizard();
        this.gremlins = fm.getGremlins();
        this.exit = fm.getDoor();
        this.magic = (PowerUp) fm.getPowerup();
        this.fireOn = false;
        this.transferred = false;
        this.fireballs = new ArrayList<FireBall>();
        this.slimes = new ArrayList<>();
        this.wizardCoolDown = (int) (fm.getWizardCoolDown() * 60);
        this.wizardCoolDownCounter = 0;
        this.enemyCoolDown = (int) (fm.getEnemyCoolDown() * 60);
        this.win = false;
        this.lose = false;
        this.blackholes = fm.getBlackHole();
        for (BlackHole b : this.blackholes) {
            b.setSprite(blackhole);
        }
    }

    /**
     * tick method will modifying all the movement of game objects.
     */
    public void tick() {
        this.fm.tick();
        this.player.tick();
        this.magic.tick();
        this.FireBallsTick();
        this.GremlinsTick();
        this.ProgressTick();
        this.PowerupTick();
        this.BlackHoleTick();
    }

    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        tick++;
        if (win) {
            background(191, 153, 114);
            textSize(40);
            text("YOU WIN!", (float) (WIDTH / 2) - 80, (float) HEIGHT / 2);
        } else if (lose) {
            background(224, 24, 24);
            textSize(40);
            text("GAME OVER!", (float) (WIDTH / 2) - 95, (float) HEIGHT / 2);
        } else {
            background(191, 153, 114);
            this.tick();
            this.fm.draw(this);
            this.exit.draw(this);
            this.player.draw(this);
            if (this.magic.getVisible()) {
                this.magic.draw(this);
            }
            text("Lives: ", 10, HEIGHT - BOTTOMBAR + 40);
            for (int i = 0; i < this.lives; i++) {
                image(this.wizardRight, 70 + i * 20, HEIGHT - BOTTOMBAR + 22);
            }
            text("Level: " + (level + 1) + "/" + totalLevel, 200, HEIGHT - BOTTOMBAR + 40);
            this.checkNextLevel();
        }
    }

    /**
     * This method will check the state of the game, deciding whether the player
     * win, lose, go to next level or unchanged.
     */
    public void checkNextLevel() {
        if (player.intersection(this.exit)) {
            level++;
            if (level == totalLevel) {
                level = 0;
                win = true;
                tick = 0;
            } else {
                this.setFrame();
            }
        } else if (lives <= 0) {
            level = 0;
            lose = true;
            tick = 0;
        }
    }

    /**
     * This method will deduct 1 live of the player
     */
    public void loseLives() {
        this.lives--;
    }

    /**
     * This method will modifying the movement of every fireball that released.
     */
    public void FireBallsTick() {
        if (fireballs != null) {
            for (int i = 0; i < fireballs.size(); i++) {
                FireBall temp_ball = fireballs.get(i);
                temp_ball.tick();
                temp_ball.draw(this);

                for (Gremlin g : gremlins) {
                    if (temp_ball.intersection(g)) {
                        temp_ball.setDestroyed();
                        g.reborn(player);
                    }
                }
                if (fireballs.get(i).getDestroyed()) {
                    fireballs.remove(i);
                }
            }
        }
    }

    /**
     * This method will modifying all the movement of gremlins and deciding whether
     * they will release slime.
     */
    public void GremlinsTick() {
        for (Gremlin g : gremlins) {
            g.tick();
            if (player.intersection(g)) {
                this.loseLives();
                this.setFrame();
                return;
            }
            for (int i = 0; i < g.getSlimes().size(); i++) {
                Slime temp_s = g.getSlimes().get(i);
                if (temp_s.getDestroyed()) {
                    g.getSlimes().remove(i);
                } else {
                    for (int j = 0; j < fireballs.size(); j++) {
                        FireBall temp_bal = fireballs.get(j);
                        if (temp_bal.intersection(temp_s)) {
                            fireballs.remove(j);
                            g.getSlimes().remove(i);
                        }
                    }
                    if (player.intersection(temp_s)) {
                        this.loseLives();
                        this.setFrame();
                        return;
                    }
                    temp_s.draw(this);
                }

            }
        }
    }

    /**
     * This method will tick the progress bar that inform player when they are
     * allowed to fire again.
     */
    public void ProgressTick() {
        if (fireOn) {
            this.wizardCoolDownCounter++;
            filledProgressBar = createShape(RECT, 0, 0, (wizardCoolDownCounter * 100) / wizardCoolDown, 5);
            filledProgressBar.setFill(0);
            shape(progressbar, 580, 670);
            shape(filledProgressBar, 580, 670);
            if (wizardCoolDownCounter >= wizardCoolDown) {
                wizardCoolDownCounter = 0;
                fireOn = false;
            }
        }
    }

    /**
     * This method will decide whether player have power up and if yes, modifying
     * the progress_bar for powerup.
     */
    public void PowerupTick() {
        if (this.player.intersection(magic) && magic.getVisible()) {
            magic.setAgain();
        }
        if (magic.effectOn()) {
            text("speed up", 480, 710);
            shape(progressbar, 580, 700);
            PShape progress_bar2 = createShape(RECT, 0, 0, (magic.getEffectTime() * 100) / 180, 5);
            progress_bar2.setFill(0);
            shape(progress_bar2, 580, 700);
            player.powerup();
        } else {
            player.setBack();
        }
    }

    /**
     * This method decides whether player pass through the blackhole. If yes,
     * relocating the position of wizard
     */
    public void BlackHoleTick() {
        boolean pass = false;
        for (int i = 0; i < blackholes.size(); i++) {
            blackholes.get(i).draw(this);
            if (player.intersection(blackholes.get(i))) {
                if (!transferred) {
                    pass = true;
                    originalBlackHole = i;
                    transferred = true;
                }

            }
        }
        if (pass) {
            for (int i = 0; i < blackholes.size(); i++) {
                if (i != originalBlackHole) {
                    player.x = blackholes.get(i).getX();
                    player.y = blackholes.get(i).getY();
                    pass = false;
                }
            }
        }
        for (int i = 0; i < blackholes.size(); i++) {
            if (i != originalBlackHole) {
                if (!player.intersection(blackholes.get(i))) {
                    transferred = false;
                }
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
