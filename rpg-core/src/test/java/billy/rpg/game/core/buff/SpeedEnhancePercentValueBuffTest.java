package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lei.liu
 * @since 2019-03-28 09:54
 */
public class SpeedEnhancePercentValueBuffTest extends GameContainerTestBase {

    private SpeedEnhancePercentValueBuff buff;
    private int percentValue = 20;
    private int lastRounds = 4;

    @Before
    public void before() {
        super.before();
        buff = new SpeedEnhancePercentValueBuff(percentValue, lastRounds);
    }

    @Test
    public void apply() {
        int buffSpeedBefore = heroCharacter.getBuffSpeed();
        logger.debug("before buff, speed is " + buffSpeedBefore);
        Assert.assertEquals(0, buffSpeedBefore);
        buff.doApply(heroCharacter);
        int buffSpeedAfter = heroCharacter.getBuffSpeed();
        logger.debug("when buff["+buff+"], speed is " + buffSpeedAfter);
        Assert.assertEquals(heroCharacter.getRoleMetaData().getSpeed()*percentValue/100, buffSpeedAfter);
    }

    @Test
    public void getBuffType() throws Exception {
        Assert.assertEquals(BuffType.ENHANCE_SPEED, buff.getBuffType());
    }

}