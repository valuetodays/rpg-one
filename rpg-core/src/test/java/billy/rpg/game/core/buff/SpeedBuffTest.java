package billy.rpg.game.core.buff;

import billy.rpg.game.core.GameContainerBaseTest;
import billy.rpg.game.core.character.HeroCharacter;
import org.junit.Test;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-06 17:17:15
 */
public class SpeedBuffTest extends GameContainerBaseTest {
    @Test
    public void testAddBuffWitSpeedValue() {
        SpeedEnhanceFixedValueBuff buff = new SpeedEnhanceFixedValueBuff(21, 5);
        buff.setName("增加固定速度");
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int speedWithBuffBefore = heroCharacter.getSpeedWithBuff();
        logger.debug("before buff, speed is " + speedWithBuffBefore);
        buff.doApply(heroCharacter);
        int speedWithBuffAfter = heroCharacter.getSpeedWithBuff();
        logger.debug("when buff["+buff+"], speed is " + speedWithBuffAfter);
    }

    @Test
    public void testAddDebuffWitSpeedValue() {
        SpeedWeakenFixedValueBuff buff = new SpeedWeakenFixedValueBuff(21, 5);
        buff.setName("减少固定速度");
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int speedWithBuffBefore = heroCharacter.getSpeedWithBuff();
        logger.debug("before buff, speed is " + speedWithBuffBefore);
        buff.doApply(heroCharacter);
        int speedWithBuffAfter = heroCharacter.getSpeedWithBuff();
        logger.debug("when buff["+buff+"], speed is " + speedWithBuffAfter);
    }


    @Test
    public void testAddBuffWitSpeedPercent() {
        SpeedEnhancePercentValueBuff buff = new SpeedEnhancePercentValueBuff(21, 5);
        buff.setName("增加百分比速度");
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int speedWithBuffBefore = heroCharacter.getSpeedWithBuff();
        logger.debug("before buff, speed is " + speedWithBuffBefore);
        buff.doApply(heroCharacter);
        int speedWithBuffAfter = heroCharacter.getSpeedWithBuff();
        logger.debug("when buff["+buff+"], speed is " + speedWithBuffAfter);
    }

    @Test
    public void testAddDebuffWitSpeedPercent() {
        SpeedWeakenPercentValueBuff buff = new SpeedWeakenPercentValueBuff(21, 5);
        buff.setName("减少百分比速度");
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(0);
        int speedWithBuffBefore = heroCharacter.getSpeedWithBuff();
        logger.debug("before buff, speed is " + speedWithBuffBefore);
        buff.doApply(heroCharacter);
        int speedWithBuffAfter = heroCharacter.getSpeedWithBuff();
        logger.debug("when buff["+buff+"], speed is " + speedWithBuffAfter);
    }
}
