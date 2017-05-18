package com.billy.rpg.game.character;

import com.billy.rpg.game.constants.CharacterConstant;

/**
 *
 * @author liulei
 * @since 2017-05-18 14:04
 */
public abstract class BaseCharacter implements CharacterConstant {
    protected int height = 5; // 当前地图的高
    protected int width = 5;  // 当前地图的宽
    protected int posX = 6; // 当前x
    protected int posY = 6; // 当前y

    protected int getPosX() {
        return posX;
    }

    protected int getPosY() {
        return posY;
    }

    protected void setPosX(int posX) {
        this.posX = posX;
    }

    protected void setPosY(int posY) {
        this.posY = posY;
    }

    protected int getHeight() {
        return height;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    protected int getWidth() {
        return width;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    /**
     * 传送门的不可移动，
     * 宝箱的不可移动
     * npc的移动，可以有指定移动，随机移动，etc...
     */
    public abstract void move();
}
