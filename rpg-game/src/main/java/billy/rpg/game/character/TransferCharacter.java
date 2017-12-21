package billy.rpg.game.character;

import billy.rpg.game.GameFrame;
import billy.rpg.game.screen.MapScreen;

/**
 * 传送门
 */
public class TransferCharacter extends BaseCharacter {
    private long lastTime = System.currentTimeMillis();

    /**
     * 传送门的自动切换
     */
    public void move(MapScreen mapScreen) {
        long now = System.currentTimeMillis();
        if (now - lastTime < 400) {
            return;
        }
        lastTime = now;
        if (GameFrame.getInstance().getCurScreen() instanceof MapScreen) {
            increaseCurFrame();
        }

    }

}

