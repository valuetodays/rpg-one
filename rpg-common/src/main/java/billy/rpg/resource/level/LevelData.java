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
    private int attack;
    private int defend;
    private int speed;
    private int exp;

    public LevelData() {
    }

    public LevelData(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
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
