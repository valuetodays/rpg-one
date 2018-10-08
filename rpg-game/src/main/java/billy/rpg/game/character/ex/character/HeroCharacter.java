package billy.rpg.game.character.ex.character;

import billy.rpg.game.character.ex.equipable.Equipables;
import billy.rpg.game.character.ex.fightable.Fightable;
import billy.rpg.game.character.ex.walkable.WalkableCharacter;
import billy.rpg.resource.role.RoleMetaData;

/**
 * @author liulei-home
 * @since 2018-10-03 14:14
 */
public class HeroCharacter {
    private Fightable fightable = new HeroFightable();
    private Equipables equipables = new Equipables();
    private WalkableCharacter walkable;

    public Fightable getFightable() {
        return fightable;
    }

    public void setFightable(Fightable fightable) {
        this.fightable = fightable;
    }

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

    public static class HeroFightable extends Fightable {
        @Override
        public void setRoleMetaData(RoleMetaData roleMetaData) {
            this.roleMetaData = roleMetaData;
        }
    }
}
