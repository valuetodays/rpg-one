package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 增加百分比攻击力
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-22 10:32:25
 */
public class AttackEnhancePercentValueBuff extends PercentValueBuff {

    public AttackEnhancePercentValueBuff(int percentValue, int lastRounds) {
        super(percentValue, lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int attack = roleMetaData.getAttack();
        attack += (int)(attack * (percentValue / 100.0)); // 此时的attack是最终值，减去角色本身的属性值就是增量
        fightable.setBuffAttack(attack - roleMetaData.getAttack());
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_ATTACK;
    }

}
