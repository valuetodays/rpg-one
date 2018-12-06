package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 减少固定速度
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 17:19:27
 */
public class SpeedValueWeakenBuff extends Buff {
    private int value;

    public SpeedValueWeakenBuff(int value, int lastRounds) {
        AssertUtil.assertTrue(value > 0, "value should greater than zero");
        this.value = value;
        super.setLastRounds(lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int speed = roleMetaData.getSpeed();
        speed += value;
        fightable.setBuffSpeed(roleMetaData.getSpeed() - speed);
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.WEAKEN_SPEED;
    }
}
