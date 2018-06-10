package billy.rpg.game.fishing;

import org.junit.Test;

/**
 * @author liulei-home
 * @since 2018-06-10 09:31
 */
public class FishingTest {
    @Test
    public void doFish() throws Exception {
        Fishing f = new Fishing();
        for (int i = 0; i < 100; i++) {
            f.doFish();
        }
    }

}