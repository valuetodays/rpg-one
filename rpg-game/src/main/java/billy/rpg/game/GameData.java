package billy.rpg.game;

import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.resource.role.RoleMetaData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-31 17:19
 */
public class GameData {
    private int money; // 金币数
    private List<Integer> heroIds = Arrays.asList(1, 3);

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

    private List<HeroBattle> heroBattleList;
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
