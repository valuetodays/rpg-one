package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerBaseTest;
import billy.rpg.game.core.character.HeroCharacter;
import org.junit.Test;

/**
 * 攻击力buff与debuff测试
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 11:00:48
 */
public class AttackBuffTest extends GameContainerBaseTest {

    @Test
    public void testAddBuffWithValue() {
        AttackBuff buff = new AttackBuff();
        buff.setName("增加固定攻击力");
        buff.setValue(21);
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int attackWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, attack is " + attackWithBuffBefore);
        buff.doApply(heroCharacter);
        int attackWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when buff, attack is " + attackWithBuffAfter);
    }

    @Test
    public void testAddDebuffWithValue() {
        AttackBuff buff = new AttackBuff();
        buff.setName("减少固定攻击力");
        buff.setValue(-32);
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int attackWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, attack is " + attackWithBuffBefore);
        buff.doApply(heroCharacter);
        int attackWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when debuff, attack is " + attackWithBuffAfter);
    }

    @Test
    public void testAddBuffWithValuePercent() {
        AttackBuff buff = new AttackBuff();
        buff.setName("增加百分比攻击力");
        buff.setValuePercent(18);
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int attackWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, attack is " + attackWithBuffBefore);
        buff.doApply(heroCharacter);
        int attackWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when buff, attack is " + attackWithBuffAfter);
    }

    @Test
    public void testAddDebuffWithValuePercent() {
        AttackBuff buff = new AttackBuff();
        buff.setName("减少百分比攻击力");
        buff.setValuePercent(-21);
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int attackWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, attack is " + attackWithBuffBefore);
        buff.doApply(heroCharacter);
        int attackWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when buff, attack is " + attackWithBuffAfter);
    }

}