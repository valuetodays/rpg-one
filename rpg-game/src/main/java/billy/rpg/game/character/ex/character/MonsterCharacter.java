package billy.rpg.game.character.ex.character;

import billy.rpg.game.character.ex.equipable.Equipables;
import billy.rpg.game.character.ex.fightable.Fightable;
import billy.rpg.game.character.ex.fightable.MonsterFightable;

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


}
