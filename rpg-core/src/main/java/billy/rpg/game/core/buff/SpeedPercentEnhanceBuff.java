package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 增加百分比速度
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 17:25:24
 */
public class SpeedPercentEnhanceBuff extends Buff {
    private int valuePercent;

    public SpeedPercentEnhanceBuff(int valuePercent, int lastRounds) {
        AssertUtil.assertTrue(valuePercent > 0, "valuePercent should greater than zero");
        this.valuePercent = valuePercent;
        super.setLastRounds(lastRounds);
    }

    @Override
    protected void apply(Fightable fightable) {
        RoleMetaData roleMetaData = fightable.getRoleMetaData();
        int speed = roleMetaData.getSpeed();
        speed += speed * (valuePercent / 100.0);
        fightable.setBuffSpeed(speed - roleMetaData.getSpeed());
    }

    @Override
    public BuffType getBuffType() {
        return BuffType.ENHANCE_SPEED;
    }
}
