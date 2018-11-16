package billy.rpg.game.core;

import billy.rpg.game.core.IGameCanvas;
import billy.rpg.game.core.IGameFrame;
import billy.rpg.game.core.IGameImage;
import billy.rpg.game.core.constants.GameConstant;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


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
        g.setColor(Color.yellow);
        g.setFont(GameConstant.FONT_FPS);
        g.drawString(frameRate, 530, 30);
        gameFrame.getGamePanel().setBackground(background);
    }

    @Override
    public void drawBitmap(IGameFrame gameFrame, IGameImage image, int left, int top) {
        InputStream is = image.getImage();
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bufferedImage != null) {
            Graphics g = background.getGraphics();
            g.setColor(Color.BLACK);
//        g.fillRect(left, top, bitmap.getWidth(), bitmap.getHeight());
            g.drawImage(bufferedImage, left, top, null);
            gameFrame.getGamePanel().setBackground(background);
        }
    }

    public void drawBitmap(IGameFrame gameFrame, BufferedImage image, int left, int top) {
        if (image != null) {
            Graphics g = background.getGraphics();
            g.setColor(Color.BLACK);
//        g.fillRect(left, top, bitmap.getWidth(), bitmap.getHeight());
            g.drawImage(image, left, top, null);
            gameFrame.getGamePanel().setBackground(background);
        }
    }
}
