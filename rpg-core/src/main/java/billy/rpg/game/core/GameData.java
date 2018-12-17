package billy.rpg.game.core;

import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.character.equipable.Equipables;
import billy.rpg.game.core.character.walkable.HeroWalkableCharacter;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.callback.GoodsUseCallback;
import billy.rpg.game.core.callback.support.DefaultGoodsUseCallback;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.MessageBoxScreen;
import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsType;
import billy.rpg.resource.level.LevelData;
import billy.rpg.resource.level.LevelMetaData;
import billy.rpg.resource.role.RoleMetaData;
import billy.rpg.resource.skill.SkillMetaData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-31 17:19
 */
public class GameData {
    private final Logger logger = Logger.getLogger(getClass());
    /** 金币数 */
    private int money;
    /** 角色列表 */
    private List<PlayerCharacter> heroList;
    private List<PlayerCharacter> heroBattleList;
    /** 角色列表 */
    private List<Integer> heroIds = new LinkedList<>();
//    private List<Integer> heroIds = Arrays.asList(1, 3);
    /** 角色列表 */
    private List<GoodsMetaData> goodsList = new ArrayList<>();
    /** 遇敌步数 */
    private static final int STEP_MEET_MONSTER = 15;
    /** 当前地图下的移动步数，当达到STEP_MEET_MONSTER时会遇到怪物进行战斗 */
    private static int steps;

