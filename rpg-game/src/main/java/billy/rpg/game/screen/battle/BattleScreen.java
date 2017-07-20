package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.scriptParser.item.ScriptItem;
import billy.rpg.resource.role.RoleMetaData;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author liulei@bshf360.com
 * @since 2017-06-08 15:29
 */
public class BattleScreen extends BaseScreen {
    private Stack<BaseScreen> screenStack = new Stack<>();
    protected java.util.List<HeroBattle> heroBattleList;

    public BattleScreen(final int[] metMonsterIds) {
        heroBattleList = new ArrayList<>();
        java.util.List<Integer> heroIds = ScriptItem.getHeroIds();
        for (int i = 0; i < heroIds.size(); i++) {
            RoleMetaData heroRole = GameFrame.getInstance().getGameContainer().getHeroRoleOf(heroIds.get(i));
            HeroBattle e = new HeroBattle();
            e.setLeft(200 + i*150 + 10);
            e.setTop(10*32);
            e.setWidth(heroRole.getImage().getWidth());
            e.setHeight(heroRole.getImage().getHeight());
            e.setRoleMetaData(heroRole);

            heroBattleList.add(e);
        }

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

}
