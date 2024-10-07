package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;

/**
 * 增加固定速度
 *
 * @author lei.liu
 * @since 2018-12-06 17:18:27
 */
public class SpeedEnhanceFixedValueBuff extends FixedValueBuff {

    public SpeedEnhanceFixedValueBuff(int fixedValue, int lastRounds) {
        super(fixedValue, lastRounds);
        setName("增加固定速度");
    }

    @Override
    protected void apply(Fightable fightable) {
        fightable.setBuffSpeed(fixedValue);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_SPEED;
    }
}
