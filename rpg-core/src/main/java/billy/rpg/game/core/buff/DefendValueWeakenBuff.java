package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 减少固定防御力
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 16:50:27
 */
public class DefendValueWeakenBuff extends Buff {
    private int value; // 增加值, 如20即防御减少20

    /**
     * @param value 增加的攻击力值
     * @param lastRounds 持续的回合数
     */
    public DefendValueWeakenBuff(int value, int lastRounds) {
        AssertUtil.assertTrue(value > 0, "value should greater than zero");
        this.value = value;
        super.setLastRounds(lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int defend = roleMetaData.getDefend();
        defend += value;
        fightable.setBuffDefend(roleMetaData.getDefend() - defend);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.WEAKEN_DEFEND;
    }
}
