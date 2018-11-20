package billy.rpg.game.core.character.fightable;

import billy.rpg.resource.role.RoleMetaData;

import java.awt.*;

/**
 * @author liulei-home
 * @since 2018-10-03 14:12
 */
public abstract class Fightable {
    // roleMetaData中有角色图片，但主角并不使用它，主角使用的是下面的battleImage
    protected RoleMetaData roleMetaData;
    private int left;
    private int top;
    private int width;
    private int height;
    private boolean died;
    private Image battleImage; // 一帧
    // 普通攻击的第几帧
    private int acctackFrame;
    // 格挡普通攻击时的防御动作，此时可格档50%的伤害（暂只有一帧）
    private int defendFrame;
    // TODO 被（普通？）攻击时的帧画面
    // 永不死亡的设置
    private boolean canDie; // false -> not die

    /**
     * 设置角色元数据，独立出本方法是因为妖怪的属性是不变的，而玩家的属性是成长的
     * @param roleMetaData 元数据
     */
    protected abstract void setRoleMetaData(RoleMetaData roleMetaData);

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

    public boolean isDied() {
        return died;
    }

    public void setDied(boolean died) {
        this.died = died;
    }

    public Image getBattleImage() {
        return battleImage;
    }

    public void setBattleImage(Image battleImage) {
        this.battleImage = battleImage;
    }

    public int getAcctackFrame() {
        return acctackFrame;
    }

    public void setAcctackFrame(int acctackFrame) {
        this.acctackFrame = acctackFrame;
    }

    public int getDefendFrame() {
        return defendFrame;
    }

}
