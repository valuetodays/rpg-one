package com.billyrupeng.screen;

import com.billy.constants.GameConstant;
import com.billy.map.MainMap;
import com.billy.scriptParser.bean.MapDataLoaderBean;
import com.billy.scriptParser.item.ScriptItem;
import com.billyrupeng.GameCanvas;
import com.billyrupeng.KeyUtil;
import com.billyrupeng.MainFrame;
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
            MainFrame.getInstance().changeScreen(1);
        }
    }

    @Override
    public void draw(GameCanvas gameCanvas) {

        // 创建一个缓冲区
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        // 得到缓冲区的画笔
        Graphics g2 = paint.getGraphics();
        
        // 将想要绘制的图形绘制到缓冲区
        MainMap mm = MainFrame.getInstance().getGameContainer().getActiveFileItem().getMm();
        int posX = mm.getPosX();
        int posY = mm.getPosY();
        final Image roleFull1 = MainFrame.getInstance().getGameContainer().getRoleItem().getRoleFull1();
        final Image bgImage1 = MainFrame.getInstance().getGameContainer().getBgImageItem().getBgImage1();
        g2.drawImage(bgImage1, 0, 0, bgImage1.getWidth(null), bgImage1.getHeight(null), null);  // draw bgImage
        
        final MapDataLoaderBean activeMap = MainFrame.getInstance().getGameContainer().getActiveMap();
        
        //////// draw layer1 start
        final Image tileImg = MainFrame.getInstance().getGameContainer().getTileItem().getTile(activeMap.getTileId());
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

        //////// draw transfer start
        final Image transferImage = MainFrame.getInstance().getGameContainer().getSkill2ImageItems().getCurrentImage();
        g2.drawImage(transferImage, 0*32, 0*32, 0*32 + 32, 0*32 + 32,
                0, 0,
                32, 32, null);
//        LOG.debug("drawing transfer");
        //////// draw transfer end

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
                    LOG.debug("layer3---------------");
                    g2.drawImage(tileImg, i*32, j*32,
                            i*32+32, j*32+32,
                            x*32, y*32,
                            x*32+32, y*32+32,
                            null);
                }
            }
        } // end of for
        //////// draw layer3 end
        
        /////////// draw flag start TODO comment it now
        int[][] flag = activeMap.getFlag();
        for (int i = 0; i < flag.length; i++) {
            int lineLen = flag[i].length;
            for (int j = 0; j < lineLen; j++) {
                int flagNum = flag[i][j];
                if (flagNum > 39 && flagNum < 100) { // Magic Number
                    Image npcImg = MainFrame.getInstance().getGameContainer().getNpcItem().getNpc(flagNum + "");
                    g2.drawImage(npcImg, j*32, i*32, npcImg.getWidth(null), npcImg.getHeight(null), null);
                }
            }
        } // end of for
        /////////// draw flag end
        

        
        // 将缓冲区的图形绘制到显示面板上
        gameCanvas.drawBitmap(paint, 0, 0);
        MainFrame.getInstance().getGameContainer().executePrimary();
    }




    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        
        if (KeyUtil.isEsc(key)) {
            MainFrame.getInstance().changeScreen(2);
          return;
        } else if (KeyUtil.isHome(key)) {
            BaseScreen bs = new AnimationScreen(null, 2, true);
            MainFrame.getInstance().pushScreen(bs);
            return ;
        }
        
        ScriptItem active = MainFrame.getInstance().getGameContainer().getActiveFileItem();
        active.initWidthAndHeight(active.getHeight(), active.getWidth());
        active.checkTrigger();
        MainMap mainMap = active.getMm();
        MainFrame.getInstance().setTitle(mainMap.toString());
        
        if (KeyUtil.isLeft(key)) {
            mainMap.decreaseX();
        } else if (KeyUtil.isRight(key)) {
            mainMap.increaseX();
        } else if (KeyUtil.isUp(key)) {
            mainMap.decreaseY();
        } else if (KeyUtil.isDown(key)) {
            mainMap.increaseY();
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
