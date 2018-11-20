package game.loader.goods;

import billy.rpg.game.core.loader.goods.BinaryGoodsDataLoader;
import billy.rpg.game.core.loader.goods.GoodsDataLoader;
import billy.rpg.resource.goods.GoodsMetaData;
import org.apache.commons.collections4.MapUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author liulei-home
 * @since 2018-10-03 00:24
 */
public class BinaryGoodsDataLoaderTest {
    @Test
    public void load() throws Exception {
        GoodsDataLoader goodsDataLoader = new BinaryGoodsDataLoader();
        goodsDataLoader.load();
        Map<Integer, GoodsMetaData> goodsMap = goodsDataLoader.getGoodsMap();
        Assert.assertTrue(MapUtils.isNotEmpty(goodsMap));
    }

}