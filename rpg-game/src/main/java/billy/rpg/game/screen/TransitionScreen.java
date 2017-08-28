package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;

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
    public void update(long delta) {
        if (lastTime >= 2000) {
            onKeyUp(1);
        } else {
            lastTime += delta;
        }
    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        LOG.debug("draw transition.....");

        Image transition = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameTransition();
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        // 得到缓冲区的画笔
        Graphics g = paint.getGraphics();
        g.drawImage(transition, 0, 0, null);
        gameCanvas.drawBitmap(paint, 0, 0);
        g.dispose();
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        GameFrame.getInstance().changeScreen(1);
    }

}
