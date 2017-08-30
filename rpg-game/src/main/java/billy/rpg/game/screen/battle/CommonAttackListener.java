package billy.rpg.game.screen.battle;

/**
 *
 * 战斗攻击动画结束监听
 *
 * @author liulei-home
 * @since 2017-07-15 19:09
 */
public interface CommonAttackListener {
    /**
     * 伤害判断
     * @return 对目标造成了多少伤害。
     */
    int doGetAttackDamage();

    /**
     * 攻击
     * 处理伤害值，等相关，
     * @param dmg 此值不应得新计算（因为计算中含有随机数），应为{@link #doGetAttackDamage()}
     */
    void doAttack(int dmg);

    /**
     * 攻击结束后的动作
     */
    void onFinished();
}