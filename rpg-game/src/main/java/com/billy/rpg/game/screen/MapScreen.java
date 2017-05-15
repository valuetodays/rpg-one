package com.billy.rpg.game.screen;

import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.character.Hero;
import com.billy.rpg.game.util.KeyUtil;
import com.billy.rpg.game.scriptParser.bean.MapDataLoaderBean;
import com.billy.rpg.game.scriptParser.item.ScriptItem;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;


public class MapScreen extends BaseScreen {
    private static Logger LOG = Logger.getLogger(MapScreen.class);
    
    public MapScreen() {
//        AnimationTimer at = new AnimationTimer(30);
//        at.start();
        
    }
    
    @Override
    public void update(long delta) {
        if (delta > 3000) {
            LOG.debug("change screen");
            GameFrame.getInstance().changeScreen(1);
        }
    }

    /**
     *
     * TODO 应该先显示地图，再显示scenename
     * @param gameCanvas gameCanvas
     */
    @Override
    public void draw(GameCanvas gameCanvas) {

        // 创建一个缓冲区
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);

        ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveFileItem();
        active.checkTrigger(); // 检查触发器

        // 得到缓冲区的画笔
        Graphics g2 = paint.getGraphics();
        
        // 将想要绘制的图形绘制到缓冲区
        Hero mm = GameFrame.getInstance().getGameContainer().getActiveFileItem().getHero();
        int posX = mm.getPosX();
        int posY = mm.getPosY();
        final Image transferImage = GameFrame.getInstance().getGameContainer().getSkill2ImageItems().getCurrentImage();
        final Image roleFull1 = GameFrame.getInstance().getGameContainer().getRoleItem().getRoleFull1();
        final Image bgImage1 = GameFrame.getInstance().getGameContainer().getBgImageItem().getBgImage1();
        g2.drawImage(bgImage1, 0, 0, bgImage1.getWidth(null), bgImage1.getHeight(null), null);  // draw bgImage
        
        final MapDataLoaderBean activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();
        
        //////// draw layer1 start
        final Image tileImg = GameFrame.getInstance().getGameContainer().getTileItem().getTile(activeMap.getTileId());
        final int[][] layer1 = activeMap.getLayer1();
        for (int i = 0; i < activeMap.getWidth(); i++) {
            for (int j = 0; j < activeMap.getHeight(); j++) {
                int tileNum = layer1[i][j];
                if (tileNum != -1) {
                    int y = tileNum % 100;
                    int x = tileNum / 100;
                    //LOG.debug("layer1---------------");
                    g2.drawImage(tileImg, i*32, j*32,
                            i*32+32, j*32+32,
                            x*32, y*32,
                            x*32+32, y*32+32,
                            null);
                }
            }
        } // end of for
        //////// draw layer1 end
        //////// draw role & npc start
        g2.drawImage(roleFull1, posX*32, posY*32, posX*32 + 32, posY*32 + 32,
                mm.getCurFrame()*32, mm.getDirection()*32,
                mm.getCurFrame()*32 + 32, mm.getDirection()*32 + 32, null);
        
        //////// draw role & npc end

        //////// draw layer2 start
        int[][] layer2 = activeMap.getLayer2();
        for (int i = 0; i < activeMap.getWidth(); i++) {
            for (int j = 0; j < activeMap.getHeight(); j++) {
                int tileNum = layer2[i][j];
                if (tileNum != -1 && tileNum != layer1[i][j]) {
                    int y = tileNum % 100;
                    int x = tileNum / 100;
                    //LOG.debug("layer2---------------");
                    g2.drawImage(tileImg, i*32, j*32,
                            i*32+32, j*32+32,
                            x*32, y*32,
                            x*32+32, y*32+32,
                            null);
                }
            }
        } // end of for
        //////// draw layer2 end

        //////// draw layer3 start
        final int[][] layer3 = activeMap.getLayer3();
        for (int i = 0; i < activeMap.getWidth(); i++) {
            for (int j = 0; j < activeMap.getHeight(); j++) {
                int tileNum = layer3[i][j];
                if (tileNum != -1 && tileNum != layer2[i][j] && tileNum != layer1[i][j]) {
                    int y = tileNum % 100;
                    int x = tileNum / 100;
//                    LOG.debug("layer3---------------");
                    g2.drawImage(tileImg, i*32, j*32,
                            i*32+32, j*32+32,
                            x*32, y*32,
                            x*32+32, y*32+32,
                            null);
                }
            }
        } // end of for
        //////// draw layer3 end

        //////// draw event start
        final int[][] eventLayer = activeMap.getEvent();
        for (int i = 0; i < activeMap.getWidth(); i++) {
            for (int j = 0; j < activeMap.getHeight(); j++) {
                int tileNum = eventLayer[i][j];
                if (tileNum == 255) { // transfer
                    g2.drawImage(transferImage, i*32, j*32,
                            i*32 + 32, j*32 + 32,
                            0, 0,
                            32, 32, null);
                }
            }
        } // end of for
        //////// draw event end

        // 将缓冲区的图形绘制到显示面板上
        gameCanvas.drawBitmap(paint, 0, 0);
        GameFrame.getInstance().getGameContainer().executePrimary();
    }


    @Override
    public void onKeyDown(int key) {    }


    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            GameFrame.getInstance().changeScreen(2);
            return;
        } else if (KeyUtil.isHome(key)) {
            BaseScreen bs = new AnimationScreen(null, 2, true);
            GameFrame.getInstance().pushScreen(bs);
            return ;
        }

        ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveFileItem();
        active.toCheckTrigger(); // 设置下一步要检查触发器
        Hero hero = active.getHero();
        GameFrame.getInstance().setTitle(hero.toString());

        if (KeyUtil.isLeft(key)) {
            hero.decreaseX();
        } else if (KeyUtil.isRight(key)) {
            hero.increaseX();
        } else if (KeyUtil.isUp(key)) {
            hero.decreaseY();
        } else if (KeyUtil.isDown(key)) {
            hero.increaseY();
        }
    }

    @Override
    public boolean isPopup() {
        return false;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

}
