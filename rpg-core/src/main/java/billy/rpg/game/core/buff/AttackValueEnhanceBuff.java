package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 固定增加攻击力
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 10:32:25
 */
public class AttackValueEnhanceBuff extends Buff {
    private int value; // 增加值, 如20即攻击增加20

    /**
     * @param value 增加的攻击力值
     * @param lastRounds 持续的回合数
     */
    public AttackValueEnhanceBuff(int value, int lastRounds) {
        AssertUtil.assertTrue(value > 0, "value should greater than zero");
        this.value = value;
        super.setLastRounds(lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int attack = roleMetaData.getAttack();
        attack += value;
        fightable.setBuffAttack(attack - roleMetaData.getAttack());
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_ATTACK;
    }

    public int getValue() {
        return value;
    }
}
