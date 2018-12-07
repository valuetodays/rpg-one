package billy.rpg.resource.skill;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-30 13:44
 */
public class SkillMetaData {
    public static final int TARGET_TYPE_ALL = 0;
    public static final int TARGET_TYPE_SINGLE = 1;
    public static final int TYPE_ATTACK = 0;
    public static final int TYPE_ADD_BUFF_TO_OUR = 1;
    public static final int TYPE_ADD_BUFF_TO_ENEMY = 2;
    public static final int TYPE_ADD_HP = 3;
    private int number;  // 唯一编号
    private String name; // 名称
    private int type; // 类型 1 攻击，2给我方施加buff，3给敌方施加buff，3回复生命
    private int baseDamage; // 基础伤害
    private int consume; // 消耗mp值
    private int targetType = TARGET_TYPE_SINGLE; // 目标类型 0时为全体攻击，1为单体攻击
    private int animationId; // 动画id
    private String desc; // 描述

    private String buff;
    private int buffValue;
    private int buffRound;

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
    public String getDescWithTargetType() {
        return (targetType == TARGET_TYPE_ALL ? "【全体】": "") + desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBuff() {
        return buff;
    }

    public void setBuff(String buff) {
        this.buff = buff;
    }

    public int getBuffValue() {
        return buffValue;
    }

    public void setBuffValue(int buffValue) {
        this.buffValue = buffValue;
    }

    public int getBuffRound() {
        return buffRound;
    }

    public void setBuffRound(int buffRound) {
        this.buffRound = buffRound;
    }

}
