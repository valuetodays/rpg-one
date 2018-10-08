package billy.rpg.game.screen.system;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.screen.BaseScreen;

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
    public void update(long delta) {
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
        screenStack.push(baseScreen);
    }

    public void pop() {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
        }
    }

    @Override
    public void onKeyDown(int key) {
        if (screenStack.peek() != null) {
            screenStack.peek().onKeyDown(key);
        }
    }

    @Override
    public void onKeyUp(int key) {
        if (screenStack.peek() != null) {
            screenStack.peek().onKeyUp(key);
        }
    }

}
