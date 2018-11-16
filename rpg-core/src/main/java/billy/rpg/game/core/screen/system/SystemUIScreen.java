package billy.rpg.game.core.screen.system;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;

import java.util.Stack;

/**
 * 在地图上按ESC，显示系统界面
 * 本界面是个占位界面，不显示任何内容，但是它是用来显示“菜单、物品、技能和系统”的
 */
public class SystemUIScreen extends BaseScreen {
    private Stack<BaseScreen> screenStack = new Stack<>();

    public SystemUIScreen() {
        MenuScreen ms = new MenuScreen(this);
        screenStack.push(ms);
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
        int n = 0;
        for (n = screenStack.size()-1; n >= 0; n--) {
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
        screenStack.push(baseScreen);
    }

    public void pop() {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
        }
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
        if (screenStack.peek() != null) {
            screenStack.peek().onKeyDown(gameContainer, key);
        }
    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (screenStack.peek() != null) {
            screenStack.peek().onKeyUp(gameContainer, key);
        }
    }

}
