package billy.rpg.game.core;

import billy.rpg.game.core.constants.GameConstant;

import java.awt.*;
import java.awt.image.BufferedImage;


public class GameCanvas extends Canvas { 
    private static final long serialVersionUID = 1L;

    private BufferedImage background;

    public GameCanvas() {
        background = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
    }
    
    public void drawBitmap(IGameFrame gameFrame, BufferedImage bitmap, int left, int top) {
        Graphics g = background.getGraphics();
        g.setColor(Color.BLACK);
        
//        g.fillRect(left, top, bitmap.getWidth(), bitmap.getHeight());
        g.drawImage(bitmap, left, top, null);
        gameFrame.getGamePanel().setBackground(background);
    }

    public void drawFPS(IGameFrame gameFrame, String frameRate) {
        Graphics g = background.getGraphics();
        g.setColor(Color.yellow);
        g.setFont(GameConstant.FONT_FPS);
        g.drawString(frameRate, 530, 30);
        gameFrame.getGamePanel().setBackground(background);
    }
}
