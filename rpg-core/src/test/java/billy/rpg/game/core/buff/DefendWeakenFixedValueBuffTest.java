package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefendWeakenFixedValueBuffTest extends GameContainerTestBase {
    private DefendWeakenFixedValueBuff buff;
    private int fixedValue = 10;
    private int lastRounds = 4;

    @Before
    public void before() {
        super.before();
        buff = new DefendWeakenFixedValueBuff(fixedValue, lastRounds);
    }

    @Test
    public void apply() {
        buff.setName("减少固定防御力");
        int buffDefendBefore = heroCharacter.getBuffDefend();
        logger.debug("before buff, defend is " + buffDefendBefore);
        Assert.assertEquals(0, buffDefendBefore);
        buff.doApply(heroCharacter);
        int buffDefendAfter = heroCharacter.getBuffDefend();
        logger.debug("when buff["+buff+"], defend is " + buffDefendAfter);
        Assert.assertEquals(-fixedValue, buffDefendAfter);

    }

    @Test
    public void getBuffType() {
        Assert.assertEquals(BuffType.WEAKEN_DEFEND, buff.getBuffType());
    }

}