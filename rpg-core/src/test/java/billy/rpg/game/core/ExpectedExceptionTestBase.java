package billy.rpg.game.core;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * @author lei.liu
 * @since 2019-05-08 09:59
 */
public abstract class ExpectedExceptionTestBase {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
}
