package billy.rpg.game.character;

import billy.rpg.game.character.equipable.Equipables;
import billy.rpg.game.character.fightable.Fightable;
import billy.rpg.game.character.walkable.WalkableCharacter;
import billy.rpg.resource.role.RoleMetaData;

/**
 * @author liulei-home
 * @since 2018-10-03 14:14
 */
public class HeroCharacter extends Fightable {
    private Equipables equipables = new Equipables();
    private WalkableCharacter walkable;

    public WalkableCharacter getWalkable() {
        return walkable;
    }

    public void setWalkable(WalkableCharacter walkable) {
        this.walkable = walkable;
    }

    public Equipables getEquipables() {
        return equipables;
    }

    public void setEquipables(Equipables equipables) {
        this.equipables = equipables;
    }

    @Override
    public void setRoleMetaData(RoleMetaData roleMetaData) {
        this.roleMetaData = roleMetaData;
    }

}
