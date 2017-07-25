package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.BoxCharacter;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.character.TransferCharacter;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.item.ScriptItem;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.box.BoxImageLoader;
import billy.rpg.resource.map.MapMetaData;
import billy.rpg.resource.npc.NPCImageLoader;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;


public class MapScreen extends BaseScreen {
    private static Logger LOG = Logger.getLogger(MapScreen.class);
    
    public MapScreen() {
    }
    
    @Override
    public void update(long delta) {
        CmdProcessor cmdProcessor = GameFrame.getInstance().getGameContainer().getActiveFileItem().getCmdProcessor();
        if (cmdProcessor != null) {
            cmdProcessor.update();
        }
    }

    /**
     * @param gameCanvas gameCanvas
     */
    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g2 = paint.getGraphics();
        
        HeroCharacter hero = GameFrame.getInstance().getGameContainer().getActiveFileItem().getHero();
        int posX = hero.getPosX();
        int posY = hero.getPosY();
        GameFrame.getInstance().setTitle(hero.toString());

        final Image roleFull1 = GameFrame.getInstance().getGameContainer().getRoleItem().getRoleFull1();
        final Image bgImage1 = GameFrame.getInstance().getGameContainer().getBgImageItem().getBgImage1();
        g2.drawImage(bgImage1, 0, 0, bgImage1.getWidth(null), bgImage1.getHeight(null), null);  // draw bgImage
        
        final MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();
        
        //////// draw bgLayer start
        final Image tileImg = GameFrame.getInstance().getGameContainer().getTileItem().getTile(activeMap.getTileId());
        final int[][] layer1 = activeMap.getBgLayer();
        for (int i = 0; i < activeMap.getWidth(); i++) {
            for (int j = 0; j < activeMap.getHeight(); j++) {
                int tileNum = layer1[i][j];
                if (tileNum != -1) {
                    int y = tileNum % 100;
                    int x = tileNum / 100;
                    //LOG.debug("bgLayer---------------");
                    g2.drawImage(tileImg, i*32, j*32,
                            i*32+32, j*32+32,
                            x*32, y*32,
                            x*32+32, y*32+32,
                            null);
                }
            }
        } // end of for
        //////// draw bgLayer end

        //////// draw role & npc start
        g2.drawImage(roleFull1, posX*32, posY*32, posX*32 + 32, posY*32 + 32,
                hero.getCurFrame()*32, hero.getDirection()*32,
                hero.getCurFrame()*32 + 32, hero.getDirection()*32 + 32, null);

        NPCImageLoader npcImageLoader = GameFrame.getInstance().getGameContainer().getNpcImageLoader();
        ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveFileItem();
        java.util.List<NPCCharacter> npcs = active.getNpcs();
        for (NPCCharacter npc : npcs) {
            npc.move();
            int posX1 = npc.getPosX();
            int posY1 = npc.getPosY();
            int curFrame = npc.getCurFrame();
            int direction = npc.getDirection();
            BufferedImage fullImageOf = npcImageLoader.getFullImageOf(npc.getTileNum());
            g2.drawImage(fullImageOf, posX1*32, posY1*32, posX1*32 + 32, posY1*32 + 32,
                    curFrame*32, direction*32,
                    curFrame*32 + 32, direction*32 + 32, null);
        }
        BoxImageLoader boxImageLoader = GameFrame.getInstance().getGameContainer().getBoxImageLoader();
        java.util.List<BoxCharacter> boxes = active.getBoxes();
        for (BoxCharacter box : boxes) {
            box.move(); // TODO box需要move?
            int posX1 = box.getPosX();
            int posY1 = box.getPosY();
            int tileNum = box.getTileNum();
            BufferedImage boxImage = boxImageLoader.getImageOf(tileNum);
            g2.drawImage(boxImage, posX1*32, posY1*32, null);
        }

//        int[][] layer2 = activeMap.getNpcLayer();
//        for (int i = 0; i < activeMap.getWidth(); i++) {
//            for (int j = 0; j < activeMap.getHeight(); j++) {
//                int tileNum = layer2[i][j];
//                if (tileNum != -1) {
//                    //LOG.debug("npcLayer---------------");
//                    BufferedImage fullImageOf = npcImageLoader.getFullImageOf(tileNum);
//                    g2.drawImage(fullImageOf, i*32, j*32,
//                            i*32+32, j*32+32,
//                            0*32, 0*32,
//                            0*32+32, 0*32+32,
//                            null);
//                }
//            }
//        } // end of for
        //////// draw role & npc end

        //////// draw fgLayer start
        final int[][] layer3 = activeMap.getFgLayer();
        for (int i = 0; i < activeMap.getWidth(); i++) {
            for (int j = 0; j < activeMap.getHeight(); j++) {
                int tileNum = layer3[i][j];
                if (tileNum != -1 && tileNum != layer1[i][j]) {
                    int y = tileNum % 100;
                    int x = tileNum / 100;
//                    LOG.debug("fgLayer---------------");
                    g2.drawImage(tileImg, i*32, j*32,
                            i*32+32, j*32+32,
                            x*32, y*32,
                            x*32+32, y*32+32,
                            null);
                }
            }
        } // end of for
        //////// draw fgLayer end


        //////// draw event start
        final Image transferImage = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameTransfer();
        java.util.List<TransferCharacter> transfers = active.getTransfers();
        for (TransferCharacter transfer : transfers) {
            transfer.move();
            int curFrame = transfer.getCurFrame();
            int posX1 = transfer.getPosX();
            int posY1 = transfer.getPosY();
            g2.drawImage(transferImage, posX1*32, posY1*32, posX1*32 + 32, posY1*32 + 32,
                    0, curFrame*32,
                    32, curFrame*32 + 32, null);
        }
        //////// draw event end

        String name = GameFrame.getInstance().getGameContainer().getActiveMap().getName();
        g2.drawString(name, 600, 20);

        g2.dispose();
        gameCanvas.drawBitmap(paint, 0, 0);
    }


    @Override
    public void onKeyDown(int key) {    }


    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            GameFrame.getInstance().changeScreen(2);
            return;
        } else if (KeyUtil.isHome(key)) {
            BaseScreen bs = new AnimationScreen(2, 100, 150, new MapScreen());
            GameFrame.getInstance().pushScreen(bs);
            return ;
        }

        ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveFileItem();
        HeroCharacter hero = active.getHero();
        if (KeyUtil.isLeft(key)) {
            hero.decreaseX();
        } else if (KeyUtil.isRight(key)) {
            hero.increaseX();
        } else if (KeyUtil.isUp(key)) {
            hero.decreaseY();
        } else if (KeyUtil.isDown(key)) {
            hero.increaseY();
        }

        active.checkTrigger(); // 检查触发器
        active.toCheckTrigger(); // 设置下一步要检查触发器
    }

    @Override
    public boolean isPopup() {
        return false;
    }

}
