package billy.rpg.game.core.screen.window;

import java.awt.Graphics;

public abstract class BaseWindow {
    private int left;
    private int top;
    private int width;
    private int height;
    private int z = 1;

    public BaseWindow() {
    }

    public BaseWindow(int left, int top) {
        this.left = left;
        this.top = top;
    }

    public abstract void update(Graphics g);

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

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

}

