package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 增加固定防御力
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 16:48:27
 */
public class DefendValueEnhanceBuff extends Buff {
    private int value; // 增加值, 如20即防御增加20

    /**
     * @param value 增加的攻击力值
     * @param lastRounds 持续的回合数
     */
    public DefendValueEnhanceBuff(int value, int lastRounds) {
        AssertUtil.assertTrue(value > 0, "value should greater than zero");
        this.value = value;
        super.setLastRounds(lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int defend = roleMetaData.getDefend();
        defend += value;
        fightable.setBuffAttack(defend - roleMetaData.getDefend());
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_DEFEND;
    }
}
