package billy.rpg.game.character.ex.equipable;

import billy.rpg.game.GameFrame;
import billy.rpg.game.equip.clothes.ClothesEquip;
import billy.rpg.game.equip.shoe.ShoeEquip;
import billy.rpg.game.equip.weapon.WeaponEquip;
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
            weapon = new Equipable();
            weapon.setEquip(new WeaponEquip(GoodsMetaData.EMPTY_GOODS_INDEX));
        }
        {
            shoe = new Equipable();
            shoe.setEquip(new ShoeEquip(GoodsMetaData.EMPTY_GOODS_INDEX));
        }
        {
            clothes = new Equipable();
            clothes.setEquip(new ClothesEquip(GoodsMetaData.EMPTY_GOODS_INDEX));
        }
    }

    /**
     * 装备武器
     * @param index index
     */
    public void equipWeapon(int index) {
        upequipWeapon();

        Equipable weaponEquip = getWeapon();
        weaponEquip.setEquip(new WeaponEquip(index));
    }

    /**
     * 卸下 武器
     */
    public void upequipWeapon() {
        Equipable weaponEquipable = getWeapon();
        GoodsMetaData goods = weaponEquipable.getEquip().getGoods();
        if (!goods.isEmptyGoods()) {
            weaponEquipable.setEquip(new WeaponEquip(GoodsMetaData.EMPTY_GOODS_INDEX));
            // 卸下的物品要放到物品栏里
            GameFrame.getInstance().getGameData().addGoods(1);
        }
    }
}
