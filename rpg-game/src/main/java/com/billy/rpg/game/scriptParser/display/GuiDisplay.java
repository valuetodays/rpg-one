package com.billy.rpg.game.scriptParser.display;

import com.billy.rpg.game.character.HeroCharacter;
import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.scriptParser.bean.MapDataLoaderBean;
import com.billy.rpg.game.scriptParser.container.GameContainer;
import com.rupeng.game.GameCore;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * show map by gui
 *  
 * @author liulei-frx
 * 
 * @since 2016-12-02 09:56:56
 */
public class GuiDisplay implements IDisplay {

    
    @Override
    public void display() {
        GameCore.setGameTitle("[" + GameContainer.getInstance().getActiveFileItem().getFileId() + "]");
        
//        printActiveMapUI();
        
        
        // print role only
      /*  for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                String text = null;
                if(posX == j && posY == i){
                    text = "|*";
                    System.out.print(text);
                } else {
                    text = "|  ";
                    System.out.print(text);
                }
//              GameCore.createNoNameTextWithPos(text, j*64, i*64);
            }
            String text = "|";
            System.out.println(text);
//          GameCore.createNoNameTextWithPos(text, width*64, i*64);
        }*/
       
    }
    
    /**
     * this method is ignored because of {@link com.rupeng.game.GameDialog#paint(Graphics)}
     */
/*    private void printActiveMapUI() {
        GameCore.removeAllNoNameText();
        GameCore.removeAllNoNameImage();
        GameCore.clear();
        
        HeroCharacter mm = FileItemsBean.active.getHero();
        int posX = mm.getPosX();
        int posY = mm.getPosY();
        
        MapBean activeMap = MapsBean.active;
        int[][] data = activeMap.getData();
        for (int i = 0; i < data.length; i++) {
            int lineLen = data[i].length;
            for (int j = 0; j < lineLen; j++) {
                String text = null;
                String tileN = null;
                if (posX == j && posY == i) {
                    text = "|  " + data[i][j] + "(*)";
                    tileN = "role1.jpg";
                } else {
                    text = "|  " + data[i][j] + "";
                    tileN = "tile" + data[i][j]  + ".jpg";
                }
                LOG.debug(text);
                GameCore.createNoNameImageWithPos(tileN, j*32, i*32);
                GameCore.createNoNameTextWithPos("-", j*32, i*32); // DO NOT use TEXT any more
            }
//-            String text = "|";  // DO NOT use TEXT any more
//-            GameCore.createNoNameTextWithPos(text, width*64, i*64);  // DO NOT use TEXT any more
        }
    }
*/
    
    public void display(Graphics g) {
//-        GameCore.removeAllNoNameText();  // not need
//-        GameCore.removeAllNoNameImage();  // not need
        GameCore.clear();

        
        // 创建一个缓冲区
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_3BYTE_BGR);
        // 得到缓冲区的画笔
        Graphics g2 = paint.getGraphics();
        
        // 将想要绘制的图形绘制到缓冲区
        HeroCharacter mm = GameContainer.getInstance().getActiveFileItem().getHero();
        int posX = mm.getPosX();
        int posY = mm.getPosY();
        Image roleFull1 = GameContainer.getInstance().getRoleItem().getRoleFull1();
        Image bgImage1 = GameContainer.getInstance().getBgImageItem().getBgImage1();
        g2.drawImage(bgImage1, 0, 0, bgImage1.getWidth(null), bgImage1.getHeight(null), null);  // draw bgImage
        
        MapDataLoaderBean activeMap = GameContainer.getInstance().getActiveMap();
        
        //////// draw layer1 start
        int[][] layer1 = activeMap.getBgLayer();
        for (int i = 0; i < layer1.length; i++) {
            int lineLen = layer1[i].length;
            for (int j = 0; j < lineLen; j++) {
                int tileNum = layer1[i][j];
                Image tileImg = GameContainer.getInstance().getTileItem().getTile(tileNum + "");
                g2.drawImage(tileImg, j*32, i*32, tileImg.getWidth(null), tileImg.getHeight(null), null);
            }
        } // end of for
        //////// draw layer1 end
        //////// draw role & npc start
        g2.drawImage(roleFull1, posX*32, posY*32, roleFull1.getWidth(null), roleFull1.getHeight(null), null);
        //////// draw role & npc end
        //////// draw layer2 start
        int[][] layer2 = activeMap.getNpcLayer();
        for (int i = 0; i < layer2.length; i++) {
            int lineLen = layer2[i].length;
            for (int j = 0; j < lineLen; j++) {
                if (isRoleOrNpc(i, j)) {  // role & npc here 
                    continue;
                }
                int tileNum = layer2[i][j];
                if (tileNum != layer1[i][j]) {
                    Image tileImg = GameContainer.getInstance().getTileItem().getTile(tileNum + "");
                    g2.drawImage(tileImg, j*32, i*32, tileImg.getWidth(null), tileImg.getHeight(null), null);
                }
            }
        } // end of for
        //////// draw layer2 end
        
        /////////// draw flag start
        int[][] flag = activeMap.getWalk();
        for (int i = 0; i < flag.length; i++) {
            int lineLen = flag[i].length;
            for (int j = 0; j < lineLen; j++) {
                int flagNum = flag[i][j];
                if (flagNum > 39 && flagNum < 100) { // Magic Number
                    Image npcImg = GameContainer.getInstance().getNpcItem().getNpc(flagNum + "");
                    g2.drawImage(npcImg, j*32, i*32, npcImg.getWidth(null), npcImg.getHeight(null), null);
                }
            }
        } // end of for
        /////////// draw flag end
        
        // 将缓冲区的图形绘制到显示面板上
        g.drawImage(paint, 0, 20, null);

//        drawDIalog(g);
        g = null;
    }
    

    /**
     * is role or npc 
     */
    private boolean isRoleOrNpc(int i, int j) {
        HeroCharacter mm = GameContainer.getInstance().getActiveFileItem().getHero();
        int posX = mm.getPosX();
        int posY = mm.getPosY();
        
        // role
        if (posX==j&&posY==i) {
            return true;
        }
        return false;
    }

    

}
