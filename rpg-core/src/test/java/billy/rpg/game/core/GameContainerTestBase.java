package billy.rpg.game.core;

import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.role.RoleMetaData;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * 继承该类可直接使用{@link GameContainer}对象
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 11:15:58
 * @see GameContainer
 */
public abstract class GameContainerTestBase {
    protected final Logger logger = Logger.getLogger(getClass());
    protected static GameContainer gameContainer;

    protected Fightable heroCharacter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        heroCharacter = getFightable();
        if (gameContainer != null) {
            return;
        }
        gameContainer = new GameContainer(null);
        gameContainer.load();
        gameContainer.setGameData(new GameData());
//        gameContainer.getGameData().addHeroId(1); // 添加待测试主角
    }

    protected Fightable getFightable() {
        PlayerCharacter fightable = new PlayerCharacter();
        RoleMetaData roleMetaData = new RoleMetaData();
        roleMetaData.setAttack(100);
        roleMetaData.setDefend(100);
        roleMetaData.setLevel(1);
        roleMetaData.setSpeed(30);
        fightable.setRoleMetaData(roleMetaData);
        return fightable;
    }

}
