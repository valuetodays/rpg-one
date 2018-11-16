package billy.rpg.game.core.character;

import billy.rpg.game.core.character.equipable.Equipables;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.role.RoleMetaData;

/**
 * @author lei.liu@datatist.com
 * @since 2018-10-08 14:50:56
 */
public class MonsterCharacter extends Fightable {
    private Equipables equipables;

    public MonsterCharacter(GameContainer gameContainer) {
        equipables = new Equipables(gameContainer);
    }

    public Equipables getEquipables() {
        return equipables;
    }

    public void setEquipables(Equipables equipables) {
        this.equipables = equipables;
    }

    @Override
    public void setRoleMetaData(RoleMetaData roleMetaData) {
        this.roleMetaData = roleMetaData.clone();
    }

}
