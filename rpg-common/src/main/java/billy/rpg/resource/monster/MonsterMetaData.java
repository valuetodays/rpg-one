package billy.rpg.resource.monster;

import billy.rpg.resource.role.RoleMetaData;

import java.util.Map;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-11 10:22
 */
public class MonsterMetaData {
    Map<Integer, RoleMetaData> monsterMap;


    public Map<Integer, RoleMetaData> getMonsterMap() {
        return monsterMap;
    }

    public void setMonsterMap(Map<Integer, RoleMetaData> monsterMap) {
        this.monsterMap = monsterMap;
    }
}
