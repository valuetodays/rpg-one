package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerBaseTest;
import billy.rpg.game.core.character.PlayerCharacter;
import org.junit.Assert;
import org.junit.Test;

/**
 * 攻击力buff与debuff测试
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 11:00:48
 */
public class AttackBuffTest extends GameContainerBaseTest {
    int fixedValue = 20;
    int percentValue = 30;

    @Test
    public void testAddBuffWithAttackValue() {
        AttackEnhanceFixedValueBuff buff = new AttackEnhanceFixedValueBuff(fixedValue, Buff.DEFAULT_ROUNDS);
        buff.setName("增加固定攻击力");
        PlayerCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int attackWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, attack is " + attackWithBuffBefore);
        buff.doApply(heroCharacter);
        int attackWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when buff, attack is " + attackWithBuffAfter);
        Assert.assertTrue(attackWithBuffBefore + fixedValue == attackWithBuffAfter);
    }

    @Test
    public void testAddBuffWithAttackPercent() {
        AttackEnhancePercentValueBuff buff = new AttackEnhancePercentValueBuff(percentValue, Buff.DEFAULT_ROUNDS);
        buff.setName("增加百分比攻击力");
        PlayerCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int attackWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, attack is " + attackWithBuffBefore);
        buff.doApply(heroCharacter);
        int attackWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when debuff, attack is " + attackWithBuffAfter);
        Assert.assertTrue(attackWithBuffBefore + attackWithBuffBefore*percentValue/100 == attackWithBuffAfter);
    }

    @Test
    public void testAddDebuffWithAttackValue() {
        AttackWeakenFixedValueBuff buff = new AttackWeakenFixedValueBuff(fixedValue, Buff.DEFAULT_ROUNDS);
        buff.setName("减少固定攻击力");
        PlayerCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int attackWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, attack is " + attackWithBuffBefore);
        buff.doApply(heroCharacter);
        int attackWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when buff, attack is " + attackWithBuffAfter);
        Assert.assertTrue(attackWithBuffBefore - fixedValue == attackWithBuffAfter);
    }

    @Test
    public void testAddDebuffWithAttackPercent() {
        AttackWeakenPercentValueBuff buff = new AttackWeakenPercentValueBuff(percentValue, Buff.DEFAULT_ROUNDS);
        buff.setName("减少百分比攻击力");
        PlayerCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int attackWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, attack is " + attackWithBuffBefore);
        buff.doApply(heroCharacter);
        int attackWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when buff, attack is " + attackWithBuffAfter);
        Assert.assertTrue(attackWithBuffBefore - attackWithBuffBefore*percentValue/100 == attackWithBuffAfter);
    }

}