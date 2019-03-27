package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerTestBase;
import billy.rpg.game.core.character.PlayerCharacter;
import org.junit.Test;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-06 16:57:46
 */
public class DefendBuffTest extends GameContainerTestBase {
    @Test
    public void testAddBuffWitDefendValue() {
        DefendEnhanceFixedValueBuff buff = new DefendEnhanceFixedValueBuff(21, 5);
        buff.setName("增加固定防御力");
        PlayerCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int defendWithBuffBefore = heroCharacter.getDefendWithBuff();
        logger.debug("before buff, defend is " + defendWithBuffBefore);
        buff.doApply(heroCharacter);
        int defendWithBuffAfter = heroCharacter.getDefendWithBuff();
        logger.debug("when buff["+buff+"], defend is " + defendWithBuffAfter);
    }

    @Test
    public void testAddDebuffWitDefendValue() {
        DefendWeakenFixedValueBuff buff = new DefendWeakenFixedValueBuff(21, 5);
        buff.setName("减少固定防御力");
        PlayerCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int defendWithBuffBefore = heroCharacter.getDefendWithBuff();
        logger.debug("before buff, defend is " + defendWithBuffBefore);
        buff.doApply(heroCharacter);
        int defendWithBuffAfter = heroCharacter.getDefendWithBuff();
        logger.debug("when buff["+buff+"], defend is " + defendWithBuffAfter);
    }

    @Test
    public void testAddBuffWithDefendPercent() {
        DefendEnhancePercentValueBuff buff = new DefendEnhancePercentValueBuff(20, 4);
        buff.setName("增加百分比防御力");
        PlayerCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int defendWithBuffBefore = heroCharacter.getDefendWithBuff();
        logger.debug("before buff, defend is " + defendWithBuffBefore);
        buff.doApply(heroCharacter);
        int defendWithBuffAfter = heroCharacter.getDefendWithBuff();
        logger.debug("when buff["+buff+"], defend is " + defendWithBuffAfter);
    }

    @Test
    public void testAddDebuffWithDefendPercent() {
        DefendWeakenPercentValueBuff buff = new DefendWeakenPercentValueBuff(20, 4);
        buff.setName("减少百分比防御力");
        PlayerCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int defendWithBuffBefore = heroCharacter.getDefendWithBuff();
        logger.debug("before buff, defend is " + defendWithBuffBefore);
        buff.doApply(heroCharacter);
        int defendWithBuffAfter = heroCharacter.getDefendWithBuff();
        logger.debug("when buff["+buff+"], defend is " + defendWithBuffAfter);
    }
}
