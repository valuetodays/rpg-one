package billy.rpg.game.core.util;

import billy.rpg.game.core.ExpectedExceptionTestBase;
import billy.rpg.game.core.exception.GameException;
import org.junit.Test;

/**
 * @author lei.liu
 * @since 2019-05-08 09:51
 */
public class AssertUtilTest extends ExpectedExceptionTestBase {


    @Test
    public void assertTrue_whenFalse() {
        String msg = "error";
        expectedException.expect(GameException.class);
        expectedException.expectMessage(msg);
        AssertUtil.assertTrue(false, msg);
    }

    @Test
    public void assertTrue_whenTrue() {
        String msg = "nothing will be happened";
        AssertUtil.assertTrue(true, msg);
    }
}