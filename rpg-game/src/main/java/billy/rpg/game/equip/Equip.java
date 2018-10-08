package billy.rpg.game.equip;

import billy.rpg.game.GameFrame;
import billy.rpg.resource.goods.GoodsMetaData;

/**
 * @author liulei-home
 * @since 2018-10-03 13:54
 */
public abstract class Equip {
    protected final GoodsMetaData goods;

    public Equip(int index) {
        this.goods = GameFrame.getInstance().getGameContainer().getGoodsMetaDataOf(index);
    }

    public GoodsMetaData getGoods() {
        return goods;
    }
}
