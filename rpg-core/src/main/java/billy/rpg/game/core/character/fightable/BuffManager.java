package billy.rpg.game.core.character.fightable;

import billy.rpg.game.core.buff.Buff;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-22 11:42:56
 */
public interface BuffManager {
    List<Buff> getBuffList();
    void addBuff(Buff buff);

    void removeBuff(Buff buff);

    int getBuffAttack(Fightable fightable);

    int getBuffDefend(Fightable fightable);

    int getBuffSpeed(Fightable fightable);
}
