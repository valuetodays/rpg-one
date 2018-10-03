package billy.rpg.game.equip.clothes;

import billy.rpg.game.equip.Equip;
import billy.rpg.resource.goods.GoodsMetaData;

/**
 *  暂时只加防御
 *
 * @author liulei-home
 * @since 2018-10-03 13:40
 */
public class ClothesEquip extends Equip {

    public ClothesEquip(GoodsMetaData goods) {
        super(goods);
    }

    public int getDefend() {
        return goods.getDefend();
    }

}
