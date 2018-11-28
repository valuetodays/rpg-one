package billy.rpg.game.core.loader.skill;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.resource.skill.SkillMetaData;
import billy.rpg.resource.skill.SkillSaverLoader;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class SkillDataLoader {
    protected Map<Integer, SkillMetaData> skillMap = new HashMap<>();

    public abstract String getFileDir();
    public abstract String getFileExt();
    public abstract SkillSaverLoader getSaverLoader();

    public void load() throws IOException {
        final String dir = getFileDir();
        String resourcePath = AssetsUtil.getResourcePath(dir);
        File file = new File(resourcePath);
        // 只取一层文件夹
        File[] files = file.listFiles(pathname -> pathname.getName().endsWith(getFileExt()));
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("cannot find " + getFileExt() + " in " + dir);
        }

        SkillSaverLoader saverLoader = getSaverLoader();
        for (File f : files) {
            SkillMetaData skillMetaData = saverLoader.load(f.getPath());
            int number = skillMetaData.getNumber();
            SkillMetaData old = skillMap.put(number, skillMetaData);
            if (old != null) {
                throw new RuntimeException("含有重复id："+ number);
            }
        }
    }

    public Map<Integer, SkillMetaData> getSkillMap() {
        return Collections.unmodifiableMap(skillMap);
    }
}
