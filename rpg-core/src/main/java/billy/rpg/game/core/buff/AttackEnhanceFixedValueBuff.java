package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

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
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int attack = roleMetaData.getAttack();
        attack += fixedValue;
        fightable.setBuffAttack(attack - roleMetaData.getAttack());
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_ATTACK;
    }

}
