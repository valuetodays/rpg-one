package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import org.apache.log4j.Logger;

/**
 * buff
 * 注意debuff也是buff，如增加150%的攻击力，与减弱80%的攻击力
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 10:25:29
 */
public abstract class Buff {
    protected final Logger logger = Logger.getLogger(getClass());

    public static int DEFAULT_ROUNDS = 1;

    // 持续回合数
    protected int lastRounds = DEFAULT_ROUNDS;
    protected String name = "";

    public void doApply(Fightable fightable) {
        apply(fightable);

        lastRounds--;
        if (lastRounds < 0) {
            RuntimeException runtimeException = new RuntimeException("exception when buff's doApply() ");
            logger.error(runtimeException.getMessage(), runtimeException);
            throw runtimeException;
        }
    }
    protected abstract void apply(Fightable fightable);
    public abstract BuffType getBuffType();

    /**
     * 互斥的buff
     */
    public boolean isOpposite(Buff theOtherBuff) {
        return getBuffType().isOpposite(theOtherBuff.getBuffType());
    }

    /**
     * 相同效果的buff
     */
    public boolean isSameEffect(Buff theOtherBuff) {
        return getBuffType() == theOtherBuff.getBuffType();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastRounds(int lastRounds) {
        this.lastRounds = lastRounds;
    }
    public int getLastRounds() {
        return lastRounds;
    }

    @Override
    public String toString() {
        return name + "("+lastRounds+")" + "["+getBuffType()+"]";
    }
}
