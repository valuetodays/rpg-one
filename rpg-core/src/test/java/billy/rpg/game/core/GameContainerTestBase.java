package billy.rpg.game.core;

import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.container.GameContainer;
import org.apache.log4j.Logger;
import org.junit.Before;

/**
 * 继承该类可直接使用{@link GameContainer}对象
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 11:15:58
 * @see GameContainer
 */
public abstract class GameContainerTestBase {
    protected final Logger logger = Logger.getLogger(getClass());
    protected GameContainer gameContainer;

    @Before
    public void before() {
        gameContainer = new GameContainer(null);
        gameContainer.load();
        gameContainer.setGameData(new GameData());
        gameContainer.getGameData().addHeroId(1); // 添加待测试主角
    }

    protected PlayerCharacter heroCharacter() {
        PlayerCharacter playerCharacter = new PlayerCharacter(null);
        return playerCharacter;
    }

}
