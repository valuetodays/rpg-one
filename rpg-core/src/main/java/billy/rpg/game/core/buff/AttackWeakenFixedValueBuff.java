package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;

/**
 * 减少固定攻击力
 *
 * @author lei.liu
 * @since 2018-11-22 10:32:25
 */
public class AttackWeakenFixedValueBuff extends FixedValueBuff {

    /**
     * @param fixedValue 减少的攻击力值
     * @param lastRounds 持续的回合数
     */
    public AttackWeakenFixedValueBuff(int fixedValue, int lastRounds) {
        super(fixedValue, lastRounds);
        setName("减少固定攻击力");
    }

    @Override
    protected void apply(Fightable fightable) {
        fightable.setBuffAttack(-fixedValue);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.WEAKEN_ATTACK;
    }

}
