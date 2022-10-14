package gremlins;

import processing.data.JSONObject;
import java.io.*;
import java.util.ArrayList;

public class Frame {

    public JSONObject level;
    public double wizardCoolDown;
    public double enemyCoolDown;
    private GameObject[][] map = new GameObject[33][36];
    private Wizard wizard;
    private Door door;
    private ArrayList<BlackHole> blackholes;
    public GameObject powerup;
    private ArrayList<Gremlins> gremlins = new ArrayList<>();

    public Frame(JSONObject level) {
        this.level = level;
        this.blackholes = new ArrayList<>();
        this.wizardCoolDown = level.getDouble("wizard_cooldown");
        this.enemyCoolDown = level.getDouble("enemy_cooldown");
    }

    public void parseMap() {
        String layout = level.getString("layout");
        try (BufferedReader br = new BufferedReader(new FileReader(layout))) {
            String line;
            int r = 0;
            while ((line = br.readLine()) != null) {
                for (int c = 0; c < 36; c++) {
                    char temp = line.charAt(c);
                    map[r][c] = this.createObj(temp, r, c);
                }
                r++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameObject createObj(char type, int i, int j) {
        GameObject Obj = null;
        if (type == 'X') {
            Obj = new StoneWall(j * 20, i * 20);
        } else if (type == 'B') {
            Obj = new BrickWall(j * 20, i * 20);
        } else if (type == 'W') {
            this.wizard = new Wizard(j * 20, i * 20, this);
        } else if (type == 'G') {
            this.gremlins.add(new Gremlins(j * 20, i * 20, this));
        } else if (type == 'E') {
            this.door = new Door(j * 20, i * 20);
        } else if (type == 'M') {
            this.powerup = new Powerup(j * 20, i * 20, this);
        } else if (type == 'H') {
            BlackHole b = new BlackHole(j * 20, i * 20);
            this.blackholes.add(b);
        } else {
        }
        return Obj;
    }

    public void setSprite(App a) {
        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 36; j++) {
                GameObject temp = map[i][j];
                if (temp == null) {
                } else if (temp.getClass() == StoneWall.class) {
                    temp.setSprite(a.stonewall);
                } else if (temp.getClass() == BrickWall.class) {
                    temp.setSprite(a.brickwall);
                    ((BrickWall) temp).setDestroyed(a.crush_wall);
                }
            }
        }
        for (Gremlins g : this.gremlins) {
            g.setSprite(a.gremlin);
            g.slime_image = a.slime;
        }
        this.wizard.setSprite(a.wizardRight);
        this.door.setSprite(a.door);
        this.powerup.setSprite(a.powerup);
    }

    public void tick() {
        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 36; j++) {
                GameObject temp = map[i][j];
                if (temp != null) {
                    if (temp.getClass() == BrickWall.class && ((BrickWall) temp).getTrans() == 13) {
                        map[i][j] = null;
                    } else {
                        temp.tick();
                    }
                }
            }
        }
    }

    public void draw(App a) {
        for (Gremlins g : this.gremlins) {
            g.draw(a);
        }
        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 36; j++) {
                GameObject temp = map[i][j];
                if (temp == null) {
                } else {
                    temp.draw(a);
                }
            }
        }
    }

    public GameObject[][] getMap() {
        return this.map;
    }

    public GameObject get(int x, int y) {
        return map[y][x];
    }

    public Wizard getWizard() {
        return this.wizard;
    }

    public Door getDoor() {
        return this.door;
    }

    public ArrayList<BlackHole> getBlackHole() {
        return this.blackholes;
    }

    public ArrayList<Gremlins> getGremlins() {
        return this.gremlins;
    }
}
