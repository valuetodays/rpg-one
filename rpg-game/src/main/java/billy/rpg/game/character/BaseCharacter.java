package billy.rpg.game.character;

import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.CharacterConstant;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.MapScreen;
import billy.rpg.game.util.WalkUtil;
import billy.rpg.resource.map.MapMetaData;

/**
 *
 * @author liulei
 * @since 2017-05-18 14:04
 */
public abstract class BaseCharacter implements CharacterConstant {
    private int number; // 编号
    private int posX = 6; // 当前x
    private int posY = 6; // 当前y
    private int curFrame;  // 步数
    private int direction; // 方向

    /**
     * init position
     * @param posX x
     * @param posY y
     */
    public void initPos(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void increaseCurFrame() {
        curFrame++;
        if (curFrame == 3) {
            curFrame = 0;
        }
    }

    public int getCurFrame() {
        return curFrame;
    }

    public void setCurFrame(int curFrame) {
        this.curFrame = curFrame;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * get next posX
     * @return nextPosX
     */
    public int getNextPosX() {
        int mapWidth = GameFrame.getInstance().getGameContainer().getActiveMap().getWidth();
        MapScreen mapScreen = GameFrame.getInstance().getGameContainer().getMapScreen();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        return posX + ((direction == DIRECTION_LEFT && posX > 0) ? -1 :
                ((direction == DIRECTION_RIGHT && (posX + offsetTileX)< mapWidth - 1) ? 1 : 0)
        );
    }

    /**
     * get next posY
     * @return nextPosY
     */
    public int getNextPosY() {
        int mapHeight = GameFrame.getInstance().getGameContainer().getActiveMap().getHeight();
        MapScreen mapScreen = GameFrame.getInstance().getGameContainer().getMapScreen();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        return posY + ((direction == DIRECTION_UP && posY > 0) ? -1 :
                ((direction == DIRECTION_DOWN && (posY+offsetTileY) < mapHeight - 1) ? 1 : 0 )
        );
    }



    /**
     * move right
     * @param mapScreen mapScreen
     */
    public void increaseX(MapScreen mapScreen) {
        increaseCurFrame();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();
        if (activeMap.getWidth() <= GameConstant.Game_TILE_X_NUM) {
            int nextPosX = posX + 1;
            if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                posX = nextPosX;
            }
        } else {
            if (offsetTileX <= 0) {
                if (posX < GameConstant.Game_TILE_X_NUM / 2) {
                    int nextPosX = posX + 1;
                    if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                        posX = nextPosX;
                    }
                } else {
                    int nextPosX = posX + 1;
                    if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                        mapScreen.increaseOffsetX();
                    }
                }
            } else if (offsetTileX < activeMap.getWidth() - GameConstant.Game_TILE_X_NUM) {
                int nextPosX = posX + 1;
                if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                    mapScreen.increaseOffsetX();
                }
            } else if (offsetTileX >= activeMap.getWidth() - GameConstant.Game_TILE_X_NUM) {
                if (posX >= GameConstant.Game_TILE_X_NUM / 2) {
                    int nextPosX = posX + 1;
                    if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                        posX = nextPosX;
                    }
                } else {
                    int nextPosX = posX + 1;
                    if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                        mapScreen.increaseOffsetX();
                    }
                }
            }
        }

        direction = DIRECTION_RIGHT;
    }

    /**
     * move left
     * @param mapScreen mapScreen
     */
    public void decreaseX(MapScreen mapScreen) {
        increaseCurFrame();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();

        if (activeMap.getWidth() <= GameConstant.Game_TILE_X_NUM) {
            int nextPosX = posX - 1;
            if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                posX = nextPosX;
            }
        } else {
            // 如下有注意点：
            // 当人物在右半屏幕且大地图在右半部分时，offsetTileX的值是大于(大地图宽度-一屏纵向Tile数的)，
            // 并且该值也是大于0的
            // 所以，千万不要把下面的第二个if放在第一个之前
            if (offsetTileX >= activeMap.getWidth() - GameConstant.Game_TILE_X_NUM) {
                if (posX > GameConstant.Game_TILE_X_NUM / 2) {
                    int nextPosX = posX - 1;
                    if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                        posX = nextPosX;
                    }
                } else {
                    int nextPosX = posX - 1;
                    if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                        mapScreen.decreaseOffsetX();
                    }
                }
            } else if (offsetTileX > 0) {
                int nextPosX = posX - 1;
                if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                    mapScreen.decreaseOffsetX();
                }
            } else if (offsetTileX <= 0) {
                if (posX <= GameConstant.Game_TILE_X_NUM / 2) {
                    int nextPosX = posX - 1;
                    if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                        posX = nextPosX;
                    }
                } else {
                    int nextPosX = posX - 1;
                    if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY + posY)) {
                        mapScreen.decreaseOffsetX();
                    }
                }
            }
        }

        direction = DIRECTION_LEFT;
    }

    /**
     * move down
     * @param mapScreen mapScreen
     */
    public void increaseY(MapScreen mapScreen) {
        increaseCurFrame();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();

        if (activeMap.getHeight() <= GameConstant.Game_TILE_Y_NUM) {
            int nextPosY = posY + 1;
            if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                posY = nextPosY;
            }
        } else {
            if (offsetTileY <= 0) {
                if (posY < GameConstant.Game_TILE_Y_NUM / 2) {
                    int nextPosY = posY + 1;
                    if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                        posY = nextPosY;
                    }
                } else {
                    int nextPosY = posY + 1;
                    if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                        mapScreen.increaseOffsetY();
                    }
                }
            } else if (offsetTileY < activeMap.getHeight() - GameConstant.Game_TILE_Y_NUM) {
                int nextPosY = posY + 1;
                if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                    mapScreen.increaseOffsetY();
                }
            } else if (offsetTileY >= activeMap.getHeight() - GameConstant.Game_TILE_Y_NUM) {
                if (posY >= GameConstant.Game_TILE_Y_NUM / 2) {
                    int nextPosY = posY + 1;
                    if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                        posY = nextPosY;
                    }
                } else {
                    int nextPosY = posY + 1;
                    if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                        mapScreen.increaseOffsetY();
                    }
                }
            }
        }

        direction = DIRECTION_DOWN;
    }

    /**
     * move up
     * @param mapScreen mapScreen
     */
    public void decreaseY(MapScreen mapScreen) {
        increaseCurFrame();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        MapMetaData activeMap = GameFrame.getInstance().getGameContainer().getActiveMap();

        if (activeMap.getHeight() <= GameConstant.Game_TILE_Y_NUM) {
            int nextPosY = posY - 1;
            if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                posY = nextPosY;
            }
        } else {
            if (offsetTileY >= activeMap.getHeight() - GameConstant.Game_TILE_Y_NUM) {
                if (posY > GameConstant.Game_TILE_Y_NUM / 2) {
                    int nextPosY = posY - 1;
                    if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                        posY = nextPosY;
                    }
                } else {
                    int nextPosY = posY - 1;
                    if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                        mapScreen.decreaseOffsetY();
                    }
                }
            } else if (offsetTileY > 0) {
                int nextPosY = posY - 1;
                if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                    mapScreen.decreaseOffsetY();
                }
            } else if (offsetTileY <= 0) {
                if (posY <= GameConstant.Game_TILE_Y_NUM / 2) {
                    int nextPosY = posY - 1;
                    if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                        posY = nextPosY;
                    }
                } else {
                    int nextPosY = posY - 1;
                    if (WalkUtil.isWalkable(offsetTileX + posX, offsetTileY + nextPosY)) {
                        mapScreen.decreaseOffsetY();
                    }
                }
            }
        }

        direction = DIRECTION_UP;
    }




    /**
     * 传送门的不可移动，
     * 宝箱的不可移动
     * npc的移动，可以有指定移动，随机移动，etc...
     */
    public abstract void move(MapScreen mapScreen);
}
