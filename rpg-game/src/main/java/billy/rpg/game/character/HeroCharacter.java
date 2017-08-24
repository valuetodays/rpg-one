package billy.rpg.game.character;

import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.MapScreen;
import billy.rpg.game.util.WalkUtil;
import billy.rpg.resource.map.MapMetaData;


public class HeroCharacter extends BaseCharacter {

    @Override
    public void move(MapScreen mapScreen) {
        // control by player
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



    @Override
    public String toString() {
        return "[posX=" + getPosX() + ", posY=" + getPosY() + ", nextPosX="
                + getNextPosX() + ", nextPosY=" + getNextPosY() + ",dir=" + getDirection() + "]";
    }

}

