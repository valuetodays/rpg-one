package billy.rpg.game.core.screen;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.constants.ScreenCodeEnum;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.util.KeyUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 制作人信息
 */
public class ProducerScreen extends BaseScreen {

    @Override
    public void update(GameContainer gameContainer, long delta) {

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g2 = paint.getGraphics();

        final Image bgImage1 = gameContainer.getBgImageItem().getBgImage1();
        g2.drawImage(bgImage1, 0, 0, bgImage1.getWidth(null), bgImage1.getHeight(null), null);  // draw bgImage

        g2.setFont(new Font("黑体", Font.BOLD, 24));
        g2.drawString("制作人信息", 190, 140);
        g2.drawString("程序设计：Billy", 100, 200);
        g2.drawString("美    工：rpg maker XP/VX, Internet", 100, 240);
        g2.drawString("Press [Exit] to exit..", 120, 440);
        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
        g2.dispose();
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
        if (KeyUtil.isEsc(key)) {
            gameContainer.getGameFrame().changeScreen(ScreenCodeEnum.SCREEN_CODE_GAME_COVER_SCREEN);
        }
    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
    }

}
