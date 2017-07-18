package billy.rpg.game.character.battle;

import billy.rpg.resource.role.RoleMetaData;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-12 16:37
 */
public class HeroBattle extends FightableCharacter {

    @Override
    public void setRoleMetaData(RoleMetaData roleMetaData) {
        this.roleMetaData = roleMetaData;
    }
}
