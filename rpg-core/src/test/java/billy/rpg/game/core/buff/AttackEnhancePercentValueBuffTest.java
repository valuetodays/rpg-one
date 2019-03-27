package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AttackEnhancePercentValueBuffTest extends GameContainerTestBase {
    private AttackEnhancePercentValueBuff buff;
    private int percentValue = 20;
    private int lastRounds = 4;

    @Before
    public void before() {
        super.before();
        buff = new AttackEnhancePercentValueBuff(percentValue, lastRounds);
    }

    @Test
    public void apply() {
        buff.setName("增加百分比攻击力");
        int buffAttackBefore = heroCharacter.getBuffAttack();
        logger.debug("before buff, attack is " + buffAttackBefore);
        Assert.assertEquals(0, buffAttackBefore);
        buff.doApply(heroCharacter);
        int buffAttackAfter = heroCharacter.getBuffAttack();
        logger.debug("when debuff, attack is " + buffAttackAfter);
        Assert.assertEquals(heroCharacter.getRoleMetaData().getAttack()*percentValue/100, buffAttackAfter);
    }

    @Test
    public void getBuffType() {
        Assert.assertEquals(BuffType.ENHANCE_ATTACK, buff.getBuffType());
    }

}