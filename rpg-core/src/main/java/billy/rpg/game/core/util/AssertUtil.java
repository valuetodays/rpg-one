package billy.rpg.game.core.util;

import billy.rpg.game.core.exception.GameException;

public class AssertUtil {

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new GameException(message);
        }
    }
}
