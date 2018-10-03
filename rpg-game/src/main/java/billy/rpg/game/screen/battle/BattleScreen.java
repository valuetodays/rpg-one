package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.ex.fightable.HeroFightable;
import billy.rpg.game.screen.BaseScreen;

import java.util.List;
import java.util.Stack;

/**
 *
 * @author liulei@bshf360.com
 * @since 2017-06-08 15:29
 */
public class BattleScreen extends BaseScreen {
    private Stack<BaseScreen> screenStack = new Stack<>();
    private java.util.List<HeroFightable> heroBattleList = GameFrame.getInstance().getGameData().getHeroBattleList();

    public BattleScreen(final int[] metMonsterIds) {
        BattleUIScreen battleUIScreen = new BattleUIScreen(metMonsterIds, this, heroBattleList);
        screenStack.push(battleUIScreen);
        BattleOptionScreen battleOptionScreen = new BattleOptionScreen(battleUIScreen);
        screenStack.push(battleOptionScreen);

        GameFrame.getInstance().change2BattleScreen(this);
    }

    @Override
    public void update(long delta) {
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
            baseScreen.update(delta);
        }

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
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
            baseScreen.draw(gameCanvas);
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
    public void onKeyDown(int key) {
    }

    @Override
    public void onKeyUp(int key) {
        screenStack.peek().onKeyUp(key);
    }

    public List<HeroFightable> getHeroBattleList() {
        return heroBattleList;
    }
}
