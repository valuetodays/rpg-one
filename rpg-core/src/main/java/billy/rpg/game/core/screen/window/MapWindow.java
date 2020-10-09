package billy.rpg.game.core.screen.window;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.DrawUtil;
import billy.rpg.game.core.GameTemp;
import billy.rpg.game.core.character.walkable.BoxWalkableCharacter;
import billy.rpg.game.core.character.walkable.FlickerObjectWalkableCharacter;
import billy.rpg.game.core.character.walkable.HeroWalkableCharacter;
import billy.rpg.game.core.character.walkable.TransferWalkableCharacter;
import billy.rpg.game.core.character.walkable.npc.NPCWalkableCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.constants.WeatherEnum;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;
import billy.rpg.game.core.screen.MapScreen;
import billy.rpg.resource.box.BoxImageLoader;
import billy.rpg.resource.map.MapMetaData;
import billy.rpg.resource.npc.NPCImageLoader;

public class MapWindow extends BaseWindow {

    private Map<String, BufferedImage> cachedTotalMaps = new ConcurrentHashMap<>();
    private int offsetTileX = 0;
    private int offsetTileY = 0;
    private MapScreen owner;

    public MapWindow(MapScreen owner) {
        super(0, 0);
        this.owner = owner;
    }

    @Override
    public void update(Graphics g) {
        GameContainer gameContainer = GameTemp.gameContainer;
        drawBgImage(gameContainer, g);
        drawBgLayer(gameContainer, g);
        drawPlayer(gameContainer, g);
        drawNpcs(gameContainer, g);
        drawBoxes(gameContainer, g);
        drawFgLayer(gameContainer, g);
        drawEvent(gameContainer, g);
        drawScreenObjects(gameContainer, g);
        drawWeather(gameContainer, g);
        drawMapName(gameContainer, g);
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

    private void drawMapName(GameContainer gameContainer, Graphics g) {
        String mapName = gameContainer.getActiveMap().getName();
        g.setFont(gameContainer.getGameConfig().getMapNameFont());
        g.setColor(gameContainer.getGameConfig().getMapNameFontColor());
        g.drawString(mapName, 300, 20);
    }

    private void drawWeather(GameContainer gameContainer, Graphics g) {
        ScriptItem active = gameContainer.getActiveScriptItem();
        WeatherEnum weather = active.getWeather();
        if (weather != null) {
            String name = weather.name();
            g.drawString(name, 200, 20);
        }
    }

    private void drawScreenObjects(GameContainer gameContainer, Graphics g) {
        ScriptItem active = gameContainer.getActiveScriptItem();
        List<FlickerObjectWalkableCharacter> sceneObjects = active.getSceneObjects();
        for (FlickerObjectWalkableCharacter sceneObject : sceneObjects) {
            BufferedImage image = sceneObject.getImage();
            int posX1 = sceneObject.getPosX();
            int posY1 = sceneObject.getPosY();
            DrawUtil.drawImage(g, image, (posX1-offsetTileX)*32, (posY1-offsetTileY) *32);
        }
    }

    private void drawEvent(GameContainer gameContainer, Graphics g) {
        final Image transferImage = gameContainer.getGameAboutItem().getGameTransfer();
        ScriptItem active = gameContainer.getActiveScriptItem();
        java.util.List<TransferWalkableCharacter> transfers = active.getTransfers();
        for (TransferWalkableCharacter transfer : transfers) {
            transfer.move(gameContainer, owner);
            int curFrame = transfer.getCurFrame();
            int posX1 = transfer.getPosX();
            int posY1 = transfer.getPosY();
            DrawUtil.drawSubImage(g, transferImage,
                    (posX1-offsetTileX)*32, (posY1-offsetTileY)*32,
                    0, curFrame*32,
                    GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);
        }
    }

    private void drawFgLayer(GameContainer gameContainer, Graphics g) {
        /*
        final int[][] layer3 = activeMap.getFgLayer();
        for (int i = offsetTileX; i < offsetTileX + GameConstant.Game_TILE_X_NUM; i++) {
            for (int j = offsetTileY; j < offsetTileY + GameConstant.Game_TILE_Y_NUM; j++) {
                int tileNum = layer3[i][j];
                if (tileNum != -1 && tileNum != layer1[i][j]) {
                    int y = tileNum / ToolsConstant.TILE_NUM_ONE_LINE;
                    int x = tileNum % ToolsConstant.TILE_NUM_ONE_LINE;
                    g2.drawImage(tileImg, (i-offsetTileX)*32, (j-offsetTileY)*32,
                            (i-offsetTileX)*32+32, (j-offsetTileY)*32+32,
                            x*32, y*32,
                            x*32+32, y*32+32,
                            null);
                }
            }
        } // end of for
        */
    }

    private void drawBoxes(GameContainer gameContainer, Graphics g) {
        BoxImageLoader boxImageLoader = gameContainer.getBoxImageLoader();
        ScriptItem active = gameContainer.getActiveScriptItem();
        java.util.List<BoxWalkableCharacter> boxes = active.getBoxes();
        for (BoxWalkableCharacter box : boxes) {
            box.move(gameContainer, owner);
            if (box.isVisible()) {
                int posX1 = box.getPosX();
                int posY1 = box.getPosY();
                BufferedImage boxImage = boxImageLoader.getImageOf(box.getTileNum());
                g.drawImage(boxImage, (posX1 - offsetTileX) * 32, (posY1 - offsetTileY) * 32, null);
            }
        }
    }

    private void drawNpcs(GameContainer gameContainer, Graphics g) {
        NPCImageLoader npcImageLoader = gameContainer.getNpcImageLoader();
        ScriptItem active = gameContainer.getActiveScriptItem();
        java.util.List<NPCWalkableCharacter> npcs = active.getNpcs();
        for (NPCWalkableCharacter npc : npcs) {
            npc.move(gameContainer, owner);
            int posX1 = npc.getPosX();
            int posY1 = npc.getPosY();
            int curFrame = npc.getCurFrame();
            int direction = npc.getDirection().getValue();
            BufferedImage fullImageOf = npcImageLoader.getFullImageOf(npc.getTileNum());
            DrawUtil.drawSubImage(g, fullImageOf,
                    (posX1-offsetTileX)*32, (posY1-offsetTileY)*32,
                    curFrame*32, direction*32,
                    GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);
        }
    }

    private void drawPlayer(GameContainer gameContainer, Graphics g) {
        HeroWalkableCharacter hero = gameContainer.getActiveScriptItem().getHero();
        int posX = hero.getPosX();
        int posY = hero.getPosY();
        gameContainer.getGameFrame().setTitle("offset x/y="+ offsetTileX + "/" + offsetTileY + hero.toString(gameContainer));

        int controlId = gameContainer.getGameData().getControlId();
        final Image roleFull1 = gameContainer.getRoleItem().getRoleFullImageOf(controlId);
        DrawUtil.drawSubImage(g, roleFull1,
                (posX)*32, (posY)*32,
                hero.getCurFrame()*32, hero.getDirection().getValue()*32,
                GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);
    }

    private void drawBgLayer(GameContainer gameContainer, Graphics g) {
        final MapMetaData activeMap = gameContainer.getActiveMap();

        //////// draw bgLayer start
        boolean flagDrawAdvanceBgLayer = true;
        if (flagDrawAdvanceBgLayer) { // use advanced
            final Image tileImg = gameContainer.getTileItem().getTile(activeMap.getTileId());
            final int[][] bgLayer = activeMap.getBgLayer();
            BufferedImage cachedImage = cachedTotalMaps.get(activeMap.getMapId());
            if (cachedImage == null) {
                cachedImage = new BufferedImage(
                        bgLayer.length * GameConstant.GAME_TILE_WIDTH,
                        bgLayer[0].length * GameConstant.GAME_TILE_HEIGHT,
                        BufferedImage.TYPE_4BYTE_ABGR);
                Graphics cachedGraphic = cachedImage.getGraphics();

                for (int i = 0; i < bgLayer.length; i++) {
                    for (int j = 0; j < bgLayer[i].length; j++) {
                        int tileNum = bgLayer[i][j];
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
            DrawUtil.drawSubImage(g, cachedImage, getLeft(), getTop(),
                    offsetTileX* GameConstant.GAME_TILE_WIDTH, offsetTileY*GameConstant.GAME_TILE_HEIGHT,
                    GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT);
        } else {
            final Image tileImg = gameContainer.getTileItem().getTile(activeMap.getTileId());
            final int[][] bgLayer = activeMap.getBgLayer();
            for (int i = offsetTileX; i < offsetTileX + GameConstant.Game_TILE_X_NUM; i++) {
                for (int j = offsetTileY; j < offsetTileY + GameConstant.Game_TILE_Y_NUM; j++) {
                    int tileNum = bgLayer[i][j];
                    if (tileNum != -1) {
                        int y = tileNum / ToolsConstant.TILE_NUM_ONE_LINE;
                        int x = tileNum % ToolsConstant.TILE_NUM_ONE_LINE;
                        //logger.debug("bgLayer---------------");
                        DrawUtil.drawSubImage(g, tileImg,
                                (i - offsetTileX) * 32, (j - offsetTileY) * 32,
                                x * 32, y*32,
                                GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);
                    }
                }
            } // end of for
        }
        //////// draw bgLayer end
    }

    private void drawBgImage(GameContainer gameContainer, Graphics g2) {
        // TODO 每个地图应该绑定一个背景图片
        final Image bgImage1 = gameContainer.getBgImageItem().getBgImage1();
        g2.drawImage(bgImage1, getLeft(), getTop(), bgImage1.getWidth(null), bgImage1.getHeight(null), null);  // draw bgImage
    }

    public int getOffsetTileX() {
        return offsetTileX;
    }

    public int getOffsetTileY() {
        return offsetTileY;
    }

    public void setOffsetTileX(int offsetTileX) {
        this.offsetTileX = offsetTileX;
    }

    public void setOffsetTileY(int offsetTileY) {
        this.offsetTileY = offsetTileY;
    }
}

