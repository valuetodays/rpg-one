package billy.rpg.game.character;

import billy.rpg.game.constants.CharacterConstant;

/**
 *
 * @author liulei
 * @since 2017-05-18 14:04
 */
public abstract class BaseCharacter implements CharacterConstant {
    public int number; // 编号
    public int height = 5; // 当前地图的高
    public int width = 5;  // 当前地图的宽
    public int posX = 6; // 当前x
    public int posY = 6; // 当前y

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    /**
     * 传送门的不可移动，
     * 宝箱的不可移动
     * npc的移动，可以有指定移动，随机移动，etc...
     */
    public abstract void move();
}
