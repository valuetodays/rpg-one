package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;

/**
 * @author lei.liu
 * @since 2018-11-22 10:27:40
 */
public class DuBuff extends Buff {

    @Override
    public void apply(Fightable fightable) {

    }

    @Override
    public BuffType getBuffType() {
        return BuffType.DU;
    }
}
