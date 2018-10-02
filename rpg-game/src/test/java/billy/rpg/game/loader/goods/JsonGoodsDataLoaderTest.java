package billy.rpg.game.loader.goods;

import billy.rpg.resource.goods.GoodsMetaData;
import org.apache.commons.collections.MapUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author liulei-home
 * @since 2018-10-03 01:17
 */
public class JsonGoodsDataLoaderTest {
    @Test
    public void load() throws Exception {
        GoodsDataLoader goodsDataLoader = new JsonGoodsDataLoader();
        goodsDataLoader.load();
        Map<Integer, GoodsMetaData> goodsMap = goodsDataLoader.getGoodsMap();
        Assert.assertTrue(MapUtils.isNotEmpty(goodsMap));
    }

}
