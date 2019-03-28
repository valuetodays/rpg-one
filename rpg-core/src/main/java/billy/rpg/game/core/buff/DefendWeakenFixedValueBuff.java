package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 减少固定防御力
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 16:50:27
 */
public class DefendWeakenFixedValueBuff extends FixedValueBuff {
    /**
     * @param fixedValue 减少的防御力值
     * @param lastRounds 持续的回合数
     */
    public DefendWeakenFixedValueBuff(int fixedValue, int lastRounds) {
        super(fixedValue, lastRounds);
        setName("减少固定防御力");
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int defend = roleMetaData.getDefend();
        defend += fixedValue;
        fightable.setBuffDefend(roleMetaData.getDefend() - defend);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.WEAKEN_DEFEND;
    }
}
