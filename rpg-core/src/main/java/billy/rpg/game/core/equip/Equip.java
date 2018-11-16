package billy.rpg.game.core.equip;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.goods.GoodsMetaData;

/**
 * @author liulei-home
 * @since 2018-10-03 13:54
 */
public abstract class Equip {
    protected final GoodsMetaData goods;

    public Equip(GameContainer gameContainer, int index) {
        this.goods = gameContainer.getGoodsMetaDataOf(index);
    }

    public GoodsMetaData getGoods() {
        return goods;
    }
}
