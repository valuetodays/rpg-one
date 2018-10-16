package billy.rpg.game.loader.goods;

import billy.rpg.resource.goods.GoodsSaverLoader0;
import billy.rpg.resource.goods.JsonGoodsSaverLoader;

/**
 * use this for debugging
 *
 * @author liulei-home
 * @since 2018-10-03 00:12
 */
public class JsonGoodsDataLoader extends GoodsDataLoader {

    @Override
    public String getFileDir() {
        return "/goods/json/";
    }

    @Override
    public String getFileExt() {
        return ".gds.json";
    }

    @Override
    public GoodsSaverLoader0 getSaverLoader() {
        return new JsonGoodsSaverLoader();
    }

}
