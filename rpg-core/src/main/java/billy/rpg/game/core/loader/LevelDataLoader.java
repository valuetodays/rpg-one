package billy.rpg.game.core.loader;

import billy.rpg.resource.level.LevelLoader;
import billy.rpg.resource.level.LevelMetaData;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-26 21:40
 */
public class LevelDataLoader {
    private Map<Integer, LevelMetaData> levleMap = new HashMap<>();

    public void load() {
        final String levelFileDirectory = "/level/";
        URL resource = LevelDataLoader.class.getResource(levelFileDirectory);

        File file = new File(resource.getPath());
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".lvl");
            }
        });
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("没有找到*.lvl数据");
        }

        for (File f : files) {
            LevelMetaData lmd = LevelLoader.load(f.getPath());
            levleMap.put(lmd.getNumber(), lmd);
        }
    }


    public Map<Integer, LevelMetaData> getLevleMap() {
        return Collections.unmodifiableMap(levleMap);
    }
}
