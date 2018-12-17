package billy.rpg.game.core.callback;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.goods.GoodsMetaData;

/**
 * 使用物品
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-05 17:06:21
 */
public interface GoodsUseCallback {
    void onUse(GameContainer gameContainer, GoodsMetaData goods);
}
