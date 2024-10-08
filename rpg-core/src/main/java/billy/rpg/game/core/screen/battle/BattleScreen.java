package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.battle.end.BattleSuccessScreen;

import java.util.List;
import java.util.Stack;

/**
 * 战斗界面
 *
 * @author liulei@bshf360.com
 * @since 2017-06-08 15:29
 */
public class BattleScreen extends BaseScreen {
    private Stack<BaseScreen> screenStack = new Stack<>();
    private java.util.List<PlayerCharacter> playerBattleList;

    public BattleScreen(GameContainer gameContainer, final int[] metEnemyIds) {
        playerBattleList = gameContainer.getGameData().getHeroList(gameContainer);
        BattleUIScreen battleUIScreen = new BattleUIScreen(gameContainer, metEnemyIds, this, playerBattleList);
        screenStack.push(battleUIScreen);
        BattleOptionScreen battleOptionScreen = new BattleOptionScreen(battleUIScreen);
        screenStack.push(battleOptionScreen);
        gameContainer.getGameFrame().change2BattleScreen(this);
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
        int n = 0;
        for (n = screenStack.size()-1; n >= 0 ;n--) {
            BaseScreen baseScreen = screenStack.get(n);
            if (!baseScreen.isPopup()) {
                break;
            }
        }

        if (n < 0) {
            n = 0;
        }
        for (int j = n; j < screenStack.size(); j++) {
            BaseScreen baseScreen = screenStack.get(j);
            baseScreen.update(gameContainer, delta);
        }

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        int n = 0;
        for (n = screenStack.size()-1; n >= 0 ;n--) {
            BaseScreen baseScreen = screenStack.get(n);
            if (!baseScreen.isPopup()) {
                break;
            }
        }

        if (n < 0) {
            n = 0;
        }
        for (int j = n; j < screenStack.size(); j++) {
            BaseScreen baseScreen = screenStack.get(j);
            baseScreen.draw(gameContainer, desktopCanvas);
        }
    }

    public void push(BaseScreen baseScreen) {
        if (baseScreen instanceof BattleSuccessScreen) {
            screenStack.clear();
        }
        screenStack.push(baseScreen);
    }

    public void pop() {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
        }
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        screenStack.peek().onKeyUp(gameContainer, key);
    }

    public List<PlayerCharacter> getPlayerBattleList() {
        return playerBattleList;
    }
}
