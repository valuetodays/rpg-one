package billy.rpg.game.character.ex.equipable;

import billy.rpg.game.container.GameContainer;
import billy.rpg.game.equip.clothes.ClothesEquip;
import billy.rpg.game.equip.shoe.ShoeEquip;
import billy.rpg.game.equip.weapon.WeaponEquip;
import billy.rpg.game.loader.goods.GoodsDataLoader;
import billy.rpg.resource.goods.GoodsMetaData;

/**
 * 一个玩家角色有一个{@link Equipables}对象
 * 一个{@link Equipables}里有多个{@link Equipable}对象
 * @author lei.liu@datatist.com
 * @since 2018-10-08 13:35:23
 */
public class Equipables {
    private Equipable weapon; // 手持
    private Equipable shoe; // 脚蹬
    private Equipable clothes; // 身穿

    public Equipables() {
        init();
    }

    public Equipable getWeapon() {
        return weapon;
    }

    public Equipable getShoe() {
        return shoe;
    }

    public Equipable getClothes() {
        return clothes;
    }

    public void setWeapon(Equipable weapon) {
        this.weapon = weapon;
    }

    public void setShoe(Equipable shoe) {
        this.shoe = shoe;
    }

    public void setClothes(Equipable clothes) {
        this.clothes = clothes;
    }

    private void init() {
        {
            GoodsMetaData goods = GameContainer.getInstance().getGoodsMetaDataOf(GoodsDataLoader.EMPTY_GOODS_INDEX);
            weapon = new Equipable();
            weapon.setEquip(new WeaponEquip(goods));
        }
        {
            GoodsMetaData goods = GameContainer.getInstance().getGoodsMetaDataOf(GoodsDataLoader.EMPTY_GOODS_INDEX);
            shoe = new Equipable();
            shoe.setEquip(new ShoeEquip(goods));
        }
        {
            GoodsMetaData goods = GameContainer.getInstance().getGoodsMetaDataOf(GoodsDataLoader.EMPTY_GOODS_INDEX);
            clothes = new Equipable();
            clothes.setEquip(new ClothesEquip(goods));
        }
    }

}
