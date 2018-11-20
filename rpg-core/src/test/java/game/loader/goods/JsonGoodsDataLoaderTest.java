package game.loader.goods;

import billy.rpg.game.core.loader.goods.GoodsDataLoader;
import billy.rpg.game.core.loader.goods.JsonGoodsDataLoader;
import billy.rpg.resource.goods.GoodsMetaData;
import org.apache.commons.collections4.MapUtils;
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
        goodsMap.values().forEach(e -> {
            System.out.println(e.getName() + " | " + e.getDesc() + "#["+e.getBuy()+","+e.getSell()+"]");
        });
    }

}
