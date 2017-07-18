package billy.rpg.resource.monster;

import billy.rpg.resource.role.RoleLoader;
import billy.rpg.resource.role.RoleMetaData;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-11 10:21
 */
public class MonsterDataLoader {
    private MonsterMetaData monsterMetaData;


    public MonsterMetaData load() {
        monsterMetaData = new MonsterMetaData();
        Map<Integer, RoleMetaData> monsterMap = new HashMap<>();
        //
        final String monsterPath = "/Images/character/monster/";
        URL resource = MonsterDataLoader.class.getResource(monsterPath);

            File file = new File(resource.getPath());
            // little
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(".role"); // 只取一层文件夹
                }
            });
            if (ArrayUtils.isEmpty(files)) {
                throw new RuntimeException("没有找到*.role数据");
            }

            for (File f : files) {
                RoleMetaData rmd = RoleLoader.load(f.getPath());
                monsterMap.put(rmd.getNumber(), rmd);
            }

            monsterMetaData.setMonsterMap(monsterMap);


        if (monsterMetaData == null) {
            throw new RuntimeException("加载*.role出错");
        }

        return monsterMetaData;
    }

    public MonsterMetaData getMonsterMetaData() {
        return monsterMetaData;
    }
}
