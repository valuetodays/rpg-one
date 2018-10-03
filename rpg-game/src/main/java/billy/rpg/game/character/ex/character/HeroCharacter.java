package billy.rpg.game.character.ex.character;

import billy.rpg.game.character.ex.equipable.Equipable;
import billy.rpg.game.character.ex.fightable.Fightable;
import billy.rpg.game.character.ex.fightable.HeroFightable;
import billy.rpg.game.character.ex.walkable.WalkableCharacter;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.equip.clothes.ClothesEquip;
import billy.rpg.game.equip.shoe.ShoeEquip;
import billy.rpg.game.equip.weapon.WeaponEquip;
import billy.rpg.game.loader.goods.GoodsDataLoader;
import billy.rpg.resource.goods.GoodsMetaData;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liulei-home
 * @since 2018-10-03 14:14
 */
public class HeroCharacter {
    private Fightable fightable = new HeroFightable();
    private List<Equipable> equipableList;
    private WalkableCharacter walkable;

    public HeroCharacter() {
        initEquipableList();
    }

    public void initEquipableList() {
        WeaponEquip weapon; // 手持
        ShoeEquip shoe; // 脚蹬
        ClothesEquip clothes; // 身穿

        {
            GoodsMetaData goods = GameContainer.getInstance().getGoodsMetaDataOf(GoodsDataLoader.EMPTY_GOODS_INDEX);
            weapon = new WeaponEquip(goods);
        }
        {
            GoodsMetaData goods = GameContainer.getInstance().getGoodsMetaDataOf(GoodsDataLoader.EMPTY_GOODS_INDEX);
            shoe = new ShoeEquip(goods);
        }
        {
            GoodsMetaData goods = GameContainer.getInstance().getGoodsMetaDataOf(GoodsDataLoader.EMPTY_GOODS_INDEX);
            clothes = new ClothesEquip(goods);
        }
        equipableList = Stream.of(weapon, shoe, clothes).map(e -> {
            Equipable equipable = new Equipable();
            equipable.setEquip(e);
            return equipable;
        }).collect(Collectors.toList());
    }

    public Fightable getFightable() {
        return fightable;
    }

    public void setFightable(Fightable fightable) {
        this.fightable = fightable;
    }

    public List<Equipable> getEquipableList() {
        return equipableList;
    }

    public void setEquipableList(List<Equipable> equipableList) {
        this.equipableList = equipableList;
    }

    public WalkableCharacter getWalkable() {
        return walkable;
    }

    public void setWalkable(WalkableCharacter walkable) {
        this.walkable = walkable;
    }
}
