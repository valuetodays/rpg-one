package billy.rpg.game.core.screen;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.IGameCanvas;
import billy.rpg.game.core.container.GameContainer;

import org.apache.log4j.Logger;

public abstract class BaseScreen {
    protected final Logger logger = Logger.getLogger(getClass());
    
    public BaseScreen() {
        logger.debug("new " + this.getClass().getSimpleName() + "()");
    }
    

    public abstract void update(GameContainer gameContainer, long delta);
    public abstract void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas);
    public void draw2(GameContainer gameContainer, IGameCanvas gameCanvas){};
    public abstract void onKeyDown(GameContainer gameContainer, int key);
    public abstract void onKeyUp(GameContainer gameContainer, int key);

    /**
     * 是否是弹出弹出窗口
     * @return true是
     */
    public boolean isPopup() {
        return false;
    }


}
