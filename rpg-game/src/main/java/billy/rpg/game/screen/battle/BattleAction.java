package billy.rpg.game.screen.battle;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-19 11:35
 */
public class BattleAction {
    public static final int ACTION_ATTACK = 1;
    public static final int ACTION_SKILL = 2;
    public static final int ACTION_GOODS = 3;
    public static final int ACTION_FLEE = 4;
    final int attackerId;
    final int targetIndex;
    final int actionType;
    final int high;
    final int low;

    public BattleAction(int attackerId, int targetIndex, int actionType, int high, int low) {
        this.attackerId = attackerId;
        this.targetIndex = targetIndex;
        this.actionType = actionType;
        this.high = high;
        this.low = low;
    }

    @Override
    public String toString() {
        return "BattleAction{" +
                "attackerId=" + attackerId +
                ", targetIndex=" + targetIndex +
                ", actionType=" + actionType +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
}
