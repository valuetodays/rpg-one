package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 增加百分比防御力
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 17:02:57
 */
public class DefendEnhancePercentValueBuff extends PercentValueBuff {

    public DefendEnhancePercentValueBuff(int percentValue, int lastRounds) {
        super(percentValue, lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int defend = roleMetaData.getDefend();
        fightable.setBuffDefend((int)(defend * (percentValue / 100.0)));
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_DEFEND;
    }
}
