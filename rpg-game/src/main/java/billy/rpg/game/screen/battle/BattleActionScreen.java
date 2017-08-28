package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.character.battle.FightableCharacter;
import billy.rpg.game.screen.AnimationScreen;
import billy.rpg.game.screen.BaseScreen;

/**
 * 一次战斗动画
 */
public class BattleActionScreen extends BaseScreen {
    private static final int STATE_PRE = 1; // 起手动画
    private static final int STATE_ANI = 2; // 魔法动画
    private static final int STATE_AFT = 3; // 伤害动画
    private static final int STATE_FIN = 4; // 结束

    private FightableCharacter attacker;
    private FightableCharacter target;
    private AnimationScreen animationScreen;
    private int state = STATE_PRE;
    private int attackFrame;
    private AttackAnimationFinishedListener afterListener;
    private final int attackerPreTop;
    private final int attackerPreLeft;
    /**
     *
     * @param attacker 攻击者
     * @param target 被攻击者
     * @param animationScreen 技能动画
     */
    public BattleActionScreen(FightableCharacter attacker, FightableCharacter target, AnimationScreen animationScreen,
                              AttackAnimationFinishedListener al) {
        this.attacker = attacker;
        this.target = target;
        this.animationScreen = animationScreen;
        afterListener = al;
        attackerPreTop = attacker.getTop();
        attackerPreLeft = attacker.getLeft();
    }

    @Override
    public void update(long delta) {
        if (state == STATE_PRE) {
            if (attackFrame > 10) {
                state = STATE_ANI;
            } else {
                int targetCenterX = target.getLeft() + target.getWidth()/2;
                int targetY       = target.getTop();
                attacker.setLeft(attacker.getLeft() + (targetCenterX - attackerPreLeft)/10);
                attacker.setTop(attacker.getTop() + (targetY - attackerPreTop)/10);
                attackFrame++;
            }
        } else if (state == STATE_ANI) {
            if (!animationScreen.update()) {
                state = STATE_AFT;
            }
        } else if (state == STATE_AFT) {
            state = STATE_FIN;
        } else if (state == STATE_FIN) {
            attacker.setTop(attackerPreTop);
            attacker.setLeft(attackerPreLeft);
            afterListener.onFinished();
        }

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        if (state == STATE_PRE) {

        } else if (state == STATE_ANI) {
            animationScreen.draw(gameCanvas);
        } else if (state == STATE_AFT) {

        }
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {

    }

    @Override
    public boolean isPopup() {
        return true;
    }
}
