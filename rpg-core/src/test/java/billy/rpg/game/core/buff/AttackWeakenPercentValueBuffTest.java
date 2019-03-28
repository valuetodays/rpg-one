package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AttackWeakenPercentValueBuffTest extends GameContainerTestBase {
    private AttackWeakenPercentValueBuff buff;
    private int percentValue = 20;
    private int lastRounds = 4;

    @Before
    public void before() {
        super.before();
        buff = new AttackWeakenPercentValueBuff(percentValue, lastRounds);
    }

    @Test
    public void apply() {
        int buffAttackBefore = heroCharacter.getBuffAttack();
        logger.debug("before buff, attack is " + buffAttackBefore);
        Assert.assertEquals(0, buffAttackBefore);
        buff.doApply(heroCharacter);
        int buffAttackAfter = heroCharacter.getBuffAttack();
        logger.debug("when buff, attack is " + buffAttackAfter);
        Assert.assertEquals(-heroCharacter.getRoleMetaData().getAttack()*percentValue/100, buffAttackAfter);
    }

    @Test
    public void getBuffType() {
        Assert.assertEquals(BuffType.WEAKEN_ATTACK, buff.getBuffType());
    }

}