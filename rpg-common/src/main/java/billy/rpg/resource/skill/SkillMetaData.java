package billy.rpg.resource.skill;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-30 13:44
 */
public class SkillMetaData {
    private int number;  // 唯一编号
    private String name; // 名称
    private int type; // 类型
    private int baseDamage; // 基础伤害
    private int consume; // 消耗mp值
    private int targetType; // 目标类型
    private int animationId; // 动画id
    private String desc; // 描述

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SkillMetaData{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", baseDamage=" + baseDamage +
                ", consume=" + consume +
                ", targetType=" + targetType +
                ", animationId=" + animationId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
