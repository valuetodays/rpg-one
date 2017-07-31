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
    private List<Integer> heroIds = Arrays.asList(1);

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

    private List<HeroBattle> heroBattleList;
    public List<HeroBattle> getHeroBattleList() {
        if (heroBattleList == null) {
            heroBattleList = new ArrayList<>();
            for (int i = 0; i < heroIds.size(); i++) {
                RoleMetaData heroRole = GameFrame.getInstance().getGameContainer().getHeroRoleOf(heroIds.get(i));
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
