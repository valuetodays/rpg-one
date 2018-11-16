package billy.rpg.game.core.equip.shoe;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.equip.Equip;
import billy.rpg.game.core.loader.goods.GoodsDataLoader;
import billy.rpg.resource.goods.GoodsType;

/**
 * // 暂时只加速度
 *
 * @author liulei-home
 * @since 2018-10-03 13:40
 */
public class ShoeEquip extends Equip {

    public ShoeEquip(GameContainer gameContainer, int index) {
        super(gameContainer, index);

        if (goods.getNumber() != GoodsDataLoader.EMPTY_GOODS_INDEX && goods.getType() != GoodsType.TYPE_SHOE.getValue()) {
            throw new RuntimeException("非鞋子");
        }
    }

    public int getSpeed() {
        return goods.getSpeed();
    }


}
