package billy.rpg.game.equip.clothes;

import billy.rpg.game.equip.Equip;
import billy.rpg.game.loader.goods.GoodsDataLoader;
import billy.rpg.resource.goods.GoodsType;

/**
 *  暂时只加防御
 *
 * @author liulei-home
 * @since 2018-10-03 13:40
 */
public class ClothesEquip extends Equip {

    public ClothesEquip(int index) {
        super(index);

        if (goods.getNumber() != GoodsDataLoader.EMPTY_GOODS_INDEX && goods.getType() != GoodsType.TYPE_CLOTHES.getValue()) {
            throw new RuntimeException("非衣服");
        }
    }

    public int getDefend() {
        return goods.getDefend();
    }

}
