package billy.rpg.game.core.loader;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.resource.role.RoleLoader;
import billy.rpg.resource.role.RoleMetaData;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-18 15:40
 */
public class RoleDataLoader {
    private Map<Integer, RoleMetaData> playerList = new HashMap<>();
    private Map<Integer, RoleMetaData> enemyList = new HashMap<>();

    public void load() {
        final String roleFileDirectory = "/assets/Images/character/role/";
        String resourcePath = AssetsUtil.getResourcePath(roleFileDirectory);


        File file = new File(resourcePath);
        // 只取一层文件夹
        File[] files = file.listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith(".role"));
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("没有找到*.role数据");
        }

        for (File f : files) {
            RoleMetaData rmd = RoleLoader.load(f.getPath());
            int type = rmd.getType();
            int number = rmd.getNumber();
            if (RoleMetaData.TYPE_PLAYER == type) {
                playerList.put(number, rmd);
            } else if (RoleMetaData.TYPE_ENEMY == type) {
                enemyList.put(number, rmd);
            } else {
                throw new RuntimeException("暂不支持类型为" + type + "角色");
            }
        }
    }

    public Map<Integer, RoleMetaData> getPlayerList() {
        if (playerList.isEmpty()) {
            throw new RuntimeException("没有主角玩个P啊。");
        }
        return Collections.unmodifiableMap(playerList);
    }

    public Map<Integer, RoleMetaData> getEnemyList() {
        return Collections.unmodifiableMap(enemyList);
    }
}
