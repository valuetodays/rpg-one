package billy.rpg.game.loader.goods;

import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsSaverLoader0;

import java.io.IOException;
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

    public abstract GoodsSaverLoader0 getSaverLoader();
    public abstract void load() throws IOException;

    public Map<Integer, GoodsMetaData> getGoodsMap() {
        if (!goodsMap.containsKey(EMPTY_GOODS_INDEX)) {
            // 一个空的goods，各属性均为0
            goodsMap.put(EMPTY_GOODS_INDEX, new GoodsMetaData());
        }
        return Collections.unmodifiableMap(goodsMap);
    }
}
