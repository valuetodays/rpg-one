package billy.rpg.game.core.character.equipable;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.equip.clothes.ClothesEquip;
import billy.rpg.game.core.equip.shoe.ShoeEquip;
import billy.rpg.game.core.equip.weapon.WeaponEquip;
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

    public Equipables(GameContainer gameContainer) {
        init(gameContainer);
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

    private void init(GameContainer gameContainer) {
        {
            weapon = new Equipable();
            weapon.setEquip(new WeaponEquip(gameContainer, GoodsMetaData.EMPTY_GOODS_INDEX));
        }
        {
            shoe = new Equipable();
            shoe.setEquip(new ShoeEquip(gameContainer, GoodsMetaData.EMPTY_GOODS_INDEX));
        }
        {
            clothes = new Equipable();
            clothes.setEquip(new ClothesEquip(gameContainer, GoodsMetaData.EMPTY_GOODS_INDEX));
        }
    }

    /**
     * 装备衣服
     * @param index index
     */
    public void equipClothes(GameContainer gameContainer, int index) {
        upequipClothes(gameContainer);

        Equipable clothesEquipable = getClothes();
        clothesEquipable.setEquip(new ClothesEquip(gameContainer, index));
    }

    private void upequipClothes(GameContainer gameContainer) {
        Equipable clothesEquipable = getClothes();
        GoodsMetaData goods = clothesEquipable.getEquip().getGoods();
        if (!goods.isEmptyGoods()) {
            clothesEquipable.setEquip(new WeaponEquip(gameContainer, GoodsMetaData.EMPTY_GOODS_INDEX));
            // 卸下的物品要放到物品栏里
            gameContainer.getGameData().addGoods(gameContainer, goods.getNumber());
        }
    }

    /**
     * 装备武器
     * @param index index
     */
    public void equipWeapon(GameContainer gameContainer, int index) {
        upequipWeapon(gameContainer);

        Equipable weaponEquipable = getWeapon();
        weaponEquipable.setEquip(new WeaponEquip(gameContainer, index));
    }

    /**
     * 卸下武器
     */
    public void upequipWeapon(GameContainer gameContainer) {
        Equipable weaponEquipable = getWeapon();
        GoodsMetaData goods = weaponEquipable.getEquip().getGoods();
        if (!goods.isEmptyGoods()) {
            weaponEquipable.setEquip(new WeaponEquip(gameContainer, GoodsMetaData.EMPTY_GOODS_INDEX));
            // 卸下的物品要放到物品栏里
            gameContainer.getGameData().addGoods(gameContainer, goods.getNumber());
        }
    }

    /**
     * 装备鞋子
     */
    public void equipShoe(GameContainer gameContainer, int index) {
        unequipShoe(gameContainer);

        Equipable shoeEquipable = getShoe();
        shoeEquipable.setEquip(new ShoeEquip(gameContainer, index));
    }

    /**
     * 卸下鞋子
     */
    public void unequipShoe(GameContainer gameContainer) {
        Equipable shoeEquipable = getShoe();
        GoodsMetaData goods = shoeEquipable.getEquip().getGoods();
        if (!goods.isEmptyGoods()) {
            shoeEquipable.setEquip(new ShoeEquip(gameContainer, GoodsMetaData.EMPTY_GOODS_INDEX));
            // 卸下的物品要放到物品栏里
            gameContainer.getGameData().addGoods(gameContainer, goods.getNumber());
        }
    }

}
