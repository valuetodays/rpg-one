package billy.rpg.game.character.battle;

import billy.rpg.resource.role.RoleMetaData;

import java.awt.*;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-12 16:37
 */
public class HeroBattle {
    private RoleMetaData monster;
    private Image image;
    private int left;
    private int top;
    private int width;
    private int height;

    public RoleMetaData getMonster() {
        return monster;
    }

    public void setMonster(RoleMetaData monster) {
        this.monster = monster.clone();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

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
}
