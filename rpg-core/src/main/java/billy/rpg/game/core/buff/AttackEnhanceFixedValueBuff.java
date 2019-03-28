package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;

/**
 * 增加固定攻击力
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 10:32:25
 */
public class AttackEnhanceFixedValueBuff extends FixedValueBuff {

    /**
     * @param fixedValue 增加的攻击力值
     * @param lastRounds 持续的回合数
     */
    public AttackEnhanceFixedValueBuff(int fixedValue, int lastRounds) {
        super(fixedValue, lastRounds);
        setName("增加固定攻击力");
    }

    @Override
    protected void apply(Fightable fightable) {
        fightable.setBuffAttack(fixedValue);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_ATTACK;
    }

}
