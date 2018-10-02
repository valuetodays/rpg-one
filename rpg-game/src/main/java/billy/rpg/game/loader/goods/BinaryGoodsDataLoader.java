package billy.rpg.game.loader.goods;

import billy.rpg.resource.goods.BinaryGoodsSaverLoader;
import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsSaverLoader0;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * to load goods, such as sword, accessory
 *
 * @author liulei-frx
 * 
 * @since 2016-12-02 09:15:16
 */
public class BinaryGoodsDataLoader extends GoodsDataLoader {

    @Override
    public GoodsSaverLoader0 getSaverLoader() {
        return new BinaryGoodsSaverLoader();
    }

    public void load() throws IOException {
        final String dir = "/goods/binary/";
        URL resource = BinaryGoodsDataLoader.class.getResource(dir);

        File file = new File(resource.getPath());
        // 只取一层文件夹
        File[] files = file.listFiles(pathname -> pathname.getName().endsWith(".gds"));
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("cannot find *.gds");
        }

        for (File f : files) {
            GoodsMetaData gmd = getSaverLoader().load(f.getPath());
//            try {
//            File jsonFile = new File(f.getAbsolutePath() + ".json");
//            FileUtils.write(jsonFile, JSON.toJSONString(gmd, SerializerFeature.PrettyFormat), "utf-8", false);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            int number = gmd.getNumber();
            goodsMap.put(number, gmd);
        }
    }


}
