package billy.rpg.game.core.buff;

import billy.rpg.game.core.util.AssertUtil;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-06 17:28:39
 */
public abstract class FixedValueBuff extends Buff {
    protected int fixedValue;

    public FixedValueBuff(int fixedValue, int lastRounds) {
        AssertUtil.assertTrue(fixedValue > 0, "fixedValue should greater than zero");
        this.fixedValue = fixedValue;
        super.setLastRounds(lastRounds);
    }

    public int getFixedValue() {
        return fixedValue;
    }
}
