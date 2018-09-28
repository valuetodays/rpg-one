package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import org.apache.log4j.Logger;

public abstract class BaseScreen {
    protected final Logger LOG = Logger.getLogger(getClass());
    
    public BaseScreen() {
        LOG.debug("new " + this.getClass().getSimpleName() + "()");
    }
    

    public abstract void update(long delta);
    public abstract void draw(GameCanvas gameCanvas);
    public abstract void onKeyDown(int key);
    public abstract void onKeyUp(int key);

    /**
     * 是否是弹出弹出窗口
     * @return true是
     */
    public boolean isPopup() {
        return false;
    }


}
