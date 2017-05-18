package com.billy.rpg.game.screen;

import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.util.KeyUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class GameCoverScreen extends BaseScreen {

    private int arrowX = 160;
    private int f = 1; // 1:new game;  -1:continue
    private Map<Integer, Integer> map = new HashMap<>(); 
    
    public GameCoverScreen() {
        map.put(1, 320); 
        map.put(-1, 350);
    }
    
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

        Image gameCover = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameCover();
        Image gameArrow = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrow();
        Image gameBalloon = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameBalloon();
        g2.drawImage(gameCover, 0, 0, gameCover.getWidth(null), gameCover.getHeight(null), null);
        g2.drawRect(150, 315, 160, 70);
        g2.setFont(new Font("黑体", Font.BOLD, 24));
        g2.drawString("开始游戏", 190, 340);
        g2.drawString("继续游戏", 190, 370);
        g2.drawImage(gameArrow, arrowX, map.get(f), gameArrow.getWidth(null), gameArrow.getHeight(null), null);
//        g2.drawImage(gameArrow, arrowX, arrowY+28, gameArrow.getWidth(null), gameArrow.getHeight(null), null);
        g2.drawImage(gameBalloon, 220, 130, 220+32, 130+32,
        		1*32, 0, 1*32+32, 0*32+32,  
        		null);
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEnter(key)) {
            if (f == 1) {
                LOG.debug("you choose `开始游戏`");
                GameFrame.getInstance().getGameContainer().startChapter(1, 1, "1-2");
                GameFrame.getInstance().changeScreen(1);
            } else {
                // TODO show saves
            }
            
            return ;
        }
        if (KeyUtil.isUp(key)) {
            transfer();
        } else if (KeyUtil.isDown(key)) {
            transfer();
        }
    }

    
    private void transfer() {
        f *= -1;
    }



}
