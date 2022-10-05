package gremlins;

import processing.data.JSONObject;
import java.io.*;
import java.util.ArrayList;
import processing.data.JSONArray;

public class Frame {
    public char[][] location;
    public GameObject[][] map;
    public Gremlins[] gremlins;
    public App a;
    public int level;
    private int[] wizard_pos = new int[2];
    public int num_G = 0;

    public Frame(App app, int cur_level) {
        this.a = app;
        this.level = cur_level;
    }

    public void setUp() {
        JSONObject conf = processing.core.PApplet.loadJSONObject(new File(a.configPath));

        map = new GameObject[33][36];
        location = new char[33][36];
        String layout = conf.getJSONArray("levels").getJSONObject(this.level).getString("layout");
        try {
            BufferedReader br = new BufferedReader(new FileReader(layout));
            String line;
            int r = 0;
            while ((line = br.readLine()) != null) {
                for (int c = 0; c < 36; c++) {
                    location[r][c] = line.charAt(c);
                }
                r++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public GameObject createObj(int i, int j) {
        char type = this.location[i][j];
        GameObject Obj = null;
        if (type == 'X') {
            Obj = new StoneWall(j * 20, i * 20);
        } else if (type == 'B') {
            Obj = new BrickWall(j * 20, i * 20);
        } else if (type == 'W') {
            wizard_pos[0] = j;
            wizard_pos[1] = i;
        } else if (type == 'G') {
            num_G++;
        } else {
        }
        return Obj;
    }

    public void setStaticSprite(int i, int j) {
        char type = this.location[i][j];
        GameObject Obj = map[i][j];
        if (type == 'X') {
            Obj.setSprite(a.stonewall);
        } else if (type == 'B') {
            Obj.setSprite(a.brickwall);
        } else {
        }
    }

    public void setMovingSprite(int i, int j) {
        char type = this.location[i][j];

    }

    public void draw() {
        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 36; j++) {
                GameObject temp = map[i][j];
                if (temp == null) {
                } else {
                    temp.draw(this.a);
                }
            }
        }
    }

    public Gremlins[] setGremlins() {
        int count = 0;
        Gremlins[] g = new Gremlins[num_G];
        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 36; j++) {
                char type = this.location[i][j];
                if (type == 'G') {
                    g[count] = new Gremlins(j * 20, i * 20);
                    g[count].setSprite(a.gremlin);
                    count++;
                }
            }
        }
        return g;
    }

    public Wizard setWizard() {
        Wizard w = new Wizard(wizard_pos[0] * 20, wizard_pos[1] * 20);
        w.setSprite(a.wizardRight);
        return w;
    }

}
