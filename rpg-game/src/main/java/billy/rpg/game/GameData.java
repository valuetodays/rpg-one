package billy.rpg.game;

import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.character.equipable.Equipables;
import billy.rpg.game.character.walkable.HeroWalkableCharacter;
import billy.rpg.game.listener.GoodsUseListener;
import billy.rpg.game.listener.support.DefaultGoodsUseListener;
import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsType;
import billy.rpg.resource.role.RoleMetaData;
import billy.rpg.resource.skill.SkillMetaData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-31 17:19
 */
public class GameData {
    /** 金币数 */
    private int money;
    /** 角色列表 */
    private List<HeroCharacter> heroList;
    private List<HeroCharacter.HeroFightable> heroBattleList;
    /** 角色列表 */
    private List<Integer> heroIds = Arrays.asList(1);
//    private List<Integer> heroIds = Arrays.asList(1, 3);
    /** 角色列表 */
    private List<GoodsMetaData> goodsList = new ArrayList<>();
    /** 遇敌步数 */
    private static final int STEP_MEET_MONSTER = 15;
    /** 当前地图下的移动步数，当达到STEP_MEET_MONSTER时会遇到怪物进行战斗 */
    private static int steps;

    private GoodsUseListener goodsUseListener = new DefaultGoodsUseListener();
    private int heroIndex = 0; // 吃药时或更新装备时的玩家索引

    /**
     * 是否发生随机战斗
     * @return true是，false否
     */
    public static boolean randomFight() {
        return steps >= STEP_MEET_MONSTER;
    }
    public static void clearSteps() {
        steps = 0;
    }
    public static void addSteps() {
        steps++;
    }


    /**
     * 指定id的hero离队
     * @param heroId heroId
     */
    public void removeHeroId(int heroId) {
        heroIds.remove((Object)heroId);
    }

    /**
     * 添加指定heroId入队
     * @param heroId heroId
     */
    public void addHeroId(int heroId) {
        heroIds.add(heroId);
    }

    /**
     * 指定heroId为队长
     * @param heroId heroId
     */
    public void setControllId(int heroId) {
        heroIds.remove((Object)heroId);
        heroIds.add(0, heroId);
    }


    // money 操作
    public int getMoney() {
        return money;
    }
    public void addMoney(int money) {
        this.money += money;
    }
    public void useMoney(int money) {
        // TODO 不应该减
        this.money -= money;
        this.money = Math.max(0, this.money);
    }

    // 物品操作

    /**
     * get count of goods with type & number
     * @param number number
     */
    public int getCountOfGoods(int number) {
        List<GoodsMetaData> gdsList = goodsList.stream().filter(e ->
            e.getNumber() == number
        ).collect(Collectors.toList());


        if (CollectionUtils.isEmpty(gdsList)) {
            return 0;
        }

        if (gdsList.size() > 1) {
            throw new RuntimeException("物品" + number + "的数据异常");
        }

        return gdsList.get(0).getCount();
    }

    /**
     * add goods
     * @param id number
     */
    public void addGoods(int id) {
        List<GoodsMetaData> gdsList = goodsList.stream().filter(e ->
                e.getNumber() == id
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(gdsList)) {
            GoodsMetaData gmd = GameFrame.getInstance().getGameContainer().getGoodsMetaDataOf(id);
            gmd.setCount(1);
            goodsList.add(gmd);
            return;
        }

        if (gdsList.size() > 1) {
            throw new RuntimeException("物品" + id + "的数据异常");
        }

        gdsList.get(0).setCount(gdsList.get(0).getCount() + 1);
    }

    /**
     * remove goods
     *    do nothing when no goods with number
     *
     * @param number number
     */
    public void useGoods(int number, int count) {
        List<GoodsMetaData> gdsList = goodsList.stream().filter(e ->
                e.getNumber() == number
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(gdsList)) {
            return ;
        }

        if (gdsList.size() > 1) {
            throw new RuntimeException("物品" + number + "的数据异常");
        }

        GoodsMetaData goodsMetaData = gdsList.get(0);
        int oldCount = goodsMetaData.getCount();
        oldCount -= count;
        goodsMetaData.setCount(Math.max(oldCount, 0));
        goodsUseListener.onUse(goodsMetaData);
    }

