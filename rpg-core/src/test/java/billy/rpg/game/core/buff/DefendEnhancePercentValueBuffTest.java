package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefendEnhancePercentValueBuffTest extends GameContainerTestBase {
    private DefendEnhancePercentValueBuff buff;
    private int percentValue = 20;
    private int lastRounds = 4;

    @Before
    public void before() {
        super.before();
        buff = new DefendEnhancePercentValueBuff(percentValue, lastRounds);
    }

    @Test
    public void apply() {
        int buffDefendBefore = heroCharacter.getBuffDefend();
        logger.debug("before buff, defend is " + buffDefendBefore);
        Assert.assertEquals(0, buffDefendBefore);
        buff.doApply(heroCharacter);
        int buffDefendAfter = heroCharacter.getBuffDefend();
        logger.debug("when buff["+buff+"], defend is " + buffDefendAfter);
        Assert.assertEquals(heroCharacter.getRoleMetaData().getDefend()*percentValue/100, buffDefendAfter);
    }

    @Test
    public void getBuffType() {
        Assert.assertEquals(BuffType.ENHANCE_DEFEND, buff.getBuffType());
    }

}