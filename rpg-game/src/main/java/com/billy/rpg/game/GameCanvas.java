package com.billy.rpg.game;

import com.billy.rpg.game.constants.GameConstant;

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
    
    public void drawBitmap( BufferedImage bitmap, int left, int top) {
        Graphics g = background.getGraphics();
        
        g.setColor(Color.BLACK);
        
//        g.fillRect(left, top, bitmap.getWidth(), bitmap.getHeight());
        g.drawImage(bitmap, left, top, null);

        GameFrame.getInstance().getGamePanel().setBackground(background);
    }
    
    
	public void drawColor(Color color) {
	    Graphics g=background.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, background.getWidth(),background.getHeight()); 
		GameFrame.getInstance().getGamePanel().setBackground(background);
	}
}
