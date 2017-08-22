package billy.rpg.game.character;

import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.MapScreen;
import billy.rpg.game.util.WalkUtil;
import billy.rpg.resource.map.MapMetaData;


public class HeroCharacter extends BaseCharacter {
    private int curFrame;  // 步数
    private int direction; // 方向


    public int getNextPosX() {
        MapScreen mapScreen = GameFrame.getInstance().getGameContainer().getMapScreen();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        return posX + ((direction == DIRECTION_LEFT && posX > 0) ? -1 :
                ((direction == DIRECTION_RIGHT && (posX + offsetTileX)< getWidth() - 1) ? 1 : 0)
        );
    }

    public int getNextPosY() {
        MapScreen mapScreen = GameFrame.getInstance().getGameContainer().getMapScreen();
        int offsetTileX = mapScreen.getOffsetTileX();
        int offsetTileY = mapScreen.getOffsetTileY();
        return posY + ((direction == DIRECTION_UP && posY > 0) ? -1 :
                ((direction == DIRECTION_DOWN && (posY+offsetTileY) < getHeight() - 1) ? 1 : 0 )
        );
    }

    public void initPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    private void increaseCurFrame() {
        curFrame++;
        if (curFrame == 3) {
            curFrame = 0;
        }
    }

    public int getCurFrame() {
        return curFrame;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
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
        if (offsetTileX <= 0) {
            if (posX < GameConstant.Game_TILE_X_NUM / 2) {
                int nextPosX = posX + 1;
                if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY +posY)) {
                    posX = nextPosX;
                }
            } else {
                int nextPosX = posX + 1;
                if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY +posY)) {
                    mapScreen.increaseOffsetX();
                }
            }
        } else if (offsetTileX < activeMap.getWidth() - GameConstant.Game_TILE_X_NUM) {
            int nextPosX = posX + 1;
            if (WalkUtil.isWalkable(offsetTileX + nextPosX, offsetTileY +posY)) {
                mapScreen.increaseOffsetX();
            }
        } else if (offsetTileX >= activeMap.getWidth() - GameConstant.Game_TILE_X_NUM) {
            if (posX >= GameConstant.Game_TILE_X_NUM/2) {
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

        // 如下有注意点：
        // 当人物在右半屏幕且大地图在右半部分时，offsetTileX的值是大于(大地图宽度-一屏纵向Tile数的)，
        // 并且该值也是大于0的
        // 所以，千万不要把下面的第二个if放在第一个之前
        if (offsetTileX >= activeMap.getWidth() - GameConstant.Game_TILE_X_NUM) {
            if (posX > GameConstant.Game_TILE_X_NUM/2) {
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
            if (posX <= GameConstant.Game_TILE_X_NUM/2) {
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

        if (offsetTileY <= 0) {
            if (posY < GameConstant.Game_TILE_Y_NUM/2) {
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
            if (posY >= GameConstant.Game_TILE_Y_NUM/2) {
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

        if (offsetTileY >= activeMap.getHeight() - GameConstant.Game_TILE_Y_NUM) {
            if (posY > GameConstant.Game_TILE_Y_NUM/2) {
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
            if (posY <= GameConstant.Game_TILE_Y_NUM/2) {
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

        direction = DIRECTION_UP;
    }

    @Override
    public String toString() {
        return "[w/h=" + width + "/" + height + ", posX=" + posX + ", posY=" + posY + ", nextPosX="
                + getNextPosX() + ", nextPosY=" + getNextPosY() + ",dir=" + direction + "]";
    }

    public int getDirection() {
        return direction;
    }

    private long lastTime = System.currentTimeMillis();

    @Override
    public void move(MapScreen mapScreen) {
        long now = System.currentTimeMillis();
        if (now - lastTime < 500) {
            return ;
        }
        lastTime = now;
        int i = GameConstant.random.nextInt(8 << 2);
        if (i % 7 == 1) {
            decreaseX(mapScreen);
        }
        if (i % 7 == 2) {
            decreaseY(mapScreen);
        }
        if (i % 7 == 3) {
            increaseX(mapScreen);
        }
        if (i % 7 == 3) {
            increaseY(mapScreen);
        }
        if (i % 2 == 0) {
            increaseCurFrame();
        }

    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}

