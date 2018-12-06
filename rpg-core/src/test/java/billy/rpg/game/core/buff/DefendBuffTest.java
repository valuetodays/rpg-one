package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerBaseTest;
import billy.rpg.game.core.character.HeroCharacter;
import org.junit.Test;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-06 16:57:46
 */
public class DefendBuffTest extends GameContainerBaseTest {
    @Test
    public void testAddBuffWitDefendValue() {
        DefendValueEnhanceBuff buff = new DefendValueEnhanceBuff(21, 5);
        buff.setName("增加固定防御力");
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int defendWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, defend is " + defendWithBuffBefore);
        buff.doApply(heroCharacter);
        int defendkWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when buff, defend is " + defendkWithBuffAfter);
    }

    @Test
    public void testAddDebuffWitDefendValue() {
        DefendValueWeakenBuff buff = new DefendValueWeakenBuff(21, 5);
        buff.setName("减少固定防御力");
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int defendWithBuffBefore = heroCharacter.getAttackWithBuff();
        logger.debug("before buff, defend is " + defendWithBuffBefore);
        buff.doApply(heroCharacter);
        int defendkWithBuffAfter = heroCharacter.getAttackWithBuff();
        logger.debug("when buff, defend is " + defendkWithBuffAfter);
    }
}
