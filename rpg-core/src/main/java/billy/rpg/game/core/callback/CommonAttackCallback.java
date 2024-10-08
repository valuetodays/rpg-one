package billy.rpg.game.core.callback;

import java.util.List;

/**
 *
 * 战斗攻击动画结束监听
 *
 * @author liulei-home
 * @since 2017-07-15 19:09
 */
public interface CommonAttackCallback {
    /**
     * 伤害判断
     * @return 对目标造成了多少伤害。
     */
    List<Integer> doPrepareForAction();

    /**
     * 攻击
     * 处理伤害值，等相关，
     * @param dmg 此值不应重新计算（因为计算中含有随机数），应为{@link #doPrepareForAction()}
     */
    void doAction(List<Integer> dmg);

    /**
     * 攻击结束后的动作
     */
    void onFinished();
}
