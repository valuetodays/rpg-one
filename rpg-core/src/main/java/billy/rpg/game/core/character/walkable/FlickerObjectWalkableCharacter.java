package billy.rpg.game.core.character.walkable;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MapScreen;

import java.awt.image.BufferedImage;

/**
 * 闪烁的火焰、灯洞
 */
public class FlickerObjectWalkableCharacter extends WalkableCharacter {
    private long lastTime = System.currentTimeMillis();
    private BufferedImage image;

    /**
     * 闪烁物品的自动切换
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

    @Override
    public void increaseCurFrame() {
        curFrame++;
        if (curFrame == 3) {
            curFrame = 0;
        }
    }


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
