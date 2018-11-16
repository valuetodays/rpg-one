package billy.rpg.game.core.character.walkable;

import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MapScreen;
import billy.rpg.game.core.util.WalkUtil;
import billy.rpg.resource.map.MapMetaData;


public class HeroWalkableCharacter extends WalkableCharacter {
    private int originalPosX = 6; // 移动前的x
    private int originalPosY = 6; // 移动前的y
    private PositionEnum originalDirection = PositionEnum.DOWN; // 移动前的方向


    @Override
    public void move(GameContainer gameContainer, MapScreen mapScreen) {
        // control by player
    }

    @Override
    public void resetFrame() {
        curFrame = 1;
    }

    /**
     * move right
     *
     * @param mapScreen mapScreen
     */
    public void increaseX(GameContainer gameContainer, MapScreen mapScreen) {
        originalPosX = posX;
        originalPosY = posY;
        originalDirection = direction;
        increaseCurFrame();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        MapMetaData activeMap = gameContainer.getActiveMap();
        if (activeMap.getWidth() <= GameConstant.Game_TILE_X_NUM) {
            int nextPosX = posX + 1;
            if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                posX = nextPosX;
            }
        } else {
            if (offsetTileX <= 0) {
                if (posX < GameConstant.Game_TILE_X_NUM / 2) {
                    int nextPosX = posX + 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                        posX = nextPosX;
                    }
                } else {
                    int nextPosX = posX + 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                        mapScreen.increaseOffsetX(gameContainer);
                    }
                }
            } else if (offsetTileX < activeMap.getWidth() - GameConstant.Game_TILE_X_NUM) {
                int nextPosX = posX + 1;
                if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                    mapScreen.increaseOffsetX(gameContainer);
                }
            } else if (offsetTileX >= activeMap.getWidth() - GameConstant.Game_TILE_X_NUM) {
                if (posX >= GameConstant.Game_TILE_X_NUM / 2) {
                    int nextPosX = posX + 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                        posX = nextPosX;
                    }
                } else {
                    int nextPosX = posX + 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                        mapScreen.increaseOffsetX(gameContainer);
                    }
                }
            }
        }

        direction = PositionEnum.RIGHT;
    }

    /**
     * move left
     *
     * @param mapScreen mapScreen
     */
    public void decreaseX(GameContainer gameContainer, MapScreen mapScreen) {
        originalPosX = posX;
        originalPosY = posY;
        originalDirection = direction;
        increaseCurFrame();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        MapMetaData activeMap = gameContainer.getActiveMap();

        if (activeMap.getWidth() <= GameConstant.Game_TILE_X_NUM) {
            int nextPosX = posX - 1;
            if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
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
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                        posX = nextPosX;
                    }
                } else {
                    int nextPosX = posX - 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                        mapScreen.decreaseOffsetX(gameContainer);
                    }
                }
            } else if (offsetTileX > 0) {
                int nextPosX = posX - 1;
                if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                    mapScreen.decreaseOffsetX(gameContainer);
                }
            } else if (offsetTileX <= 0) {
                if (posX <= GameConstant.Game_TILE_X_NUM / 2) {
                    int nextPosX = posX - 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                        posX = nextPosX;
                    }
                } else {
                    int nextPosX = posX - 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + nextPosX, offsetTileY + posY)) {
                        mapScreen.decreaseOffsetX(gameContainer);
                    }
                }
            }
        }

        direction = PositionEnum.LEFT;
    }

    /**
     * move down
     *
     * @param mapScreen mapScreen
     */
    public void increaseY(GameContainer gameContainer, MapScreen mapScreen) {
        originalPosX = posX;
        originalPosY = posY;
        originalDirection = direction;
        increaseCurFrame();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        MapMetaData activeMap = gameContainer.getActiveMap();

        if (activeMap.getHeight() <= GameConstant.Game_TILE_Y_NUM) {
            int nextPosY = posY + 1;
            if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                posY = nextPosY;
            }
        } else {
            if (offsetTileY <= 0) {
                if (posY < GameConstant.Game_TILE_Y_NUM / 2) {
                    int nextPosY = posY + 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                        posY = nextPosY;
                    }
                } else {
                    int nextPosY = posY + 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                        mapScreen.increaseOffsetY(gameContainer);
                    }
                }
            } else if (offsetTileY < activeMap.getHeight() - GameConstant.Game_TILE_Y_NUM) {
                int nextPosY = posY + 1;
                if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                    mapScreen.increaseOffsetY(gameContainer);
                }
            } else if (offsetTileY >= activeMap.getHeight() - GameConstant.Game_TILE_Y_NUM) {
                if (posY >= GameConstant.Game_TILE_Y_NUM / 2) {
                    int nextPosY = posY + 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                        posY = nextPosY;
                    }
                } else {
                    int nextPosY = posY + 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                        mapScreen.increaseOffsetY(gameContainer);
                    }
                }
            }
        }

        direction = PositionEnum.DOWN;
    }

    /**
     * move up
     *
     * @param mapScreen mapScreen
     */
    public void decreaseY(GameContainer gameContainer, MapScreen mapScreen) {
        originalPosX = posX;
        originalPosY = posY;
        originalDirection = direction;
        increaseCurFrame();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        MapMetaData activeMap = gameContainer.getActiveMap();

        if (activeMap.getHeight() <= GameConstant.Game_TILE_Y_NUM) {
            int nextPosY = posY - 1;
            if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                posY = nextPosY;
            }
        } else {
            if (offsetTileY >= activeMap.getHeight() - GameConstant.Game_TILE_Y_NUM) {
                if (posY > GameConstant.Game_TILE_Y_NUM / 2) {
                    int nextPosY = posY - 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                        posY = nextPosY;
                    }
                } else {
                    int nextPosY = posY - 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                        mapScreen.decreaseOffsetY(gameContainer);
                    }
                }
            } else if (offsetTileY > 0) {
                int nextPosY = posY - 1;
                if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                    mapScreen.decreaseOffsetY(gameContainer);
                }
            } else if (offsetTileY <= 0) {
                // 当从三清宫回到百草地时，不加后面的+1导致不能从百草地的下半地图走至上半地图
                if (posY <= GameConstant.Game_TILE_Y_NUM / 2 + 1) {
                    int nextPosY = posY - 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                        posY = nextPosY;
                    }
                } else {
                    int nextPosY = posY - 1;
                    if (WalkUtil.isWalkable(gameContainer, offsetTileX + posX, offsetTileY + nextPosY)) {
                        mapScreen.decreaseOffsetY(gameContainer);
                    }
                }
            }
        }

        direction = PositionEnum.UP;
    }


    public String toString(GameContainer gameContainer) {
        return "[posX=" + getPosX() + ", posY=" + getPosY() + ", nextPosX="
                + getNextPosX(gameContainer) + ", nextPosY=" + getNextPosY(gameContainer)
                + ",dir=" + getDirection() + ",preDir="+originalDirection+"]";
    }

}

