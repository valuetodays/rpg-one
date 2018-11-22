package billy.rpg.game.core.character.fightable;

import billy.rpg.game.core.buff.Buff;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-22 11:43:55
 */
public class DefaultBuffManager implements BuffManager, Cloneable {
    private List<Buff> buffList = new ArrayList<>();

    @Override
    public List<Buff> getBuffList() {
        return buffList;
    }

    @Override
    public void addBuff(Buff buff) {
        // TODO 暂不考虑两个引燃只能存在后一个的情况
        buffList.add(buff);
    }

    @Override
    public void removeBuff(Buff buff) {
        buffList.remove(buff);
    }

    @Override
    public int getBuffAttack(Fightable fightable) {
        // buffList的排序情况
        for (Buff buff : buffList) {
            buff.doApply(fightable);
        }
        return fightable.getBuffAttack();
    }

    @Override
    public int getBuffDefend(Fightable fightable) {
        for (Buff buff : buffList) {
            buff.doApply(fightable);
        }
        return fightable.getBuffDefend();
    }

    @Override
    public int getBuffSpeed(Fightable fightable) {
        for (Buff buff : buffList) {
            buff.doApply(fightable);
        }
        return fightable.getBuffSpeed();
    }

    @Override
    public DefaultBuffManager clone() {
        DefaultBuffManager clone = null;
        try {
            clone = (DefaultBuffManager) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
