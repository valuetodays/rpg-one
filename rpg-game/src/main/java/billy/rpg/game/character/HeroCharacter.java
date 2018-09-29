package billy.rpg.game.character;

import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.MapScreen;
import billy.rpg.game.util.WalkUtil;
import billy.rpg.resource.map.MapMetaData;


public class HeroCharacter extends BaseCharacter {

    private int originalPosX = 6; // 移动前的x
    private int originalPosY = 6; // 移动前的y
    private PositionEnum originalDirection = PositionEnum.DOWN; // 移动前的方向

    @Override
    public void move(MapScreen mapScreen) {
        // controll by player
    }

    @Override
    public void resetFrame() {
        curFrame = 1;
    }

    /**
     * get next posX
     * @return nextPosX
     */
    @Override
    public int getNextPosX() {
        int mapWidth = GameFrame.getInstance().getGameContainer().getActiveMap().getWidth();
        return originalPosX + ((direction == PositionEnum.LEFT && originalPosX > 0) ? -1 :
                ((originalDirection == PositionEnum.RIGHT && (originalPosX) < mapWidth - 1) ? 1 : 0)
        );
    }

    /**
     * get next posY
     * @return nextPosY
     */
    @Override
    public int getNextPosY() {
        int mapHeight = GameFrame.getInstance().getGameContainer().getActiveMap().getHeight();
        return originalPosY + ((direction == PositionEnum.UP && originalPosY > 0) ? -1 :
                ((originalDirection == PositionEnum.DOWN && (originalPosY) < mapHeight - 1) ? 1 : 0 )
        );
    }


    /**
     * move right
     * @param mapScreen mapScreen
     */
    public void increaseX(MapScreen mapScreen) {
        originalPosX = posX;
        originalPosY = posY;
        originalDirection = direction;
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

        direction = PositionEnum.RIGHT;
    }

    /**
     * move left
     * @param mapScreen mapScreen
     */
    public void decreaseX(MapScreen mapScreen) {
        originalPosX = posX;
        originalPosY = posY;
        originalDirection = direction;
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

        direction = PositionEnum.LEFT;
    }

    /**
     * move down
     * @param mapScreen mapScreen
     */
    public void increaseY(MapScreen mapScreen) {
        originalPosX = posX;
        originalPosY = posY;
        originalDirection = direction;
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

        direction = PositionEnum.DOWN;
    }

    /**
     * move up
     * @param mapScreen mapScreen
     */
    public void decreaseY(MapScreen mapScreen) {
        originalPosX = posX;
        originalPosY = posY;
        originalDirection = direction;
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
                // 当从三清宫回到百草地时，不加后面的+1导致不能从百草地的下半地图走至上半地图
                if (posY <= GameConstant.Game_TILE_Y_NUM / 2+1) {
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

        direction = PositionEnum.UP;
    }


    @Override
    public String toString() {
        return "[posX=" + getPosX() + ", posY=" + getPosY() + ", nextPosX="
                + getNextPosX() + ", nextPosY=" + getNextPosY() + ",dir=" + getDirection() + "]";
    }

}

