package billy.rpg.game.character.battle;

import billy.rpg.resource.role.RoleMetaData;

/**
 * 战斗角色
 *  包含角色信息 RoleMetaData
 *
 * @author liulei@bshf360.com
 * @since 2017-07-18 13:53
 */
public abstract class FightableCharacter {
    RoleMetaData roleMetaData;
    private int left;
    private int top;
    private int width;
    private int height;

    /**
     * 设置角色元数据，独立出本方法是因为妖怪的属性是不变的，而玩家的属性是成长的
     * @param roleMetaData 元数据
     */
    abstract void setRoleMetaData(RoleMetaData roleMetaData);


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

    public RoleMetaData getRoleMetaData() {
        return roleMetaData;
    }



}
