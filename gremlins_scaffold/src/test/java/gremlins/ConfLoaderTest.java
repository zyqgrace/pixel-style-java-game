package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfLoaderTest {
    @Test
    void testTotalLevel() {
        App a = new App();
        a.setFrame();
        a.delay(1000);
        assertEquals(2, a.total_level);
    }

    @Test
    void testMapPosition() {
        App a = new App();
        a.setFrame();
        Frame fm = a.fm;
        a.delay(1000);
        assertEquals(StoneWall.class, (fm.get(0, 0).getClass()));
    }

    @Test
    void testLivesSetUp() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        assertEquals(3, app.getLives());
    }

    @Test
    void testCheckGameStatus() {
        App app = new App();
        app.setFrame();
        app.delay(1000);
        assertFalse(app.win);
        assertFalse(app.lose);
    }

    @Test
    void testcheckLosetatus() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.loseLives();
        app.loseLives();
        app.loseLives();
        assertEquals(0, app.getLives());
    }

    @Test
    void testWizardRightLeftDirection() {
        App app = new App();
        app.setFrame();
        app.delay(1000);
        app.keyCode = 39;
        app.player.setAdjusted(true);
        app.keyPressed();
        assertEquals("RIGHT", app.player.getDirection());
        app.keyCode = 37;
        app.player.setAdjusted(true);
        app.keyPressed();
        assertEquals("LEFT", app.player.getDirection());
    }

    @Test
    void testWizardDownDirection() {
        App app = new App();
        app.setFrame();
        app.keyCode = 40;
        app.delay(1000);
        app.player.setAdjusted(true);
        app.keyPressed();
        assertEquals("DOWN", app.player.getDirection());
        app.player.tick();
        assertEquals(40, app.player.getX());
        assertEquals(20, app.player.getY());
    }

    @Test
    void testWizardUpDirection() {
        App app = new App();
        app.setFrame();
        app.delay(1000);
        app.keyCode = 38;
        app.player.setAdjusted(true);
        app.keyPressed();
        assertEquals("UP", app.player.getDirection());
        app.player.tick();
        assertEquals(40, app.player.getX());
        assertEquals(20, app.player.getY());
    }

    @Test
    void testfireball() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyCode = 32;
        app.keyPressed();
        assertEquals(1, app.fireballs.size());
    }

    @Test
    void testFireBallHitGremlin() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyCode = 32;
        app.keyPressed();
        assertEquals(1, app.fireballs.size());
        FireBall ball = app.fireballs.get(0);
        Gremlins g = app.gremlins.get(0);
        ball.setX(g.getX());
        ball.setY(g.getY());
        app.delay(1000);
        assertTrue(ball.getDestroyed());
    }

    @Test
    void testWallBreak() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        FireBall ball = new FireBall(20, 20, "LEFT", app.fm);
        ball.check_collision_wall();
        ball.setDestroyed();
        assertEquals(true, ball.getDestroyed());
    }

    @Test
    void testRestart() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.level = 1;
        app.player.x = app.exit.getX();
        app.player.y = app.exit.getY();
        app.check_next_level();
        app.tick = 61;
        app.keyPressed();
        assertEquals(0, app.level);
    }

    @Test
    void testFireBallDirectionLeft() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        FireBall ball = new FireBall(60, 20, "LEFT", app.fm);
        ball.tick();
        assertEquals(56, ball.getX());
        assertEquals(20, ball.getY());
    }

    @Test
    void testFireBallRight() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        FireBall ball = new FireBall(60, 20, "RIGHT", app.fm);
        ball.tick();
        assertEquals(64, ball.getX());
        assertEquals(20, ball.getY());
    }

    @Test
    void testReborn() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        Gremlins g = new Gremlins(20, 20, app.fm);
        int[] original_dis = new int[] { g.getX(), g.getY() };
        g.reborn(app.player);
        app.delay(1000);
        int[] new_dis = new int[] { g.getX(), g.getY() };
        assertNotEquals(original_dis, new_dis);
        original_dis = new int[] { g.getX(), g.getY() };
        g.reborn(app.player);
        app.delay(1000);
        new_dis = new int[] { g.getX(), g.getY() };
        assertNotEquals(original_dis, new_dis);
        original_dis = new int[] { g.getX(), g.getY() };
        g.reborn(app.player);
        app.delay(1000);
        new_dis = new int[] { g.getX(), g.getY() };
        assertNotEquals(original_dis, new_dis);

    }

    @Test
    void testPowerOn() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.magic.setVisible();
        app.player.x = app.magic.getX();
        app.player.y = app.magic.getY();
        assertTrue(app.player.intersection(app.magic));
        app.delay(2000);
        assertEquals(4, app.player.getSpeed());
    }

    @Test
    void testWizardHitGremlins() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.player.x = app.gremlins.get(0).getX();
        app.player.y = app.gremlins.get(0).getY();
        assertTrue(app.player.intersection(app.gremlins.get(0)));
        app.delay(1000);
        assertEquals(2, app.getLives());
    }

    @Test
    void testKeyReleased() {
        App app = new App();
        app.setFrame();
        app.delay(1000);
        app.keyReleased();
        assertTrue(app.player.getReleased());
    }

    @Test
    void testFireBallUp() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        FireBall ball = new FireBall(60, 20, "UP", app.fm);
        ball.tick();
        assertEquals(60, ball.getX());
        assertEquals(16, ball.getY());
        app.delay(1000);
        assertTrue(ball.check_collision_wall());
    }

    @Test
    void testFireBallDown() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        FireBall ball = new FireBall(20, 620, "DOWN", app.fm);
        ball.tick();
        assertEquals(20, ball.getX());
        assertEquals(624, ball.getY());
    }

    @Test
    void GremlinsDirection() {
        App app = new App();
        app.setFrame();
        app.delay(1000);
        Gremlins g = new Gremlins(20, 20, app.fm);
        g.setDirection("DOWN");
        assertEquals("DOWN", g.getDirection());
        g.tick();
        assertEquals(21, g.getY());
    }

    @Test
    void testpassblackHole() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        int x = app.blackholes.get(0).getX();
        int y = app.blackholes.get(0).getY();
        app.player.x = x;
        app.player.y = y;
        app.delay(1000);
        assertTrue(app.transferred);
    }

    @Test
    void testgetlivesequals0() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.loseLives();
        app.loseLives();
        app.loseLives();
        app.check_next_level();
        assertTrue(app.lose);
    }

    @Test
    void testWin() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.level = 1;
        app.player.x = app.exit.getX();
        app.player.y = app.exit.getY();
        app.check_next_level();
        assertTrue(app.win);
    }

    @Test
    void testDrawWin() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.win = true;
        assertTrue(app.win);
    }

    @Test
    void testGamePlayAgain() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.win = true;
        app.tick = 61;
        app.delay(1000);
        app.keyCode = 32;
        app.keyPressed();
        assertEquals(0, app.level);
    }

    @Test
    void testFireBallHitLeftWall() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        FireBall ball = new FireBall(20, 20, "LEFT", app.fm);
        ball.tick();
        assertEquals(16, ball.getX());
        assertEquals(20, ball.getY());
        assertTrue(ball.check_collision_wall());
    }

    @Test
    void testFireBallHitDownWall() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        FireBall ball = new FireBall(20, 620, "DOWN", app.fm);
        ball.tick();
        assertEquals(20, ball.getX());
        assertEquals(624, ball.getY());
        assertTrue(ball.check_collision_wall());
    }

    @Test
    void testWizardNotAdjustedDirection() {
        App app = new App();
        app.setFrame();
        app.delay(1000);
        app.keyCode = 39;
        app.keyPressed();
        app.keyCode = 37;
        app.keyPressed();
        app.keyCode = 38;
        app.keyPressed();
        app.keyCode = 40;
        app.keyPressed();
        assertFalse(app.player.getAdjusted());
    }

    @Test
    void testWizardRightDirection() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyCode = 39;
        app.player.setAdjusted(true);
        app.keyPressed();
        assertEquals("RIGHT", app.player.getDirection());
        app.player.tick();
        assertEquals(42, app.player.getX());
        assertEquals(20, app.player.getY());
        assertFalse(app.player.check_collision_wall());
    }

    @Test
    void testWizardLeftDirection() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyCode = 37;
        app.player.setAdjusted(true);
        app.keyPressed();
        assertEquals("LEFT", app.player.getDirection());
        app.player.tick();
        assertEquals(38, app.player.getX());
        assertEquals(20, app.player.getY());
        assertFalse(app.player.check_collision_wall());
    }

    @Test
    void testSlimeHitWizard() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        Gremlins g = app.gremlins.get(0);
        g.createSlime();
        assertEquals(1, g.getSlimes().size());
        app.player.x = g.getSlimes().get(0).getX();
        app.player.y = g.getSlimes().get(0).getY();
        app.delay(1000);
        assertEquals(2, app.getLives());
    }

    @Test
    void testFireBallHitSlime() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        Gremlins g = app.gremlins.get(0);
        g.createSlime();
        Slime s = g.getSlimes().get(0);
        FireBall f = new FireBall(s.getX(), s.getY(), "UP", app.fm);
        f.setSprite(app.fireball);
        app.fireballs.add(f);
        app.delay(1000);
        assertTrue(s.getDestroyed());
    }

    @Test
    void testNotFireOn() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.fire_on = true;
        app.keyCode = 32;
        app.keyPressed();
        assertEquals(0, app.fireballs.size());
    }

    @Test
    void testWizardAdjusted() {
        App app = new App();
        app.setFrame();
        app.delay(1000);
        app.player.setAdjusted(true);
        app.keyReleased();
        app.player.tick();
        assertTrue(app.player.isAdjusted());
    }
}
