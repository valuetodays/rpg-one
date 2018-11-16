package billy.rpg.game.core.equip.clothes;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.equip.Equip;
import billy.rpg.game.core.loader.goods.GoodsDataLoader;
import billy.rpg.resource.goods.GoodsType;

/**
 *  暂时只加防御
 *
 * @author liulei-home
 * @since 2018-10-03 13:40
 */
public class ClothesEquip extends Equip {

    public ClothesEquip(GameContainer gameContainer, int index) {
        super(gameContainer, index);

        if (goods.getNumber() != GoodsDataLoader.EMPTY_GOODS_INDEX && goods.getType() != GoodsType.TYPE_CLOTHES.getValue()) {
            throw new RuntimeException("非衣服");
        }
    }

    public int getDefend() {
        return goods.getDefend();
    }

}
