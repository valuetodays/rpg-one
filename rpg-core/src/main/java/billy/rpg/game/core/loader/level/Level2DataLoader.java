package billy.rpg.game.core.loader.level;

import billy.rpg.resource.level.LevelMetaData;
import billy.rpg.resource.level.LevelSaverLoader;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liulei-home
 * @since 2018-10-03 00:40
 */
public abstract class Level2DataLoader {
    protected Map<Integer, LevelMetaData> levelMap = new HashMap<>();

    public abstract String getFileDir();
    public abstract String getFileExt();
    public abstract LevelSaverLoader getSaverLoader();

    public void load() throws IOException {
        final String dir = getFileDir();
        URL resource = getClass().getResource(dir);
        File file = new File(resource.getPath());
        // 只取一层文件夹
        File[] files = file.listFiles(pathname -> pathname.getName().endsWith(getFileExt()));
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("cannot find " + getFileExt() + " in " + getFileDir());
        }

        LevelSaverLoader saverLoader = getSaverLoader();
        for (File f : files) {
            LevelMetaData levelMetaData = saverLoader.load(f.getPath());
            int number = levelMetaData.getNumber();
            levelMap.put(number, levelMetaData);
        }
    }

    public Map<Integer, LevelMetaData> getLevelMap() {
        return Collections.unmodifiableMap(levelMap);
    }

}
