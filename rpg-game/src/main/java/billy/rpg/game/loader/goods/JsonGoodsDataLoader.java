package billy.rpg.game.loader.goods;

import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsSaverLoader0;
import billy.rpg.resource.goods.JsonGoodsSaverLoader;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * use this for debugging
 *
 * @author liulei-home
 * @since 2018-10-03 00:12
 */
public class JsonGoodsDataLoader extends GoodsDataLoader {

    @Override
    public GoodsSaverLoader0 getSaverLoader() {
        return new JsonGoodsSaverLoader();
    }

    public void load() throws IOException {
        final String dir = "/goods/";
        URL resource = BinaryGoodsDataLoader.class.getResource(dir);

        File file = new File(resource.getPath());
        // 只取一层文件夹
        File[] files = file.listFiles(pathname -> pathname.getName().endsWith(".gds.json"));
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("cannot find *.gds.json");
        }

        for (File f : files) {
            GoodsMetaData gmd = getSaverLoader().load(f.getPath());
            int number = gmd.getNumber();
            goodsMap.put(number, gmd);
        }
    }

}
