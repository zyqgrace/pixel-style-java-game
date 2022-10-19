package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.data.JSONObject;
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
    public boolean lose;
    private int lives;
    public int wizardCoolDown;
    public int wizardCoolDown_counter;
    public int enemyCoolDown;
    public int level = 0;
    public int total_level;
    public Powerup magic;

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
    public PImage[] crush_wall;
    public int tick;
    public PShape progress_bar;
    public PShape filled_progress_bar;

    public Frame fm;
    public Wizard player;
    public Door exit;
    public ArrayList<Gremlins> gremlins;
    public ArrayList<FireBall> fireballs;
    public ArrayList<Slime> slimes;
    public ArrayList<BlackHole> blackholes;
    public boolean fire_on;
    public boolean transferred;
    public int from = 0;

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
        // initilize all the objects
        this.progress_bar = createShape(RECT, 0, 0, 100, 5);
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.lives = Integer.parseInt(conf.get("lives").toString());
        this.total_level = conf.getJSONArray("levels").size();
        this.setFrame();
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {
        player.Released();
        if ((win || lose) && tick > 60) {
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
            if (fire_on == false) {
                FireBall b = new FireBall(player.getX(), player.getY(), player.getDirection(), this.fm);
                b.setSprite(fireball);
                fireballs.add(b);
                fire_on = true;
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
            this.fm.tick();
            this.player.tick();
            this.magic.tick();
            background(191, 153, 114);
            if (fireballs != null) {
                for (int i = 0; i < fireballs.size(); i++) {
                    FireBall temp_ball = fireballs.get(i);
                    temp_ball.tick();
                    temp_ball.draw(this);

                    for (Gremlins g : gremlins) {
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
            for (Gremlins g : gremlins) {
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
            if (fire_on) {
                this.wizardCoolDown_counter++;
                filled_progress_bar = createShape(RECT, 0, 0, (wizardCoolDown_counter * 100) / wizardCoolDown, 5);
                filled_progress_bar.setFill(0);
                shape(progress_bar, 580, 670);
                shape(filled_progress_bar, 580, 670);
                if (wizardCoolDown_counter >= wizardCoolDown) {
                    wizardCoolDown_counter = 0;
                    fire_on = false;
                }
            }
            if (this.player.intersection(magic)) {
                magic.set_again();
            }
            if (magic.effectOn()) {
                text("speed up", 480, 710);
                shape(progress_bar, 580, 700);
                PShape progress_bar2 = createShape(RECT, 0, 0, (magic.getEffect_time() * 100) / 180, 5);
                progress_bar2.setFill(0);
                shape(progress_bar2, 580, 700);

                player.powerup();
            } else {
                player.setback();
            }
            this.fm.draw(this);
            this.exit.draw(this);
            boolean pass = false;
            for (int i = 0; i < blackholes.size(); i++) {
                blackholes.get(i).draw(this);
                if (player.intersection(blackholes.get(i))) {
                    if (!transferred) {
                        pass = true;
                        from = i;
                        transferred = true;
                    }

                }
            }
            if (pass) {
                for (int i = 0; i < blackholes.size(); i++) {
                    if (i != from) {
                        player.x = blackholes.get(i).getX();
                        player.y = blackholes.get(i).getY();
                        pass = false;
                    }
                }
            }
            for (int i = 0; i < blackholes.size(); i++) {
                if (i != from) {
                    if (!player.intersection(blackholes.get(i))) {
                        transferred = false;
                    }
                }
            }

            this.player.draw(this);
            if (this.magic.getVisible()) {
                this.magic.draw(this);
            }
            text("Lives: ", 10, HEIGHT - BOTTOMBAR + 40);
            for (int i = 0; i < getLives(); i++) {
                image(this.wizardRight, 70 + i * 20, HEIGHT - BOTTOMBAR + 22);
            }
            text("Level: " + (level + 1) + "/" + total_level, 200, HEIGHT - BOTTOMBAR + 40);
            this.check_next_level();
        }
    }

    public void check_next_level() {
        System.out.println(lives);
        if (player.intersection(this.exit)) {
            level++;
            if (level == total_level) {
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

    public int getLives() {
        return lives;
    }

    public void loseLives() {
        this.lives--;
    }

    public void setFrame() {
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.total_level = conf.getJSONArray("levels").size();
        JSONObject cur_level = conf.getJSONArray("levels").getJSONObject(this.level);
        this.fm = new Frame(cur_level);
        this.fm.parseMap();
        fm.setSprite(this);
        this.player = fm.getWizard();
        this.gremlins = fm.getGremlins();
        this.exit = fm.getDoor();
        this.magic = (Powerup) fm.getPowerup();
        fire_on = false;
        this.transferred = false;
        fireballs = new ArrayList<FireBall>();
        slimes = new ArrayList<>();
        this.wizardCoolDown = (int) (fm.getWizardCoolDown() * 60);
        this.wizardCoolDown_counter = 0;
        this.enemyCoolDown = (int) (fm.getEnemyCoolDown() * 60);
        this.win = false;
        this.lose = false;
        this.blackholes = fm.getBlackHole();
        for (BlackHole b : this.blackholes) {
            b.setSprite(blackhole);
        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