    private GoodsUseCallback goodsUseListener = new DefaultGoodsUseCallback();
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
        if (!heroIds.contains(heroId)) {
            throw new IllegalArgumentException("无控制角色："+heroId);
        }
        heroIds.remove((Object)heroId);
    }

    /**
     * 添加指定heroId入队
     * @param heroId heroId
     */
    public void addHeroId(int heroId) {
        if (heroIds.contains(heroId)) {
            throw new IllegalArgumentException("已有可控制角色："+heroId);
        }
        heroIds.add(heroId);
    }

    /**
     * 指定heroId为队长
     * @param heroId heroId
     */
    public void setControlId(int heroId) {
        removeHeroId(heroId);
        heroIds.add(0, heroId);
    }
    public int getControlId() {
        if (heroIds.size() <= 0) {
            throw new RuntimeException("无可控制的角色");
        }
        return heroIds.get(0);
    }

    /**
     * 切换脸谱角色即是指将第一个角色移到角色列表的最后
     */
    public void exChangeControlId() {
        if (heroIds.size() == 1) {
            return;
        }
        int controlId = getControlId();
        removeHeroId(controlId);
        addHeroId(controlId);
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
        List<GoodsMetaData> gdsList = new ArrayList<>();

        /*
        gdsList = goodsList.stream().filter(e ->
            e.getNumber() == number
        ).collect(Collectors.toList());*/

        for (GoodsMetaData goodsMetaData : goodsList) {
            if (goodsMetaData.getNumber() == number) {
                gdsList.add(goodsMetaData);
            }
        }


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
    public void addGoods(GameContainer gameContainer, int id) {
        List<GoodsMetaData> gdsList = new ArrayList<>();
//        gdsList = goodsList.stream().filter(e ->
//                e.getNumber() == id
//        ).collect(Collectors.toList());

        for (GoodsMetaData goodsMetaData : goodsList) {
            if (goodsMetaData.getNumber() == id) {
                gdsList.add(goodsMetaData);
            }
        }

        if (CollectionUtils.isEmpty(gdsList)) {
            GoodsMetaData gmd = gameContainer.getGoodsMetaDataOf(id);
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
    public void useGoods(GameContainer gameContainer, int number, int count) {
        List<GoodsMetaData> gdsList = new ArrayList<>();
//       gdsList = goodsList.stream().filter(e ->
//                e.getNumber() == number
//        ).collect(Collectors.toList());

        for (GoodsMetaData goodsMetaData : goodsList) {
            if (goodsMetaData.getNumber() == number) {
                gdsList.add(goodsMetaData);
            }
        }

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
        goodsUseListener.onUse(gameContainer, goodsMetaData);
    }

    /**
     * get goods list
     */
    public List<GoodsMetaData> getGoodsList() {
        List<GoodsMetaData> gdsList = new ArrayList<>();
        for (GoodsMetaData goodsMetaData : goodsList) {
            if (goodsMetaData.getCount() > 0) {
                gdsList.add(goodsMetaData);
            }
        }
        goodsList = gdsList;
//        goodsList = goodsList.stream().filter(e -> e.getCount() > 0).collect(Collectors.toList());
        return Collections.unmodifiableList(goodsList);
    }

    /**
     * 装备物品
     */
    public void equip(GameContainer gameContainer, int roleId, int goodsNumber) {
        int countOfGoods = getCountOfGoods(goodsNumber);
        if (countOfGoods == 0) {
            throw new RuntimeException("还未拥有物品: " + goodsNumber);
        }
        GoodsMetaData goodsMetaData = gameContainer.getGoodsMetaDataOf(goodsNumber);
        int type = goodsMetaData.getType();
        if (type == GoodsType.TYPE_WEAPON.getValue()) {
            equipWeapon(gameContainer, roleId, goodsNumber);
        } else if (type == GoodsType.TYPE_CLOTHES.getValue()) {
            equipClothes(gameContainer, roleId, goodsNumber);
        } else if (type == GoodsType.TYPE_SHOE.getValue()) {
            equipShoe(gameContainer, roleId, goodsNumber);
        } else {
            throw new RuntimeException("无效的物品类型：" + type);
        }

    }
    private void equipWeapon(GameContainer gameContainer, int roleId, int goodsNumber) {
        checkHeroList(gameContainer);
        PlayerCharacter heroCharacter = getHeroCharacterOf(roleId);
        Equipables equipables = heroCharacter.getEquipables();
        equipables.equipWeapon(gameContainer, goodsNumber);
    }

    private void equipClothes(GameContainer gameContainer, int roleId, int goodsNumber) {
        checkHeroList(gameContainer);
        PlayerCharacter heroCharacter = getHeroCharacterOf(roleId);
        Equipables equipables = heroCharacter.getEquipables();
        equipables.equipClothes(gameContainer, goodsNumber);
    }
    private void equipShoe(GameContainer gameContainer, int roleId, int goodsNumber) {
        checkHeroList(gameContainer);
        PlayerCharacter heroCharacter = getHeroCharacterOf(roleId);
        Equipables equipables = heroCharacter.getEquipables();
        equipables.equipShoe(gameContainer, goodsNumber);
    }

    private PlayerCharacter getHeroCharacterOf(int roleId) {
        PlayerCharacter heroCharacter = null;
        for (PlayerCharacter h : heroList) {
            if (h.getWalkable().getNumber() == roleId) {
                heroCharacter = h;
            }
        }
        if (heroCharacter == null) {
            throw new RuntimeException("数据异常");
        }
        return heroCharacter;
        /*Optional<PlayerCharacter> first = heroList.stream().filter(e -> e.getWalkable().getNumber() == roleId).findFirst();
            if (!first.isPresent()) {
            throw new RuntimeException("数据异常");
        }
        return first.get();*/
    }


    private synchronized void checkHeroList(GameContainer gameContainer) {
        if (heroList == null) {
            heroList = new ArrayList<>();
            for (int i = 0; i < heroIds.size(); i++) {

                Integer heroId = heroIds.get(i);
                RoleMetaData heroRole = gameContainer.getHeroRoleOf(heroId);
                // 首次加载角色信息，要升级到一级
                heroRole.setLevel(0);
                heroRole.setExp(0);
                heroRole.setSkillIds("1,2,3,4");
                levelUp(gameContainer, heroRole, true);

                PlayerCharacter e = new PlayerCharacter(gameContainer);
                e.setLeft(200 + i * 150 + 10);
                e.setTop(10 * 32);
                e.setWidth(heroRole.getImage().getWidth());
                e.setHeight(heroRole.getImage().getHeight());
                e.setRoleMetaData(heroRole);
                e.setBattleImage(gameContainer.getRoleItem().getRoleBattleOf(heroId));

                HeroWalkableCharacter walkable = new HeroWalkableCharacter();
                walkable.setNumber(heroId);
                e.setWalkable(walkable);

                heroList.add(e);
            }
        }
    }

    public synchronized List<PlayerCharacter> getHeroList(GameContainer gameContainer) {
        checkHeroList(gameContainer);
        return heroList;
    }

    public List<SkillMetaData> getSkillsOf(GameContainer gameContainer, int roleId) {
        PlayerCharacter heroCharacter = getHeroList(gameContainer).get(roleId);
        java.util.List<SkillMetaData> skillList = new ArrayList<>();
        String skillIds = heroCharacter.getRoleMetaData().getSkillIds();
        if (StringUtils.isNotEmpty(skillIds)) { // 如果有技能
            String[] skillIdArr = skillIds.split(",");
            for (String skillId : skillIdArr) {
                int si = Integer.parseInt(skillId);
                SkillMetaData skillMetaData = gameContainer.getSkillMetaDataOf(si);
                skillList.add(skillMetaData);
            }
        }

        return Collections.unmodifiableList(skillList);
    }

    public int getHeroIndex() {
        return heroIndex;
    }

    /**
     * 处理升级逻辑，不考虑连续升级的情况，永远不可能一刀99级，升到99级最少要砍99刀
     * @param gameContainer gameContainer
     * @param heroMetaData 要升级的角色的信息
     * @param force 是否强制升级，打怪升级时不能强制升级
     */
    public void levelUp(GameContainer gameContainer, RoleMetaData heroMetaData, boolean force) {
        LevelMetaData levelMetaData = gameContainer.getLevelMetaDataOf(heroMetaData.getLevelChain());

        int level = heroMetaData.getLevel();
        if (level >= levelMetaData.getMaxLevel()) {
            // 满级
            String msg = "满级";
            final BaseScreen bs = new MessageBoxScreen(msg);
            gameContainer.getGameFrame().pushScreen(bs);
            return;
        }

        LevelData levelData = levelMetaData.getLevelDataList().get(level+1);
        if (level == 0 || heroMetaData.getExp() > levelData.getExp()) {
            logger.debug("level up to " + (level+1));
            if (!force) { // 强制升级时不变动角色的现有经验值
                LevelData preLevelData = levelMetaData.getLevelDataList().get(level);
                heroMetaData.setExp(heroMetaData.getExp() - preLevelData.getExp());
            }
            heroMetaData.setMaxMp(heroMetaData.getMaxMp() + levelData.getMaxMp() - levelMetaData.getLevelDataList().get(level).getMaxMp());
            heroMetaData.setMp(heroMetaData.getMaxMp()); // 当前mp加到最大
            heroMetaData.setMaxHp(heroMetaData.getMaxHp() + levelData.getMaxHp() - levelMetaData.getLevelDataList().get(level).getMaxHp());
            heroMetaData.setHp(heroMetaData.getMaxHp()); // 当前hp加到最大
            heroMetaData.setAttack(heroMetaData.getAttack() + levelData.getAttack() - levelMetaData.getLevelDataList().get(level).getAttack());
            heroMetaData.setDefend(heroMetaData.getDefend() + levelData.getDefend() - levelMetaData.getLevelDataList().get(level).getDefend());
            heroMetaData.setSpeed(heroMetaData.getSpeed() + levelData.getSpeed() - levelMetaData.getLevelDataList().get(level).getSpeed());
            heroMetaData.setLevel(level + 1);
        }
    }

}
