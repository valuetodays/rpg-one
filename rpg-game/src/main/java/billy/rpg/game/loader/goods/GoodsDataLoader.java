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
    protected Map<Integer, GoodsMetaData> goodsMap = new HashMap<>();

    public abstract GoodsSaverLoader0 getSaverLoader();
    public abstract void load() throws IOException;

    public Map<Integer, GoodsMetaData> getGoodsMap() {
        return Collections.unmodifiableMap(goodsMap);
    }
}
