package billy.rpg.game.character.ex.character;

import billy.rpg.game.character.ex.walkable.WalkableCharacter;

/**
 * @author liulei-home
 * @since 2018-10-03 14:15
 */
public class NPCCharacter {
    private WalkableCharacter walkable;

    public WalkableCharacter getWalkable() {
        return walkable;
    }

    public void setWalkable(WalkableCharacter walkable) {
        this.walkable = walkable;
    }
}
