package billy.rpg.game.core.character.fightable;

import billy.rpg.game.core.buff.Buff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-22 11:43:55
 */
public class DefaultBuffManager implements BuffManager, Cloneable {
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
        System.out.println("buffCount: " + buffList.size());
        Iterator<Buff> iterator = buffList.iterator();
        while (iterator.hasNext()) {
            Buff next = iterator.next();
            next.onRoundEnd();
            if (next.isEnd()) {
                iterator.remove();
            }
        }
//        for (Buff buff : buffList) {
//            buff.onRoundEnd();
//            if (buff.isEnd()) {
//                buffList.remove(buff);
//            }
//        }
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
