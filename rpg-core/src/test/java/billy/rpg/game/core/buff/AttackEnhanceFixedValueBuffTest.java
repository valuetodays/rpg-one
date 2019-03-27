package billy.rpg.game.core.buff;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lei.liu
 * @since 2019-03-27 18:37
 */
public class AttackEnhanceFixedValueBuffTest {
    private AttackEnhanceFixedValueBuff buff;
    private int fixedValue = 10;
    private int lastRounds = 4;

    @Before
    public void before() {
        buff = new AttackEnhanceFixedValueBuff(10, 4);
    }

    @Test
    public void testApply() {
//        buff.
//        TODO s

    }

    @Test
    public void testGetBuffType() {
        Assert.assertEquals(BuffType.ENHANCE_ATTACK, buff.getBuffType());
    }

}