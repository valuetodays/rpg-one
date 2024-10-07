package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 减少百分比防御力
 *
 * @author lei.liu
 * @since 2018-12-06 17:06:25
 */
public class DefendWeakenPercentValueBuff extends PercentValueBuff {
    public DefendWeakenPercentValueBuff(int percentValue, int lastRounds) {
        super(percentValue, lastRounds);
        setName("减少百分比防御力");
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int defend = roleMetaData.getDefend();
        defend += defend * (percentValue / 100.0); // 此时的attack是最终值，减去角色本身的属性值就是增量
        fightable.setBuffDefend(roleMetaData.getDefend() - defend);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.WEAKEN_DEFEND;
    }
}
