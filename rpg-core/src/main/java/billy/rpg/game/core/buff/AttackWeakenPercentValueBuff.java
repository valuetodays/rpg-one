package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 减少百分比攻击力
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 10:32:25
 */
public class AttackWeakenPercentValueBuff extends PercentValueBuff {

    public AttackWeakenPercentValueBuff(int percentValue, int lastRounds) {
        super(percentValue, lastRounds);
        setName("减少百分比攻击力");
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int attack = roleMetaData.getAttack();
        fightable.setBuffAttack(-(int)(attack * (percentValue / 100.0)));
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.WEAKEN_ATTACK;
    }

}
