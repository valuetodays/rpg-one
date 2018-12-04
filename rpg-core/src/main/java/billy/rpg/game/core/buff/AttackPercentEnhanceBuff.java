package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 百分比增加攻击力
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 10:32:25
 */
public class AttackPercentEnhanceBuff extends Buff {
    private int valuePercent; // 增加值, 如20即攻击增加20%

    public AttackPercentEnhanceBuff(int valuePercent, int lastRounds) {
        AssertUtil.assertTrue(valuePercent > 0, "valuePercent should greater than zero");
        this.valuePercent = valuePercent;
        super.setLastRounds(lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int attack = roleMetaData.getAttack();
        attack += attack * (valuePercent / 100.0); // 此时的attack是最终值，减去角色本身的属性值就是增量
        fightable.setBuffAttack(attack - roleMetaData.getAttack());
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_ATTACK;
    }

    public int getValuePercent() {
        return valuePercent;
    }

}
