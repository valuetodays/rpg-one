package game.loader.level;

import billy.rpg.resource.level.JsonLevelSaverLoader;
import billy.rpg.resource.level.LevelData;
import billy.rpg.resource.level.LevelMetaData;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-20 18:16:29
 */
public class LevelDataGenerator {

    @Test
    public void testGen() throws IOException {
        String filepath = "D:/tmp/aaa/3.jlvl";
        LevelMetaData levelMetaData = new LevelMetaData();
        levelMetaData.setNumber(1);
        levelMetaData.setMaxLevel(60);
        {
            List<LevelData> levelDataList = new ArrayList<>();
            for (int i = 1; i <= 60; i++ ) {
                LevelData levelData = new LevelData(i);
                levelData.setExp(2 * i + 15);
                levelData.setAttack(4 * i);
                levelData.setDefend(4 * i);
                levelData.setMaxHp(4 * i);
                levelData.setMaxMp(4 * i);
                levelData.setSpeed(1 * i);
                levelDataList.add(levelData);
            }
            levelMetaData.setLevelDataList(levelDataList);
        }
        new JsonLevelSaverLoader().save(filepath, levelMetaData);
    }
}
