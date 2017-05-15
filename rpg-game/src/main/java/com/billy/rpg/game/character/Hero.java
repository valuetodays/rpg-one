package com.billy.rpg.game.character;

import com.billy.rpg.game.constants.CharacterConstant;
import com.billy.rpg.game.util.WalkUtil;


public class Hero implements CharacterConstant {
    private int height = 5; // 当前地图的高
    private int width = 5;  // 当前地图的宽
    private int posX = 6; // 当前x
    private int posY = 6; // 当前y
    private int nextPosX; // 下一步x
    private int nextPosY; // 下一步y
    private int curFrame;  // 步数
    private int direction; // 方向


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
        return nextPosX;
    }

    public int getNextPosY() {
        return nextPosY;
    }

    public void initPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.nextPosX = -1;
        this.nextPosY = -1;
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

    public void increaseX() {
        if (direction == DIRECTION_RIGHT) {
            increaseCurFrame();
        }
        direction = DIRECTION_RIGHT;
        nextPosX = posX + 1;
        if ((posX < width - 1) && WalkUtil.isWalkable(posX + 1, posY)) {
            posX++;
        }
    }

    public void decreaseX() {
        if (direction == DIRECTION_LEFT) {
            increaseCurFrame();
        }
        direction = DIRECTION_LEFT;
        nextPosX = posX - 1;
        if (posX > 0 && WalkUtil.isWalkable(posX - 1, posY)) {
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
        nextPosY = posY + 1;
        if (posY < height - 1 && WalkUtil.isWalkable(posX, posY + 1)) {
            posY++;
        }
    }

    public void decreaseY() {
        if (direction == DIRECTION_UP) {
            increaseCurFrame();
        }
        direction = DIRECTION_UP;
        nextPosY = posY - 1;
        if (posY > 0 && WalkUtil.isWalkable(posX, posY - 1)) {
            posY--;
        }
    }

    @Override
    public String toString() {
        return "[height=" + height + ", width=" + width + ", posX=" + posX + ", posY=" + posY + ", nextPosX="
                + nextPosX + ", nextPosY=" + nextPosY + ", curFrame=" + curFrame + ", direction=" + direction + "]";
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


}
