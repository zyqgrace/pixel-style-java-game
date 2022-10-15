package gremlins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfLoaderTest {
    private App a;
    private Frame fm;

    @BeforeEach
    void setup() {
        a = new App();
        a.setFrame();
        fm = a.fm;
    }

    @Test
    void testTotalLevel() {
        assertEquals(2, a.total_level);
    }

    @Test
    void testMapPosition() {
        assertEquals(StoneWall.class, (fm.get(0, 0).getClass()));
    }
}
