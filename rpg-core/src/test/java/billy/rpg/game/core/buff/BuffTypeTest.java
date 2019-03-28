package billy.rpg.game.core.buff;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author lei.liu
 * @since 2019-03-28 11:45
 */
public class BuffTypeTest {

    @Test
    public void ENHANCE_ATTACK_isOpposite() {
        Assert.assertTrue(BuffType.ENHANCE_ATTACK.isOpposite(BuffType.WEAKEN_ATTACK));
        Assert.assertFalse(BuffType.ENHANCE_ATTACK.isOpposite(BuffType.ENHANCE_ATTACK));
        Assert.assertFalse(BuffType.ENHANCE_ATTACK.isOpposite(BuffType.WEAKEN_DEFEND));
        Assert.assertFalse(BuffType.ENHANCE_ATTACK.isOpposite(BuffType.WEAKEN_SPEED));
    }

    @Test
    public void ENHANCE_ATTACK_getOpposite() {
        Assert.assertEquals(BuffType.WEAKEN_ATTACK, BuffType.ENHANCE_ATTACK.getOpposite());
    }

    @Test
    public void ENHANCE_DEFEND_isOpposite() {
        Assert.assertTrue(BuffType.ENHANCE_DEFEND.isOpposite(BuffType.WEAKEN_DEFEND));
        Assert.assertFalse(BuffType.ENHANCE_DEFEND.isOpposite(BuffType.ENHANCE_DEFEND));
        Assert.assertFalse(BuffType.ENHANCE_DEFEND.isOpposite(BuffType.WEAKEN_ATTACK));
        Assert.assertFalse(BuffType.ENHANCE_DEFEND.isOpposite(BuffType.WEAKEN_SPEED));
    }

    @Test
    public void ENHANCE_DEFEND_getOpposite() {
        Assert.assertEquals(BuffType.WEAKEN_DEFEND, BuffType.ENHANCE_DEFEND.getOpposite());
    }

    @Test
    public void ENHANCE_SPEED_isOpposite() {
        Assert.assertTrue(BuffType.ENHANCE_SPEED.isOpposite(BuffType.WEAKEN_SPEED));
        Assert.assertFalse(BuffType.ENHANCE_SPEED.isOpposite(BuffType.ENHANCE_SPEED));
        Assert.assertFalse(BuffType.ENHANCE_SPEED.isOpposite(BuffType.WEAKEN_ATTACK));
        Assert.assertFalse(BuffType.ENHANCE_SPEED.isOpposite(BuffType.WEAKEN_DEFEND));
    }

    @Test
    public void ENHANCE_SPEED_getOpposite() {
        Assert.assertEquals(BuffType.WEAKEN_SPEED, BuffType.ENHANCE_SPEED.getOpposite());
    }

}