package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AttackWeakenFixedValueBuffTest extends GameContainerTestBase {

    private AttackWeakenFixedValueBuff buff;
    private int fixedValue = 10;
    private int lastRounds = 4;

    @Before
    public void before() {
        super.before();
        buff = new AttackWeakenFixedValueBuff(fixedValue, lastRounds);
    }

    @Test
    public void apply() throws Exception {
        AttackWeakenFixedValueBuff buff = new AttackWeakenFixedValueBuff(fixedValue, Buff.DEFAULT_ROUNDS);
        buff.setName("减少固定攻击力");
        int buffAttackBefore = heroCharacter.getBuffAttack();
        logger.debug("before buff, attack is " + buffAttackBefore);
        Assert.assertEquals(0, buffAttackBefore);
        buff.doApply(heroCharacter);
        int buffAttackAfter = heroCharacter.getBuffAttack();
        logger.debug("when buff, attack is " + buffAttackAfter);
        Assert.assertEquals(-fixedValue, buffAttackAfter);
    }

    @Test
    public void getBuffType() throws Exception {
        Assert.assertEquals(BuffType.WEAKEN_ATTACK, buff.getBuffType());
    }

}