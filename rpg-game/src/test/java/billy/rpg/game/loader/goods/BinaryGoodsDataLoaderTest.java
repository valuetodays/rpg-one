package billy.rpg.game.loader.goods;

import billy.rpg.resource.goods.GoodsMetaData;
import org.junit.Test;

import java.util.Map;

/**
 * @author liulei-home
 * @since 2018-10-03 00:24
 */
public class BinaryGoodsDataLoaderTest {
    @Test
    public void load() throws Exception {
        BinaryGoodsDataLoader b = new BinaryGoodsDataLoader();
        b.load();
        Map<Integer, GoodsMetaData> goodsMap = b.getGoodsMap();
        System.out.println(goodsMap.values());
    }

}