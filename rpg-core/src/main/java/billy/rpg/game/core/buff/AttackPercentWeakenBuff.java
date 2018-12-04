package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-22 10:32:25
 */
public class AttackPercentWeakenBuff extends Buff {
    private int valuePercent; // 减少的比率，该值/100.0即为显示值，如valueRatio == 80，则削弱80%

    public AttackPercentWeakenBuff(int valuePercent, int lastRounds) {
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
        return BuffType.WEAKEN_ATTACK;
    }

    public int getValuePercent() {
        return valuePercent;
    }
}
