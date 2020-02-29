package billy.rpg.game.core.screen;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.DrawUtil;
import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.walkable.BoxWalkableCharacter;
import billy.rpg.game.core.character.walkable.FlickerObjectWalkableCharacter;
import billy.rpg.game.core.character.walkable.HeroWalkableCharacter;
import billy.rpg.game.core.character.walkable.TransferWalkableCharacter;
import billy.rpg.game.core.character.walkable.npc.NPCWalkableCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.constants.ScreenCodeEnum;
import billy.rpg.game.core.constants.WalkableConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.constants.WeatherEnum;
import billy.rpg.game.core.item.ScriptItem;
import billy.rpg.game.core.script.event.EventTableDeterminer;
import billy.rpg.game.core.util.KeyUtil;
import billy.rpg.resource.box.BoxImageLoader;
import billy.rpg.resource.map.MapMetaData;
import billy.rpg.resource.npc.NPCImageLoader;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 地图界面
 */
public class MapScreen extends BaseScreen {
    private int offsetTileX = 0;
    private int offsetTileY = 0;

    @Override
    public void update(GameContainer gameContainer, long delta) {
        ScriptItem activeScriptItem = gameContainer.getActiveScriptItem();
        if (activeScriptItem == null) {
            throw new RuntimeException("没有ActiveScriptItem");
        }
        CmdProcessor cmdProcessor = activeScriptItem.getCmdProcessor();
        if (cmdProcessor != null) {
            cmdProcessor.update(gameContainer);
        }
    }

    private Map<String, BufferedImage> cachedTotalMaps = new ConcurrentHashMap<>();

    /**
     * @param desktopCanvas desktopCanvas
     */
    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g2 = paint.getGraphics();
        
        HeroWalkableCharacter hero = gameContainer.getActiveScriptItem().getHero();
        int posX = hero.getPosX();
        int posY = hero.getPosY();
        gameContainer.getGameFrame().setTitle("offset x/y="+ offsetTileX + "/" + offsetTileY + hero.toString(gameContainer));

        {
            // draw background image
            final Image bgImage1 = gameContainer.getBgImageItem().getBgImage1();
            g2.drawImage(bgImage1, 0, 0, bgImage1.getWidth(null), bgImage1.getHeight(null), null);  // draw bgImage
        }

        final MapMetaData activeMap = gameContainer.getActiveMap();

        //////// draw bgLayer start
        boolean flagDrawAdvanceBgLayer = true;
        if (flagDrawAdvanceBgLayer) { // use advanced
            final Image tileImg = gameContainer.getTileItem().getTile(activeMap.getTileId());
            final int[][] layer1 = activeMap.getBgLayer();
            BufferedImage cachedImage = cachedTotalMaps.get(activeMap.getMapId());
            if (cachedImage == null) {
                cachedImage = new BufferedImage(
                        layer1.length * GameConstant.GAME_TILE_WIDTH,
                        layer1[0].length * GameConstant.GAME_TILE_HEIGHT,
                        BufferedImage.TYPE_4BYTE_ABGR);
                Graphics cachedGraphic = cachedImage.getGraphics();

                for (int i = 0; i < layer1.length; i++) {
                    for (int j = 0; j < layer1[i].length; j++) {
                        int tileNum = layer1[i][j];
                        if (-1 != tileNum) {
                            int y = tileNum / ToolsConstant.TILE_NUM_ONE_LINE;
                            int x = tileNum % ToolsConstant.TILE_NUM_ONE_LINE;
                            DrawUtil.drawSubImage(cachedGraphic, tileImg,
                                    (i) * 32, (j) * 32,
                                    x * 32, y*32,
                                    GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);
                        }
                    }
                }
                cachedGraphic.dispose();
                cachedTotalMaps.put(activeMap.getMapId(), cachedImage);
            }
            DrawUtil.drawSubImage(g2, cachedImage, 0, 0,
                    offsetTileX*GameConstant.GAME_TILE_WIDTH, offsetTileY*GameConstant.GAME_TILE_HEIGHT,
                    GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT);
        } else {
            final Image tileImg = gameContainer.getTileItem().getTile(activeMap.getTileId());
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
        }
        //////// draw bgLayer end

        //////// draw role & npc start
        int controlId = gameContainer.getGameData().getControlId();
        final Image roleFull1 = gameContainer.getRoleItem().getRoleFullImageOf(controlId);
        DrawUtil.drawSubImage(g2, roleFull1,
                (posX)*32, (posY)*32,
                hero.getCurFrame()*32, hero.getDirection().getValue()*32,
                GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);

