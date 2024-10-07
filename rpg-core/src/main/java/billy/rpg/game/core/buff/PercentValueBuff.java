package billy.rpg.game.core.buff;

import billy.rpg.game.core.util.AssertUtil;

/**
 * @author lei.liu
 * @since 2018-12-06 17:33:39
 */
public abstract class PercentValueBuff extends Buff {
    protected int percentValue;

    public PercentValueBuff(int percentValue, int lastRounds) {
        AssertUtil.assertTrue(percentValue > 0, "percentValue should greater than zero");
        this.percentValue = percentValue;
        super.setLastRounds(lastRounds);
    }


}
