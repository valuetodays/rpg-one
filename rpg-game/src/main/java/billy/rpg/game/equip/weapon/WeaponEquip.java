package billy.rpg.game.equip.weapon;

import billy.rpg.game.equip.Equip;
import billy.rpg.game.loader.goods.GoodsDataLoader;
import billy.rpg.resource.goods.GoodsType;

/**
 *  // 暂时只加攻击
 *
 * @author liulei-home
 * @since 2018-10-03 13:38
 */
public class WeaponEquip extends Equip {

    public WeaponEquip(int index) {
        super(index);

        if (goods.getNumber() != GoodsDataLoader.EMPTY_GOODS_INDEX && goods.getType() != GoodsType.TYPE_WEAPON.getValue()) {
            throw new RuntimeException("非武器");
        }
    }

    public int getAttack() {
        return goods.getAttack();
    }
}
