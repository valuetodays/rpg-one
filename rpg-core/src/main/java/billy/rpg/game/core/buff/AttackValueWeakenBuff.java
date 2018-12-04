package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 固定减少攻击力
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 10:32:25
 */
public class AttackValueWeakenBuff extends Buff {
    private int value; // 减少的数值 如20即攻击减少20

    /**
     * @param value 减少的攻击力值
     * @param lastRounds 持续的回合数
     */
    public AttackValueWeakenBuff(int value, int lastRounds) {
        AssertUtil.assertTrue(value > 0, "value should greater than zero");
        this.value = value;
        super.setLastRounds(lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int attack = roleMetaData.getAttack();
        attack += value;
        fightable.setBuffAttack(roleMetaData.getAttack() - attack);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.WEAKEN_ATTACK;
    }

    public int getValue() {
        return value;
    }

}
