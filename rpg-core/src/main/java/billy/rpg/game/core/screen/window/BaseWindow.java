package billy.rpg.game.core.screen.window;

import billy.rpg.game.core.screen.BaseScreen;

import java.awt.Graphics;

public abstract class BaseWindow {
    protected int left;
    protected int right;
    protected int width;
    protected int height;
    protected int z = 1;

    public abstract void update(Graphics g);

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
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

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

}

