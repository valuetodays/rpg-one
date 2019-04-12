package game;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-31 13:13
 */
public class InstanceOfTest {
    @Test
    public void test1() {
        String a = null;
        Assert.assertFalse(a instanceof String);
    }
}
