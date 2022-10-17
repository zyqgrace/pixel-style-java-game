package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfLoaderTest {
    @Test
    void testTotalLevel() {
        App a = new App();
        a.setFrame();
        assertEquals(2, a.total_level);
    }

    @Test
    void testMapPosition() {
        App a = new App();
        a.setFrame();
        Frame fm = a.fm;
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
    void testcheckgamestatus() {
        App app = new App();
        app.setFrame();
        assertFalse(app.win);
        assertFalse(app.lose);
    }

    @Test
    void testcheckLosetatus() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.loseLives();
        app.loseLives();
        app.loseLives();
        assertEquals(0, app.getLives());
    }

    @Test
    void testWizardUpDirection() {
        App app = new App();
        app.setFrame();
        app.keyCode = 38;
        app.player.setAdjusted(true);
        app.keyPressed();
        assertEquals("UP", app.player.getDirection());
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
        app.player.setAdjusted(true);
        app.keyPressed();
        assertEquals("DOWN", app.player.getDirection());
    }

    @Test
    void testfireball() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.keyCode = 32;
        app.player.setAdjusted(true);
        app.keyPressed();
        assertEquals(1, app.fireballs.size());
    }

    @Test
    void testWallBreak() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        FireBall ball = new FireBall(20, 20, "LEFT", app.fm);
        ball.check_collision_wall();
        ball.setDestroyed();
        assertEquals(true, ball.getDestroyed());
    }

    @Test
    void testWinning() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.level = 1;
        app.player.x = app.exit.getX();
        app.player.y = app.exit.getY();
        app.check_next_level();
        assertTrue(app.win);

    }

    @Test
    void testDirectionLeft() {
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
        app.setFrame();
        Gremlins g = new Gremlins(20, 20, app.fm);
        int x = g.getX();
        int y = g.getY();
        g.reborn(app.player);
        assertTrue(x != g.getX());
        assertTrue(y != g.getY());
        x = g.getX();
        y = g.getY();
        g.reborn(app.player);
        assertTrue(x != g.getX());
        assertTrue(y != g.getY());
    }

    @Test
    void testPowerson() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.player.x = app.magic.getX();
        app.player.y = app.magic.getY();
        app.draw();
        assertTrue(app.magic.effectOn());
        assertEquals(4, app.player.getSpeed());
    }
}
