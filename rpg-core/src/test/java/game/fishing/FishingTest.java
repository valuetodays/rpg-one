package game.fishing;

import org.junit.Test;

import billy.rpg.game.core.fishing.Fishing;

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