package billy.rpg.game.equip.shoe;

import billy.rpg.game.equip.Equip;
import billy.rpg.game.loader.goods.GoodsDataLoader;
import billy.rpg.resource.goods.GoodsType;

/**
 * // 暂时只加速度
 *
 * @author liulei-home
 * @since 2018-10-03 13:40
 */
public class ShoeEquip extends Equip {

    public ShoeEquip(int index) {
        super(index);

        if (goods.getNumber() != GoodsDataLoader.EMPTY_GOODS_INDEX && goods.getType() != GoodsType.TYPE_SHOE.getValue()) {
            throw new RuntimeException("非鞋子");
        }
    }

    public int getSpeed() {
        return goods.getSpeed();
    }


}