        NPCImageLoader npcImageLoader = gameContainer.getNpcImageLoader();
        ScriptItem active = gameContainer.getActiveScriptItem();
        java.util.List<NPCWalkableCharacter> npcs = active.getNpcs();
        for (NPCWalkableCharacter npc : npcs) {
            npc.move(gameContainer, this);
            int posX1 = npc.getPosX();
            int posY1 = npc.getPosY();
            int curFrame = npc.getCurFrame();
            int direction = npc.getDirection().getValue();
            BufferedImage fullImageOf = npcImageLoader.getFullImageOf(npc.getTileNum());
            DrawUtil.drawSubImage(g2, fullImageOf,
                    (posX1-offsetTileX)*32, (posY1-offsetTileY)*32,
                    curFrame*32, direction*32,
                    GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);
        }
        BoxImageLoader boxImageLoader = gameContainer.getBoxImageLoader();
        java.util.List<BoxWalkableCharacter> boxes = active.getBoxes();
        for (BoxWalkableCharacter box : boxes) {
            box.move(gameContainer, this);
            if (box.isVisible()) {
                int posX1 = box.getPosX();
                int posY1 = box.getPosY();
                BufferedImage boxImage = boxImageLoader.getImageOf(box.getTileNum());
                g2.drawImage(boxImage, (posX1 - offsetTileX) * 32, (posY1 - offsetTileY) * 32, null);
            }
        }
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
        final Image transferImage = gameContainer.getGameAboutItem().getGameTransfer();
        java.util.List<TransferWalkableCharacter> transfers = active.getTransfers();
        for (TransferWalkableCharacter transfer : transfers) {
            transfer.move(gameContainer, this);
            int curFrame = transfer.getCurFrame();
            int posX1 = transfer.getPosX();
            int posY1 = transfer.getPosY();
            DrawUtil.drawSubImage(g2, transferImage,
                    (posX1-offsetTileX)*32, (posY1-offsetTileY)*32,
                    0, curFrame*32,
                    GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);
        }
        //////// draw event end

        // draw scene-object begin
        List<FlickerObjectWalkableCharacter> sceneObjects = active.getSceneObjects();
        for (FlickerObjectWalkableCharacter sceneObject : sceneObjects) {
            BufferedImage image = sceneObject.getImage();
            int posX1 = sceneObject.getPosX();
            int posY1 = sceneObject.getPosY();
            DrawUtil.drawImage(g2, image, (posX1-offsetTileX)*32, (posY1-offsetTileY) *32);
        }
        // draw scene-object end

        // draw weather begin
        WeatherEnum weather = active.getWeather();
        if (weather != null) {
            String name = weather.name();
            g2.drawString(name, 200, 20);
        }
        // draw weather end

        String mapName = gameContainer.getActiveMap().getName();
        g2.setFont(gameContainer.getGameConfig().getMapNameFont());
        g2.setColor(gameContainer.getGameConfig().getMapNameFontColor());
        g2.drawString(mapName, 300, 20);
        g2.dispose();

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }


    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
        ScriptItem active = gameContainer.getActiveScriptItem();
        active.checkMonster(gameContainer);
        HeroWalkableCharacter hero = active.getHero();
        if (KeyUtil.isLeft(key)) {
            hero.setDirection(WalkableConstant.PositionEnum.LEFT);
        } else if (KeyUtil.isRight(key)) {
            hero.setDirection(WalkableConstant.PositionEnum.RIGHT);
        } else if (KeyUtil.isUp(key)) {
            hero.setDirection(WalkableConstant.PositionEnum.UP);
        } else if (KeyUtil.isDown(key)) {
            hero.setDirection(WalkableConstant.PositionEnum.DOWN);
        }

        if (!active.checkTrigger(gameContainer)) { // 检查触发器
            active.toCheckTrigger(); // 设置下一步要检查触发器
            if (KeyUtil.isLeft(key)) {
                hero.decreaseX(gameContainer, this);
            } else if (KeyUtil.isRight(key)) {
                hero.increaseX(gameContainer, this);
            } else if (KeyUtil.isUp(key)) {
                hero.decreaseY(gameContainer, this);
            } else if (KeyUtil.isDown(key)) {
                hero.increaseY(gameContainer, this);
            }
        }
    }


    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (KeyUtil.isEsc(key)) {
            gameContainer.getGameFrame().changeScreen(ScreenCodeEnum.SCREEN_CODE_SYSTEM_UI_SCREEN);
            return;
        } else if (KeyUtil.isHome(key)) {
            ScriptItem active = gameContainer.getActiveScriptItem();
            HeroWalkableCharacter hero = active.getHero();

            BaseScreen bs = new AnimationScreen(gameContainer, 10001, hero.getPosX()*32, hero.getPosY()*32, new MapScreen());
            gameContainer.getGameFrame().pushScreen(bs);
            return;
        } else if (KeyUtil.isLeft(key) || KeyUtil.isRight(key) || KeyUtil.isUp(key) || KeyUtil.isDown(key)) {
            ScriptItem active = gameContainer.getActiveScriptItem();
            HeroWalkableCharacter hero = active.getHero();
            hero.resetFrame();
        } else if (KeyUtil.isG(key)) {
            EventTableDeterminer.getInstance().printEvents();
        } else if (KeyUtil.isQ(key)) {
            gameContainer.getGameData().exChangeControlId();
        }
    }

    @Override
    public boolean isPopup() {
        return false;
    }

    public void increaseOffsetX(GameContainer gameContainer) {
        final MapMetaData activeMap = gameContainer.getActiveMap();
        int height = activeMap.getHeight();
        int width = activeMap.getWidth();
        if (offsetTileX < (width - GameConstant.Game_TILE_X_NUM)) {
            offsetTileX++;
        }
    }

    public void decreaseOffsetX(GameContainer gameContainer) {
        final MapMetaData activeMap = gameContainer.getActiveMap();
        int height = activeMap.getHeight();
        int width = activeMap.getWidth();
        if (offsetTileX > 0) {
            offsetTileX--;
        }
    }

    public void increaseOffsetY(GameContainer gameContainer) {
        final MapMetaData activeMap = gameContainer.getActiveMap();
        int height = activeMap.getHeight();
        int width = activeMap.getWidth();
        if (offsetTileY < (height - GameConstant.Game_TILE_Y_NUM)) {
            offsetTileY++;
        }
    }

    public void decreaseOffsetY(GameContainer gameContainer) {
        final MapMetaData activeMap = gameContainer.getActiveMap();
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
