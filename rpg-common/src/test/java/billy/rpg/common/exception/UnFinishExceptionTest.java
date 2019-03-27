package billy.rpg.common.exception;

import org.junit.Test;

/**
 * @author lei.liu@datatist.com
 * @since 2019-03-19 13:04
 */
public class UnFinishExceptionTest {

    @Test(expected = UnFinishException.class)
    public void testConstructor() {
        throw new UnFinishException();
    }

    @Test(expected = UnFinishException.class)
    public void testConstructorString() {
        throw new UnFinishException("todo");
    }

}