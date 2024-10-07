package game.loader.level;

import billy.rpg.resource.level.JsonLevelSaverLoader;
import billy.rpg.resource.level.LevelData;
import billy.rpg.resource.level.LevelMetaData;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lei.liu
 * @since 2018-11-20 18:16:29
 */
public class LevelDataGenerator {

    @Test
    public void testGen() throws IOException {
        int id = 3;
        String filePath = "D:/tmp/aaa/"+id+".jlvl";
        LevelMetaData levelMetaData = new LevelMetaData();
        levelMetaData.setNumber(id);
        levelMetaData.setMaxLevel(60);
        {
            List<LevelData> levelDataList = new ArrayList<>();
            for (int i = 1; i <= 60; i++ ) {
                LevelData levelData = new LevelData(i);
                levelData.setExp(i * (10+i) *  (i-1) + 15);
                levelData.setAttack(4 * i);
                levelData.setDefend(6 * i);
                levelData.setMaxHp(4 * i);
                levelData.setMaxMp(6 * i);
                levelData.setSpeed(1 * i);
                levelDataList.add(levelData);
            }
            levelMetaData.setLevelDataList(levelDataList);
        }
        new JsonLevelSaverLoader().save(filePath, levelMetaData);
    }
}
