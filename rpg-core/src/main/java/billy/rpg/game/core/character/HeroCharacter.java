package billy.rpg.game.core.character;

import billy.rpg.game.core.character.equipable.Equipables;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.character.walkable.WalkableCharacter;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.role.RoleMetaData;

/**
 * @author liulei-home
 * @since 2018-10-03 14:14
 */
public class HeroCharacter extends Fightable {
    private Equipables equipables;
    private WalkableCharacter walkable;

    public HeroCharacter(GameContainer gameContainer) {
        equipables = new Equipables(gameContainer);
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

    @Override
    public void setRoleMetaData(RoleMetaData roleMetaData) {
        this.roleMetaData = roleMetaData;
    }

}
