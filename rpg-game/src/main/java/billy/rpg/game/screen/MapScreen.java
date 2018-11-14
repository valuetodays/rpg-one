package billy.rpg.game.screen;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.DrawUtil;
import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.walkable.HeroWalkableCharacter;
import billy.rpg.game.command.processor.CmdProcessor;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.resource.item.ScriptItem;
import billy.rpg.game.script.variable.VariableTableDeterminer;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.map.MapMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * map
 */
public class MapScreen extends BaseScreen {
    private int offsetTileX = 0;
    private int offsetTileY = 0;

    @Override
    public void update(long delta) {
        ScriptItem activeScriptItem = GameFrame.getInstance().getGameContainer().getActiveScriptItem();
        if (activeScriptItem == null) {
            throw new RuntimeException("没有ActiveScriptItem");
        }
        CmdProcessor cmdProcessor = activeScriptItem.getCmdProcessor();
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
        
        HeroWalkableCharacter hero = GameFrame.getInstance().getGameContainer().getActiveScriptItem().getHero();
        int posX = hero.getPosX();
        int posY = hero.getPosY();
        GameFrame.getInstance().setTitle("offset x/y="+ offsetTileX + "/" + offsetTileY + hero.toString());

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
                    int y = tileNum / ToolsConstant.TILE_NUM_ONE_LINE;
                    int x = tileNum % ToolsConstant.TILE_NUM_ONE_LINE;
                    //logger.debug("bgLayer---------------");
                    DrawUtil.drawSubImage(g2, tileImg,
                            (i - offsetTileX) * 32, (j - offsetTileY) * 32,
                            x * 32, y*32,
                            GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);
                }
            }
        } // end of for
        //////// draw bgLayer end

        //////// draw role & npc start
        final Image roleFull1 = GameFrame.getInstance().getGameContainer().getRoleItem().getRoleFull1();
        DrawUtil.drawSubImage(g2, roleFull1,
                (posX)*32, (posY)*32,
                hero.getCurFrame()*32, hero.getDirection().getValue()*32,
                GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);

        /*NPCImageLoader npcImageLoader = GameFrame.getInstance().getGameContainer().getNpcImageLoader();
        ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveScriptItem();
        java.util.List<NPCWalkableCharacter> npcs = active.getNpcs();
        for (NPCWalkableCharacter npc : npcs) {
            npc.move(this);
            int posX1 = npc.getPosX();
            int posY1 = npc.getPosY();
            int curFrame = npc.getCurFrame();
            int direction = npc.getDirection().getValue();
            BufferedImage fullImageOf = npcImageLoader.getFullImageOf(npc.getTileNum());
            g2.drawImage(fullImageOf, (posX1-offsetTileX)*32, (posY1-offsetTileY)*32,
                    (posX1-offsetTileX)*32 + 32, (posY1-offsetTileY)*32 + 32,
                    curFrame*32, direction*32,
                    curFrame*32 + 32, direction*32 + 32, null);
        }*/
        /*BoxImageLoader boxImageLoader = GameFrame.getInstance().getGameContainer().getBoxImageLoader();
        java.util.List<BoxWalkableCharacter> boxes = active.getBoxes();
        for (BoxWalkableCharacter box : boxes) {
            box.move(this); // TODO box需要move?
            int posX1 = box.getPosX();
            int posY1 = box.getPosY();
            int tileNum = box.getTileNum();
            BufferedImage boxImage = boxImageLoader.getImageOf(tileNum);
            g2.drawImage(boxImage, (posX1-offsetTileX)*32, (posY1-offsetTileY)*32, null);
        }*/
        //////// draw role & npc end

        //////// draw fgLayer start
        /*final int[][] layer3 = activeMap.getFgLayer();
        for (int i = offsetTileX; i < offsetTileX + GameConstant.Game_TILE_X_NUM; i++) {
            for (int j = offsetTileY; j < offsetTileY + GameConstant.Game_TILE_Y_NUM; j++) {
                int tileNum = layer3[i][j];
                if (tileNum != -1 && tileNum != layer1[i][j]) {
                    int y = tileNum / ToolsConstant.TILE_NUM_ONE_LINE;
                    int x = tileNum % ToolsConstant.TILE_NUM_ONE_LINE;
//                    logger.debug("fgLayer---------------");
                    g2.drawImage(tileImg, (i-offsetTileX)*32, (j-offsetTileY)*32,
                            (i-offsetTileX)*32+32, (j-offsetTileY)*32+32,
                            x*32, y*32,
                            x*32+32, y*32+32,
                            null);
                }
            }
        } */ // end of for
        //////// draw fgLayer end


        //////// draw event start
       /* final Image transferImage = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameTransfer();
        java.util.List<TransferWalkableCharacter> transfers = active.getTransfers();
        for (TransferWalkableCharacter transfer : transfers) {
            transfer.move(this);
            int curFrame = transfer.getCurFrame();
            int posX1 = transfer.getPosX();
            int posY1 = transfer.getPosY();
            g2.drawImage(transferImage, (posX1-offsetTileX)*32, (posY1-offsetTileY)*32,
                    (posX1-offsetTileX)*32 + 32, (posY1-offsetTileY)*32 + 32,
                    0, curFrame*32,
                    32, curFrame*32 + 32, null);
        }*/
        //////// draw event end

        String mapName = GameFrame.getInstance().getGameContainer().getActiveMap().getName();
        g2.setFont(GameConstant.FONT_SIZE_MAP_NAME);
        g2.setColor(GameConstant.FONT_COLOR_MAP_NAME);
        g2.drawString(mapName, 300, 20);

        g2.dispose();

        gameCanvas.drawBitmap(paint, 0, 0);
    }


    @Override
    public void onKeyDown(int key) {
        ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveScriptItem();
        active.checkTrigger(); // 检查触发器
        active.checkMonster();
        active.toCheckTrigger(); // 设置下一步要检查触发器
        HeroWalkableCharacter hero = active.getHero();
        if (KeyUtil.isLeft(key)) {
            hero.decreaseX(this);
        } else if (KeyUtil.isRight(key)) {
            hero.increaseX(this);
        } else if (KeyUtil.isUp(key)) {
            hero.decreaseY(this);
        } else if (KeyUtil.isDown(key)) {
            hero.increaseY(this);
        }
    }


    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            GameFrame.getInstance().changeScreen(2);
            return;
        } else if (KeyUtil.isHome(key)) {
            ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveScriptItem();
            HeroWalkableCharacter hero = active.getHero();

            BaseScreen bs = new AnimationScreen(10001, hero.getPosX()*32, hero.getPosY()*32, new MapScreen());
            GameFrame.getInstance().pushScreen(bs);
            return;
        } else if (KeyUtil.isLeft(key) || KeyUtil.isRight(key) || KeyUtil.isUp(key) || KeyUtil.isDown(key)) {
            ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveScriptItem();
            HeroWalkableCharacter hero = active.getHero();
            hero.resetFrame();
        } else if (KeyUtil.isG(key)) {
            VariableTableDeterminer.getInstance().printVariables();
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
