package billy.rpg.game.character.battle;

import billy.rpg.resource.role.RoleMetaData;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-11 12:08
 */
public class MonsterBattle extends FightableCharacter {

    @Override
    public void setRoleMetaData(RoleMetaData roleMetaData) {
        this.roleMetaData = roleMetaData.clone();
    }
}
