package billy.rpg.game.core.character.walkable;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MapScreen;

/**
 * 传送门
 */
public class TransferWalkableCharacter extends WalkableCharacter {
    private long lastTime = System.currentTimeMillis();

    /**
     * 传送门的自动切换
     */
    @Override
    public void move(GameContainer gameContainer, MapScreen mapScreen) {
        long now = System.currentTimeMillis();
        if (now - lastTime < 400) {
            return;
        }
        lastTime = now;
        if (gameContainer.getGameFrame().getCurScreen() instanceof MapScreen) {
            increaseCurFrame();
        }

    }

}

