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

    // 装备暂只支持固定数值的加强
//
//    public int getAttack() {
//        return 0;
//    }
//
//    public int getDefend() {
//        return 0;
//    }
//
//    public int getSpeed() {
//        return 0;
//    }
}
