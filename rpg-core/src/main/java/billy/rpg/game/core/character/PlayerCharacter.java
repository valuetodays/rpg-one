package billy.rpg.game.core.character;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.character.walkable.WalkableCharacter;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 玩家角色
 *
 * @author liulei-home
 * @since 2018-10-03 14:14
 */
public class PlayerCharacter extends Fightable {
    private WalkableCharacter walkable;

    public PlayerCharacter(GameContainer gameContainer) {
        super(gameContainer);
    }

    public PlayerCharacter() {
        super();
    }

    public WalkableCharacter getWalkable() {
        return walkable;
    }

    public void setWalkable(WalkableCharacter walkable) {
        this.walkable = walkable;
    }

    @Override
    public void setRoleMetaData(RoleMetaData roleMetaData) {
        this.roleMetaData = roleMetaData;
    }

}
