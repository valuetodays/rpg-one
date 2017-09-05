package billy.rpg.game;

import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.role.RoleMetaData;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-31 17:19
 */
public class GameData {
    private int money; // 金币数
    private List<HeroBattle> heroBattleList;
    private List<Integer> heroIds = Arrays.asList(1, 3);
    private List<GoodsMetaData> goodsList = new ArrayList<>();

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
    public void increaseMoney(int money) {
        this.money += money;
    }
    public void decreaseMoney(int money) {
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
     * @param number number
     */
    public void increaseGoods(int number) {
        List<GoodsMetaData> gdsList = goodsList.stream().filter(e ->
                e.getNumber() == number
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(gdsList)) {
            GoodsMetaData gmd = GameFrame.getInstance().getGameContainer().getGoodsMetaDataOf(number);
            gmd.setCount(1);
            goodsList.add(gmd);
            return;
        }

        if (gdsList.size() > 1) {
            throw new RuntimeException("物品" + number + "的数据异常");
        }

        gdsList.get(0).setCount(gdsList.get(0).getCount() + 1);
    }

    /**
     * remove goods
     *    do nothing when no goods with number
     *
     * @param number number
     */
    public void decreaseGoods(int number, int count) {
        List<GoodsMetaData> gdsList = goodsList.stream().filter(e ->
                e.getNumber() == number
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(gdsList)) {
            return ;
        }

        if (gdsList.size() > 1) {
            throw new RuntimeException("物品" + number + "的数据异常");
        }

        int oldCount = gdsList.get(0).getCount();
        oldCount -= count;
        int newCount = Math.max(oldCount, 0);
        gdsList.get(0).setCount(newCount);
    }

    /**
     * get goods list
     */
    public List<GoodsMetaData> getGoodsList() {
        return Collections.unmodifiableList(goodsList);
    }


    public List<HeroBattle> getHeroBattleList() {
        if (heroBattleList == null) {
            heroBattleList = new ArrayList<>();
            for (int i = 0; i < heroIds.size(); i++) {
                RoleMetaData heroRole = GameFrame.getInstance().getGameContainer().getHeroRoleOf(heroIds.get(i));
                if (heroRole == null) {
                    throw new RuntimeException("玩家角色"+heroIds.get(i) + "不存在");
                }
                HeroBattle e = new HeroBattle();
                e.setLeft(200 + i * 150 + 10);
                e.setTop(10 * 32);
                e.setWidth(heroRole.getImage().getWidth());
                e.setHeight(heroRole.getImage().getHeight());
                e.setRoleMetaData(heroRole);

                heroBattleList.add(e);
            }
        }
        return heroBattleList;
    }

}
