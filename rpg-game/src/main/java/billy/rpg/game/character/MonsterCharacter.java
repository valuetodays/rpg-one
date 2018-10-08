package billy.rpg.game.character;

import billy.rpg.game.character.equipable.Equipables;
import billy.rpg.game.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * @author lei.liu@datatist.com
 * @since 2018-10-08 14:50:56
 */
public class MonsterCharacter {
    private Fightable fightable = new MonsterFightable();
    private Equipables equipables = new Equipables();

    public Fightable getFightable() {
        return fightable;
    }

    public void setFightable(Fightable fightable) {
        this.fightable = fightable;
    }

    public Equipables getEquipables() {
        return equipables;
    }

    public void setEquipables(Equipables equipables) {
        this.equipables = equipables;
    }

    public static class MonsterFightable extends Fightable {
        @Override
        public void setRoleMetaData(RoleMetaData roleMetaData) {
            this.roleMetaData = roleMetaData.clone();
        }
    }

}
