package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.BoxCharacter;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.character.TransferCharacter;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.item.ScriptItem;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.box.BoxImageLoader;
import billy.rpg.resource.map.MapMetaData;
import billy.rpg.resource.npc.NPCImageLoader;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * map
 */
public class MapScreen extends BaseScreen {
    private static Logger LOG = Logger.getLogger(MapScreen.class);
    private int offsetTileX = 10;
    private int offsetTileY = 0;

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
        GameFrame.getInstance().setTitle("offset x/y="+ offsetTileX + "/" + offsetTileY + hero.toString());

        final Image roleFull1 = GameFrame.getInstance().getGameContainer().getRoleItem().getRoleFull1();
        final Image bgImage1 = GameFrame.getInstance().getGameContainer().getBgImageItem().getBgImage1();
        g2.drawImage(bgImage1, 0, 0, bgImage1.getWidth(null), bgImage1.getHeight(null), null);  // draw bgImage
        
        final MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();
        
        //////// draw bgLayer start
        final Image tileImg = GameFrame.getInstance().getGameContainer().getTileItem().getTile(activeMap.getTileId());
        final int[][] layer1 = activeMap.getBgLayer();
        for (int i = offsetTileX; i < offsetTileX + GameConstant.Game_TILE_X_NUM; i++) {
            for (int j = offsetTileY; j < offsetTileY + GameConstant.Game_TILE_Y_NUM; j++) {
                int tileNum = layer1[i][j];
                if (tileNum != -1) {
                    int y = tileNum % 100;
                    int x = tileNum / 100;
                    //LOG.debug("bgLayer---------------");
                    g2.drawImage(tileImg, (i - offsetTileX) * 32, (j - offsetTileY) * 32,
                            (i - offsetTileX) * 32 + 32, (j - offsetTileY) * 32 + 32,
                            x * 32, y * 32,
                            x * 32 + 32, y * 32 + 32,
                            null);
                }
            }
        } // end of for
        //////// draw bgLayer end

        //////// draw role & npc start
        g2.drawImage(roleFull1, (posX)*32, (posY)*32,
                (posX)*32 + 32, (posY)*32 + 32,
                hero.getCurFrame()*32, hero.getDirection()*32,
                hero.getCurFrame()*32 + 32, hero.getDirection()*32 + 32, null);

        NPCImageLoader npcImageLoader = GameFrame.getInstance().getGameContainer().getNpcImageLoader();
        ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveFileItem();
        java.util.List<NPCCharacter> npcs = active.getNpcs();
        for (NPCCharacter npc : npcs) {
            npc.move(this);
            int posX1 = npc.getPosX();
            int posY1 = npc.getPosY();
            int curFrame = npc.getCurFrame();
            int direction = npc.getDirection();
            BufferedImage fullImageOf = npcImageLoader.getFullImageOf(npc.getTileNum());
            g2.drawImage(fullImageOf, (posX1-offsetTileX)*32, (posY1-offsetTileY)*32,
                    (posX1-offsetTileX)*32 + 32, (posY1-offsetTileY)*32 + 32,
                    curFrame*32, direction*32,
                    curFrame*32 + 32, direction*32 + 32, null);
        }
        BoxImageLoader boxImageLoader = GameFrame.getInstance().getGameContainer().getBoxImageLoader();
        java.util.List<BoxCharacter> boxes = active.getBoxes();
        for (BoxCharacter box : boxes) {
            box.move(this); // TODO box需要move?
            int posX1 = box.getPosX();
            int posY1 = box.getPosY();
            int tileNum = box.getTileNum();
            BufferedImage boxImage = boxImageLoader.getImageOf(tileNum);
            g2.drawImage(boxImage, (posX1-offsetTileX)*32, (posY1-offsetTileY)*32, null);
        }
        //////// draw role & npc end

