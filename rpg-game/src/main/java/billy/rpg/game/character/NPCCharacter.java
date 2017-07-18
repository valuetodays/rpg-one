package billy.rpg.game.character;

import billy.rpg.game.util.WalkUtil;

public abstract class NPCCharacter extends BaseCharacter {
    private int curFrame;  // 步数
    private int direction; // 方向
    private int tileNum; // for npc


    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getPosX() {
        return posX;
    }
    public int getNextPosX() {
        return posX + (direction == DIRECTION_LEFT ? -1 : (direction == DIRECTION_RIGHT ? 1 : 0 ));
    }

    public int getNextPosY() {
        return posY + (direction == DIRECTION_UP ? -1 : (direction == DIRECTION_DOWN ? 1 : 0 ));
    }

    public void initPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    protected void increaseCurFrame() {
        curFrame++;
        if (curFrame == 3) {
            curFrame = 0;
        }
    }

    public int getCurFrame() {
        return curFrame;
    }

    public void increaseX() {
        if (direction == DIRECTION_RIGHT) {
            increaseCurFrame();
        }
        direction = DIRECTION_RIGHT;
        int nextPosX = posX + 1;
        if ((nextPosX < width - 1) && WalkUtil.isWalkable(posX + 1, posY)) {
            posX++;
        }
    }

    public void decreaseX() {
        if (direction == DIRECTION_LEFT) {
            increaseCurFrame();
        }
        direction = DIRECTION_LEFT;
        int nextPosX = posX - 1;
        if (nextPosX > 0 && WalkUtil.isWalkable(posX - 1, posY)) {
            posX--;
        }
    }

    public int getPosY() {
        return posY;
    }

    public void increaseY() {
        if (direction == DIRECTION_DOWN) {
            increaseCurFrame();
        }
        direction = DIRECTION_DOWN;
        int nextPosY = posY + 1;
        if (nextPosY < height - 1 && WalkUtil.isWalkable(posX, posY + 1)) {
            posY++;
        }
    }

    public void decreaseY() {
        if (direction == DIRECTION_UP) {
            increaseCurFrame();
        }
        direction = DIRECTION_UP;
        int nextPosY = posY - 1;
        if (nextPosY > 0 && WalkUtil.isWalkable(posX, posY - 1)) {
            posY--;
        }
    }

    @Override
    public String toString() {
        return "[height=" + height + ", width=" + width + ", posX=" + posX + ", posY=" + posY + ", nextPosX="
                + getNextPosX() + ", nextPosY=" + getNextPosY() + "]";
    }

    public int getDirection() {
        return direction;
    }

    public boolean isX() {
        return (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT);
    }

    public boolean isY() {
        return (direction == DIRECTION_UP || direction == DIRECTION_DOWN);
    }

    public int getTileNum() {
        return tileNum;
    }

    public void setTileNum(int tileNum) {
        this.tileNum = tileNum;
    }

}

