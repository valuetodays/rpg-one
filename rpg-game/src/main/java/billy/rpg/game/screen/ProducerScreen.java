package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 制作人信息
 */
public class ProducerScreen extends BaseScreen {

    @Override
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g2 = paint.getGraphics();

        final Image bgImage1 = GameFrame.getInstance().getGameContainer().getBgImageItem().getBgImage1();
        g2.drawImage(bgImage1, 0, 0, bgImage1.getWidth(null), bgImage1.getHeight(null), null);  // draw bgImage

        g2.setFont(new Font("黑体", Font.BOLD, 24));
        g2.drawString("制作人信息", 190, 140);
        g2.drawString("程序设计：Billy", 100, 200);
        g2.drawString("美    工：rpg maker XP/VX, Internet", 100, 240);
        gameCanvas.drawBitmap(paint, 0, 0);
        g2.dispose();
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        GameFrame.getInstance().changeScreen(9);
    }

}