        //////// draw fgLayer start
        final int[][] layer3 = activeMap.getFgLayer();
        for (int i = offsetTileX; i < offsetTileX + GameConstant.Game_TILE_X_NUM; i++) {
            for (int j = offsetTileY; j < offsetTileY + GameConstant.Game_TILE_Y_NUM; j++) {
                int tileNum = layer3[i][j];
                if (tileNum != -1 && tileNum != layer1[i][j]) {
                    int y = tileNum % 100;
                    int x = tileNum / 100;
//                    LOG.debug("fgLayer---------------");
                    g2.drawImage(tileImg, (i-offsetTileX)*32, (j-offsetTileY)*32,
                            (i-offsetTileX)*32+32, (j-offsetTileY)*32+32,
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
            transfer.move(this);
            int curFrame = transfer.getCurFrame();
            int posX1 = transfer.getPosX();
            int posY1 = transfer.getPosY();
            g2.drawImage(transferImage, (posX1-offsetTileX)*32, (posY1-offsetTileY)*32,
                    (posX1-offsetTileX)*32 + 32, (posY1-offsetTileY)*32 + 32,
                    0, curFrame*32,
                    32, curFrame*32 + 32, null);
        }
        //////// draw event end

        String name = GameFrame.getInstance().getGameContainer().getActiveMap().getName();
        g2.setFont(GameConstant.FONT_SIZE_MAP_NAME);
        g2.setColor(GameConstant.FONT_COLOR_MAP_NAME);
        g2.drawString(name, 500, 20);

        g2.dispose();
        gameCanvas.drawBitmap(paint, 0, 0);
    }


    @Override
    public void onKeyDown(int key) {
        ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveFileItem();
        HeroCharacter hero = active.getHero();
        if (KeyUtil.isLeft(key)) {
            hero.decreaseX(this);
        } else if (KeyUtil.isRight(key)) {
            hero.increaseX(this);
        } else if (KeyUtil.isUp(key)) {
            hero.decreaseY(this);
        } else if (KeyUtil.isDown(key)) {
            hero.increaseY(this);
        }

        active.checkTrigger(); // 检查触发器
        active.toCheckTrigger(); // 设置下一步要检查触发器
    }


    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            GameFrame.getInstance().changeScreen(2);
            return;
        } else if (KeyUtil.isHome(key)) {
            BaseScreen bs = new AnimationScreen(2, 100, 150, new MapScreen());
            GameFrame.getInstance().pushScreen(bs);
            return ;
        } else if (KeyUtil.isLeft(key) || KeyUtil.isRight(key) || KeyUtil.isUp(key) || KeyUtil.isDown(key)) {
            ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveFileItem();
            HeroCharacter hero = active.getHero();
            hero.resetFrame();
        }

    }

    @Override
    public boolean isPopup() {
        return false;
    }

    public void increaseOffsetX() {
        final MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();
        int height = activeMap.getHeight();
        int width = activeMap.getWidth();
        if (offsetTileX < (width - GameConstant.Game_TILE_X_NUM)) {
            offsetTileX++;
        }
    }

    public void decreaseOffsetX() {
        final MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();
        int height = activeMap.getHeight();
        int width = activeMap.getWidth();
        if (offsetTileX > 0) {
            offsetTileX--;
        }
    }

    public void increaseOffsetY() {
        final MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();
        int height = activeMap.getHeight();
        int width = activeMap.getWidth();
        if (offsetTileY < (height - GameConstant.Game_TILE_Y_NUM)) {
            offsetTileY++;
        }
    }

    public void decreaseOffsetY() {
        final MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();
        int height = activeMap.getHeight();
        int width = activeMap.getWidth();
        if (offsetTileY > 0) {
            offsetTileY--;
        }
    }

    public int getOffsetTileX() {
        return offsetTileX;
    }

    public int getOffsetTileY() {
        return offsetTileY;
    }
    public void clearOffset() {
        offsetTileX = 0;
        offsetTileY = 0;
    }

    public void setOffset(int offsetX, int offsetY) {
        offsetTileX = offsetX;
        offsetTileY = offsetY;
    }
}
