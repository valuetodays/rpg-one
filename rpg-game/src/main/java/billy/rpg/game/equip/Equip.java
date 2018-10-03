package billy.rpg.game.equip;

import billy.rpg.resource.goods.GoodsMetaData;

/**
 * @author liulei-home
 * @since 2018-10-03 13:54
 */
public abstract class Equip {
    protected final GoodsMetaData goods;

    public Equip(GoodsMetaData goods) {
        this.goods = goods;
    }

    public GoodsMetaData getGoods() {
        return goods;
    }
}
