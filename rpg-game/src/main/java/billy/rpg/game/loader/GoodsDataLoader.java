package billy.rpg.game.loader;

import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsSaverLoader;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * to load goods, such as sword, accessory
 *
 * @author liulei-frx
 * 
 * @since 2016-12-02 09:15:16
 */
public class GoodsDataLoader {
    private Map<Integer, GoodsMetaData> goodsMap = new HashMap<>();

    public void load() {
        final String dir = "/goods/";
        URL resource = RoleDataLoader.class.getResource(dir);

        File file = new File(resource.getPath());
        // 只取一层文件夹
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".gds");
            }
        });
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("cannot find *.gds");
        }

        for (File f : files) {
            GoodsMetaData gmd = GoodsSaverLoader.load(f.getPath());
            int number = gmd.getNumber();
            goodsMap.put(number, gmd);
        }
    }


    public Map<Integer, GoodsMetaData> getGoodsMap() {
        return Collections.unmodifiableMap(goodsMap);
    }

}
