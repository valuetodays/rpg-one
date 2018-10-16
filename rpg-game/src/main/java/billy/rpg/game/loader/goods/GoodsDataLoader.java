package billy.rpg.game.loader.goods;

import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsSaverLoader0;
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
public abstract class GoodsDataLoader {
    public static int EMPTY_GOODS_INDEX = GoodsMetaData.EMPTY_GOODS_INDEX;
    protected Map<Integer, GoodsMetaData> goodsMap = new HashMap<>();

    public abstract String getFileDir();
    public abstract String getFileExt();
    public abstract GoodsSaverLoader0 getSaverLoader();

    public void load() throws IOException {
        final String dir = getFileDir();
        URL resource = getClass().getResource(dir);
        File file = new File(resource.getPath());
        // 只取一层文件夹
        File[] files = file.listFiles(pathname -> pathname.getName().endsWith(getFileExt()));
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("cannot find " + getFileExt() + " in " + getFileDir());
        }

        for (File f : files) {
            GoodsMetaData gmd = getSaverLoader().load(f.getPath());
            int number = gmd.getNumber();
            goodsMap.put(number, gmd);
        }
    }

    public Map<Integer, GoodsMetaData> getGoodsMap() {
        if (!goodsMap.containsKey(EMPTY_GOODS_INDEX)) {
            // 一个空的goods，各属性均为0
            goodsMap.put(EMPTY_GOODS_INDEX, new GoodsMetaData());
        }
        return Collections.unmodifiableMap(goodsMap);
    }
}
