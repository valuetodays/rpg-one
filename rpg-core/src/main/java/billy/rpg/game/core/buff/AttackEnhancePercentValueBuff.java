package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 增加百分比攻击力
 *
 * @author lei.liu
 * @since 2018-11-22 10:32:25
 */
public class AttackEnhancePercentValueBuff extends PercentValueBuff {

    public AttackEnhancePercentValueBuff(int percentValue, int lastRounds) {
        super(percentValue, lastRounds);
        setName("增加百分比攻击力");
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int attack = roleMetaData.getAttack();
        fightable.setBuffAttack((int)(attack * (percentValue / 100.0)));
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_ATTACK;
    }

}
