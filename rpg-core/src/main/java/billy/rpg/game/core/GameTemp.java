package billy.rpg.game.core;

import billy.rpg.game.core.container.GameContainer;

/**
 * 游戏临时数据
 *
 * 是不是本类可以和{@link GameData}合并
 */
public final class GameTemp {
    private GameTemp() {
    }
    public static GameContainer gameContainer = null;
}

