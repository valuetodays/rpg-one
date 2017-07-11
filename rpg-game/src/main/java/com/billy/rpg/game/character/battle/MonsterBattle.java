package com.billy.rpg.game.character.battle;

import java.awt.*;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-11 12:08
 */
public class MonsterBattle {
    private Image image;
    private int left;
    private int top;
    private int width;
    private int height;

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
