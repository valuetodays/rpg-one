package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 增加固定速度
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 17:18:27
 */
public class SpeedEnhanceFixedValueBuff extends FixedValueBuff {

    public SpeedEnhanceFixedValueBuff(int fixedValue, int lastRounds) {
        super(fixedValue, lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int speed = roleMetaData.getSpeed();
        speed += fixedValue;
        fightable.setBuffSpeed(speed - roleMetaData.getSpeed());
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_SPEED;
    }
}
