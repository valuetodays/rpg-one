package billy.rpg.game.loader;

import billy.rpg.resource.skill.SkillLoader;
import billy.rpg.resource.skill.SkillMetaData;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-31 09:34
 */
public class SkillDataLoader {
    private Map<Integer, SkillMetaData> skillMap = new HashMap<>();

    public void load() {
        final String roleFileDirectory = "/skill/";
        URL resource = RoleDataLoader.class.getResource(roleFileDirectory);

        File file = new File(resource.getPath());
        // 只取一层文件夹
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".skl");
            }
        });
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("cannot find *.skl");
        }

        for (File f : files) {
            SkillMetaData smd = SkillLoader.load(f.getPath());
            int number = smd.getNumber();
            skillMap.put(number, smd);
        }
    }

    public Map<Integer, SkillMetaData> getSkillMap() {
        return Collections.unmodifiableMap(skillMap);
    }
}
