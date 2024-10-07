package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 增加百分比速度
 *
 * @author lei.liu
 * @since 2018-12-06 17:25:24
 */
public class SpeedEnhancePercentValueBuff extends PercentValueBuff {

    public SpeedEnhancePercentValueBuff(int percentValue, int lastRounds) {
        super(percentValue, lastRounds);
        setName("增加百分比速度");
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int speed = roleMetaData.getSpeed();
        speed += speed * (percentValue / 100.0);
        fightable.setBuffSpeed(speed - roleMetaData.getSpeed());
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_SPEED;
    }
}