    /**
     * get goods list
     */
    public List<GoodsMetaData> getGoodsList() {
        goodsList = goodsList.stream().filter(e -> e.getCount() > 0).collect(Collectors.toList());
        return Collections.unmodifiableList(goodsList);
    }

    /**
     * 装备物品
     */
    public void equip(int roleId, int goodsNumber) {
        int countOfGoods = getCountOfGoods(goodsNumber);
        if (countOfGoods == 0) {
            throw new RuntimeException("还未拥有物品: " + goodsNumber);
        }
        GoodsMetaData goodsMetaData = GameFrame.getInstance().getGameContainer().getGoodsMetaDataOf(goodsNumber);
        int type = goodsMetaData.getType();
        if (type == GoodsType.TYPE_WEAPON.getValue()) {
            equipWeapon(roleId, goodsNumber);
        } else if (type == GoodsType.TYPE_CLOTHES.getValue()) {
            equipClothes(roleId, goodsNumber);
        } else if (type == GoodsType.TYPE_SHOE.getValue()) {
            equipShoe(roleId, goodsNumber);
        } else {
            throw new RuntimeException("无效的物品类型：" + type);
        }

    }
    private void equipWeapon(int roleId, int goodsNumber) {
        checkHeroList();
        HeroCharacter heroCharacter = getHeroCharacterOf(roleId);
        Equipables equipables = heroCharacter.getEquipables();
        equipables.equipWeapon(goodsNumber);
    }

    private void equipClothes(int roleId, int goodsNumber) {
        checkHeroList();
        HeroCharacter heroCharacter = getHeroCharacterOf(roleId);
        Equipables equipables = heroCharacter.getEquipables();
        equipables.equipClothes(goodsNumber);
    }
    private void equipShoe(int roleId, int goodsNumber) {
        checkHeroList();
        HeroCharacter heroCharacter = getHeroCharacterOf(roleId);
        Equipables equipables = heroCharacter.getEquipables();
        equipables.equipShoe(goodsNumber);
    }

    private HeroCharacter getHeroCharacterOf(int roleId) {
        Optional<HeroCharacter> first = heroList.stream().filter(e -> e.getWalkable().getNumber() == roleId).findFirst();
            if (!first.isPresent()) {
            throw new RuntimeException("数据异常");
        }
        return first.get();
    }


    private void checkHeroList() {
        if (heroList == null) {
            heroList = new ArrayList<>();
            for (int i = 0; i < heroIds.size(); i++) {

                Integer heroId = heroIds.get(i);
                RoleMetaData heroRole = GameFrame.getInstance().getGameContainer().getHeroRoleOf(heroId);

                HeroCharacter.HeroFightable e = new HeroCharacter.HeroFightable();
                e.setLeft(200 + i * 150 + 10);
                e.setTop(10 * 32);
                e.setWidth(heroRole.getImage().getWidth());
                e.setHeight(heroRole.getImage().getHeight());
                e.setRoleMetaData(heroRole);
                e.setBattleImage(GameFrame.getInstance().getGameContainer().getRoleItem().getRoleBattleOf(heroId));

                HeroCharacter heroCharacter = new HeroCharacter();
                heroCharacter.setFightable(e);
                HeroWalkableCharacter walkable = new HeroWalkableCharacter();
                walkable.setNumber(heroId);
                heroCharacter.setWalkable(walkable);

                heroList.add(heroCharacter);
            }
        }
    }

    public List<HeroCharacter> getHeroList() {
        checkHeroList();
        return heroList;
    }

    public List<SkillMetaData> getSkillsOf(int roleId) {
        HeroCharacter heroCharacter = getHeroList().get(roleId);
        HeroCharacter.HeroFightable fightable = (HeroCharacter.HeroFightable)heroCharacter.getFightable();
        java.util.List<SkillMetaData> skillList = new ArrayList<>();
        String skillIds = fightable.getRoleMetaData().getSkillIds();
        if (StringUtils.isNotEmpty(skillIds)) { // 如果有技能
            String[] skillIdArr = skillIds.split(",");
            for (String skillId : skillIdArr) {
                int si = Integer.parseInt(skillId);
                SkillMetaData skillMetaData = GameFrame.getInstance().getGameContainer().getSkillMetaDataOf(si);
                skillList.add(skillMetaData);
            }
        }

        return Collections.unmodifiableList(skillList);
    }

    public int getHeroIndex() {
        return heroIndex;
    }
}
