package billy.rpg.game.core;

import billy.rpg.game.core.platform.image.IGameImage;
import billy.rpg.game.core.constants.GameConstant;

import java.awt.*;
import java.awt.image.BufferedImage;


public class DesktopCanvas extends Canvas implements IGameCanvas {
    private static final long serialVersionUID = 1L;

    private BufferedImage background;

    public DesktopCanvas() {
        background = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
    }

    public void drawFPS(IGameFrame gameFrame, String frameRate) {
        Graphics g = background.getGraphics();
        g.setColor(gameFrame.getFPSColor());
        g.setFont(gameFrame.getFPSFont());
        g.drawString(frameRate, 530, 30);
        gameFrame.getGamePanel().setBackground(background);
    }

    @Override
    public void drawBitmap(IGameFrame gameFrame, IGameImage image, int left, int top) {
        BufferedImage bufferedImage = (BufferedImage)image.getRealImageObject();

        drawBitmap(gameFrame, bufferedImage, left, top);
    }

    public void drawBitmap(IGameFrame gameFrame, BufferedImage image, int left, int top) {
        if (image != null) {
            Graphics g = background.getGraphics();
            g.setColor(Color.BLACK);
            g.drawImage(image, left, top, null);
            g.dispose();
            gameFrame.getGamePanel().setBackground(background); // TODO 应该移出去
        }
    }
}
