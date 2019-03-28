package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 减少固定速度
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 17:19:27
 */
public class SpeedWeakenFixedValueBuff extends FixedValueBuff {

    public SpeedWeakenFixedValueBuff(int fixedValue, int lastRounds) {
        super(fixedValue, lastRounds);
        setName("减少固定速度");
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int speed = roleMetaData.getSpeed();
        speed += fixedValue;
        fightable.setBuffSpeed(roleMetaData.getSpeed() - speed);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.WEAKEN_SPEED;
    }
}
