package billy.rpg.game.character.ex.character;

import billy.rpg.game.character.ex.equipable.Equipable;
import billy.rpg.game.character.ex.fightable.Fightable;
import billy.rpg.game.character.ex.fightable.MonsterFightable;
import billy.rpg.game.character.ex.walkable.WalkableCharacter;

/**
 * @author liulei-home
 * @since 2018-10-03 14:15
 */
public class NPCEx {
    private Fightable fightable = new MonsterFightable();
    private WalkableCharacter walkable;
    private Equipable equipable;

    public WalkableCharacter getWalkable() {
        return walkable;
    }

    public void setWalkable(WalkableCharacter walkable) {
        this.walkable = walkable;
    }

    public Fightable getFightable() {
        return fightable;
    }

    public void setFightable(Fightable fightable) {
        this.fightable = fightable;
    }

    public Equipable getEquipable() {
        return equipable;
    }

    public void setEquipable(Equipable equipable) {
        this.equipable = equipable;
    }
}
