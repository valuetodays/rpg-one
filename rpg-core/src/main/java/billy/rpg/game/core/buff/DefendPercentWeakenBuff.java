package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 减少百分比防御力
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 17:06:25
 */
public class DefendPercentWeakenBuff extends Buff {
    private int valuePercent; // 增加值, 如20即防御减少20

    public DefendPercentWeakenBuff(int valuePercent, int lastRounds) {
        AssertUtil.assertTrue(valuePercent > 0, "valuePercent should greater than zero");
        this.valuePercent = valuePercent;
        super.setLastRounds(lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int defend = roleMetaData.getDefend();
        defend += defend * (valuePercent / 100.0); // 此时的attack是最终值，减去角色本身的属性值就是增量
        fightable.setBuffDefend(roleMetaData.getDefend() - defend);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.WEAKEN_DEFEND;
    }
}
