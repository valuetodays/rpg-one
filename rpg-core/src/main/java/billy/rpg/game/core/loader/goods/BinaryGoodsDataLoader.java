package billy.rpg.game.core.loader.goods;

import billy.rpg.resource.goods.BinaryGoodsSaverLoader;
import billy.rpg.resource.goods.GoodsSaverLoader;

/**
 * to load goods, such as sword, accessory
 *
 * @author liulei-frx
 * 
 * @since 2016-12-02 09:15:16
 */
public class BinaryGoodsDataLoader extends GoodsDataLoader {

    @Override
    public String getFileDir() {
        return "/assets/goods/binary/";
    }

    @Override
    public String getFileExt() {
        return ".gds";
    }

    @Override
    public GoodsSaverLoader getSaverLoader() {
        return new BinaryGoodsSaverLoader();
    }

}
