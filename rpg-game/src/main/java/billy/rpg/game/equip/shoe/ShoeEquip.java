package billy.rpg.game.equip.shoe;

import billy.rpg.game.equip.Equip;
import billy.rpg.resource.goods.GoodsMetaData;

/**
 * // 暂时只加速度
 *
 * @author liulei-home
 * @since 2018-10-03 13:40
 */
public class ShoeEquip extends Equip {

    public ShoeEquip(GoodsMetaData goods) {
        super(goods);
    }

    public int getSpeed() {
        return goods.getAttack(); // TODO 暂无speed属性
    }


}
