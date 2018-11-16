package billy.rpg.game.core.screen;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * 2s后自动消失，或按任意键使之消失
 *
 * @author liulei
 * @since 2017-05-17 20:47
 */
public class TransitionScreen extends BaseScreen {

    private long lastTime = 0;
    public TransitionScreen() {
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
        if (lastTime >= 2000) {
            onKeyUp(gameContainer, 1);
        } else {
            lastTime += delta;
        }
    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        Image transition = gameContainer.getGameAboutItem().getGameTransition();
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        // 得到缓冲区的画笔
        Graphics g = paint.getGraphics();
        g.drawImage(transition, 0, 0, null);
        g.dispose();
        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        gameContainer.getGameFrame().changeScreen(1);
    }

}
