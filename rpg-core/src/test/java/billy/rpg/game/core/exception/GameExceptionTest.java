package billy.rpg.game.core.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author lei.liu
 * @since 2019-05-08 09:46
 */
public class GameExceptionTest {

    @Test
    public void constructor() {
        String msg = "game cannot start because of lacking of assets.";
        GameException gameException = new GameException(msg);
        Assert.assertEquals(msg, gameException.getMessage());
    }
}