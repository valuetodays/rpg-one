package billy.rpg.resource.level;

import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-26 09:22
 */
public class LevelMetaData {
    private int number; // 玩家id
    private int maxLevel;
    private List<LevelData> levelDataList;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public List<LevelData> getLevelDataList() {
        return levelDataList;
    }

    public void setLevelDataList(List<LevelData> levelDataList) {
        this.levelDataList = levelDataList;
    }
}
