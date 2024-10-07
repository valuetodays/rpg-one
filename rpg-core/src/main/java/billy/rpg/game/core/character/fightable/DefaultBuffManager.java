package billy.rpg.game.core.character.fightable;

import billy.rpg.game.core.buff.Buff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lei.liu
 * @since 2018-11-22 11:43:55
 */
public class DefaultBuffManager implements BuffManager, Cloneable {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private List<Buff> buffList = new ArrayList<>();

    @Override
    public List<Buff> getBuffChainList() {
        return buffList;
    }

    @Override
    public void addBuff(Buff toAddBuff) {
        // 互斥的buff会消失
        for (Buff buff : buffList) {
            if (buff.isOpposite(toAddBuff)) {
                buffList.remove(buff);
                return;
            }
        }

        // 同类型的buff只能存在后一个
        for (Buff buff : buffList) {
            if (buff.isSameEffect(toAddBuff)) {
                buffList.remove(buff);
                break;
            }
        }

        buffList.add(toAddBuff);
    }

    @Override
    public void removeBuff(Buff buff) {
        buffList.remove(buff);
    }

    @Override
    public int getBuffAttack(Fightable fightable) {
        fightable.clearBuffValue();
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
    public void onRoundEnd() {
        logger.debug("buffCount: " + buffList.size());
        Iterator<Buff> iterator = buffList.iterator();
        while (iterator.hasNext()) {
            Buff next = iterator.next();
            next.onRoundEnd();
            if (next.isEnd()) {
                iterator.remove();
            }
        }
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
