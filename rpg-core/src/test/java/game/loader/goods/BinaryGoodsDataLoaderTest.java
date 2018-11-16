package game.loader.goods;

import org.apache.commons.collections.MapUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import billy.rpg.resource.goods.GoodsMetaData;

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