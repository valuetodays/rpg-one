package billy.rpg.game.listener;

import billy.rpg.resource.goods.GoodsMetaData;

/**
 * 使用物品
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-05 17:06:21
 */
public interface GoodsUseListener {
    void onUse(GoodsMetaData goods);
}
