package billy.rpg.resource.skill;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-30 13:44
 */
public class SkillMetaData {
    private int number;  // 唯一编号
    private String name; // 名称
    private String desc; // 描述
    private int consume; // 消耗mp值
    private int baseDamage; // 基础伤害

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }
}
