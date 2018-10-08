package billy.rpg.game.character.walkable;

import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.WalkableConstant;
import billy.rpg.game.screen.MapScreen;

/**
 *
 * @author liulei
 * @since 2017-05-18 14:04
 */
public abstract class WalkableCharacter implements WalkableConstant {
    protected int number; // 编号
    protected int posX = 6; // 当前x
    protected int posY = 6; // 当前y
    protected int curFrame;  // 步数 0右，1停止，2左
    protected WalkableConstant.PositionEnum direction; // 方向 0下，1左，2右，3上

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

    public WalkableConstant.PositionEnum getDirection() {
        return direction;
    }

    public void setDirection(WalkableConstant.PositionEnum direction) {
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
        return posX + ((direction == PositionEnum.LEFT && posX > 0) ? -1 :
                ((direction == PositionEnum.RIGHT && (posX) < mapWidth - 1) ? 1 : 0)
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
        return posY + ((direction == PositionEnum.UP && posY > 0) ? -1 :
                ((direction == PositionEnum.DOWN && (posY) < mapHeight - 1) ? 1 : 0 )
        );
    }



    /**
     * 传送门的不可移动，
     * 宝箱的不可移动
     * npc的移动，可以有指定移动，随机移动，etc...
     */
    public abstract void move(MapScreen mapScreen);

    public void resetFrame() { }

}
