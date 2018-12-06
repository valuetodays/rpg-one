package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 增加百分比防御力
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 17:02:57
 */
public class DefendPercentEnhanceBuff extends Buff {
    private int valuePercent; // 增加值, 如20即防御增加20%

    public DefendPercentEnhanceBuff(int valuePercent, int lastRounds) {
        AssertUtil.assertTrue(valuePercent > 0, "valuePercent should greater than zero");
        this.valuePercent = valuePercent;
        super.setLastRounds(lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int defend = roleMetaData.getDefend();
        defend += defend * (valuePercent / 100.0); // 此时的defend是最终值，减去角色本身的属性值就是增量
        fightable.setBuffDefend(defend - roleMetaData.getDefend());
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_DEFEND;
    }
}
