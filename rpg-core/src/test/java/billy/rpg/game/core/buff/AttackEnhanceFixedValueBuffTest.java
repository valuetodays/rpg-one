package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lei.liu
 * @since 2019-03-27 18:37
 */
public class AttackEnhanceFixedValueBuffTest extends GameContainerTestBase {
    private AttackEnhanceFixedValueBuff buff;
    private int fixedValue = 10;
    private int lastRounds = 4;

    @Before
    public void before() {
        super.before();
        buff = new AttackEnhanceFixedValueBuff(fixedValue, lastRounds);
    }

    @Test
    public void apply() {
        buff.setName("增加固定攻击力");

        int buffAttackBefore = heroCharacter.getBuffAttack();
        logger.debug("before buff, attack is " + buffAttackBefore);
        Assert.assertEquals(0, buffAttackBefore);
        buff.doApply(heroCharacter);
        int buffAttackAfter = heroCharacter.getBuffAttack();
        logger.debug("when buff, attack is " + buffAttackAfter);
        Assert.assertEquals(fixedValue, buffAttackAfter);
    }

    @Test
    public void getBuffType() {
        Assert.assertEquals(BuffType.ENHANCE_ATTACK, buff.getBuffType());
    }

}