package billy.rpg.resource.level;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-25 17:11
 */
public class LevelData {
    private Integer level;
    private Integer maxHp;
    private Integer maxMp;
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
        return level != null && maxHp != null && maxMp != null && attack != null && defend != null && speed != null && exp != null;
    }
}
