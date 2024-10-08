package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;

/**
 * 增加固定防御力
 *
 * @author lei.liu
 * @since 2018-12-06 16:48:27
 */
public class DefendEnhanceFixedValueBuff extends FixedValueBuff {

    public DefendEnhanceFixedValueBuff(int fixedValue, int lastRounds) {
        super(fixedValue, lastRounds);
        setName("增加固定防御力");
    }

    @Override
    protected void apply(Fightable fightable) {
        fightable.setBuffDefend(fixedValue);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_DEFEND;
    }
}
