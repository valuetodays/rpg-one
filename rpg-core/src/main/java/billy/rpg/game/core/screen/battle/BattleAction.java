package billy.rpg.game.core.screen.battle;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-19 11:35
 */
public class BattleAction {

    public enum BattleOption {
        COMMON(0, "普攻"),
        SKILL(1, "技能"),
        GOODS(2, "物品"),
        STATE(3, "状态"),
        ESCAPE(4, "逃跑");

        private int order;
        private String desc;

        BattleOption(int order, String desc) {
            this.order = order;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public int getOrder() {
            return order;
        }
    }

    public static final boolean FROM_PLAYER = true;
    public static final boolean FROM_ENEMY = !FROM_PLAYER;

    final boolean fromPlayer; // 是玩家发起的攻击，否则就是敌方
    final int attackerId;
    final int targetIndex;
    final int actionType; // 普攻，技能等
    final int high;
    final int low;

    /**
     *
     * @param fromHero
     * @param attackerId
     * @param targetIndex targetIndex = -1 时说明是全体攻击，其它时为被攻击的索引
     * @param actionType 行动类型，
     * @param high 技能编号，当行动类型为｛@link #ACTION_SKILL}时该值有效
     * @param low preserved
     */
    public BattleAction(boolean fromHero, int attackerId, int targetIndex, int actionType, int high, int low) {
        this.fromPlayer = fromHero;
        this.attackerId = attackerId;
        this.targetIndex = targetIndex;
        this.actionType = actionType;
        this.high = high;
        this.low = low;
    }

    @Override
    public String toString() {
        return "BattleAction{" +
                "fromPlayer=" + fromPlayer +
                ", attackerId=" + attackerId +
                ", targetIndex=" + targetIndex +
                ", actionType=" + actionType +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
}
