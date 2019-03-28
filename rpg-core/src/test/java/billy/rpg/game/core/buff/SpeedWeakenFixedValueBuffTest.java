package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lei.liu
 * @since 2019-03-28 10:00
 */
public class SpeedWeakenFixedValueBuffTest extends GameContainerTestBase {
    private SpeedWeakenFixedValueBuff buff;
    private int fixedValue = 10;
    private int lastRounds = 4;

    @Before
    public void before() {
        super.before();
        buff = new SpeedWeakenFixedValueBuff(fixedValue, lastRounds);
    }

    @Test
    public void apply() {
        int buffSpeedBefore = heroCharacter.getBuffSpeed();
        logger.debug("before buff, speed is " + buffSpeedBefore);
        Assert.assertEquals(0, buffSpeedBefore);
        buff.doApply(heroCharacter);
        int buffSpeedAfter = heroCharacter.getBuffSpeed();
        logger.debug("when buff["+buff+"], speed is " + buffSpeedAfter);
        Assert.assertEquals(-fixedValue, buffSpeedAfter);
    }

    @Test
    public void getBuffType() throws Exception {
        Assert.assertEquals(BuffType.WEAKEN_SPEED, buff.getBuffType());
    }

}