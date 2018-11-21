package billy.rpg.resource.level;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-25 17:11
 */
@JSONType(ignores = {"isValid", "valid"})
public class LevelData {
    private int level;
    private int maxHp;
    private int maxMp;
    private Integer attack;
    private Integer defend;
    private Integer speed;
    private Integer exp;

    public LevelData() {
    }

    public LevelData(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(Integer maxHp) {
        this.maxHp = maxHp;
    }

    public Integer getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(Integer maxMp) {
        this.maxMp = maxMp;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefend() {
        return defend;
    }

    public void setDefend(Integer defend) {
        this.defend = defend;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "LevelData{" +
                "level=" + level +
                ", maxHp=" + maxHp +
                ", maxMp=" + maxMp +
                ", attack=" + attack +
                ", defend=" + defend +
                ", exp=" + exp +
                '}';
    }

    public boolean isValid() {
        return level != 0 && maxHp != 0 && maxMp != 0 && attack != 0 && defend != 0 && speed != 0 && exp != 0;
    }
}
