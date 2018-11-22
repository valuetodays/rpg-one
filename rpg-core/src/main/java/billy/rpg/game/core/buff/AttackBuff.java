package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-22 10:32:25
 */
public class AttackBuff extends Buff {
    // 固定增减与百分比增减的优先级，先固定，再百分比
    private int value; // 增加/减少的数值 如20即攻击增加20，-50攻击减少50
    private int valuePercent; // 增加/减少的比率，该值/100.0即为显示值，如valueRatio == 100，则攻击力增加一倍，若为-80，则削弱80%

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int attack = roleMetaData.getAttack();
        attack += value;
        attack += attack * (valuePercent / 100.0); // 此时的attack是最终值，减去角色本身的属性值就是增量
        fightable.setBuffAttack(attack - roleMetaData.getAttack());
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValuePercent() {
        return valuePercent;
    }

    public void setValuePercent(int valuePercent) {
        this.valuePercent = valuePercent;
    }
}
