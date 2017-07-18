package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.game.character.battle.MonsterBattle;
import billy.rpg.game.screen.AnimationScreen;
import billy.rpg.game.screen.BaseScreen;

public class BattleActionScreen extends BaseScreen {
    private static final int STATE_PRE = 1; // 起手动画
    private static final int STATE_ANI = 2; // 魔法动画
    private static final int STATE_AFT = 3; // 伤害动画
    private static final int STATE_FIN = 4; // 结束

    private HeroBattle attacker;
    private MonsterBattle target;
    private AnimationScreen animationScreen;
    private int state = STATE_PRE;
    private static final int ATTACKER_FRAMES = 10;
    private int attackFrame;
    private AttackAnimationFinishedListener afterListener;
    private int attackerPreTopPos;
    /**
     *
     * @param attacker TODO 战斗角色类
     * @param target
     * @param animationScreen 技能动画
     */
    public BattleActionScreen(HeroBattle attacker, MonsterBattle target, AnimationScreen animationScreen, AttackAnimationFinishedListener al) {
        this.attacker = attacker;
        this.target = target;
        this.animationScreen = animationScreen;
        afterListener = al;
        attackerPreTopPos = attacker.getTop();
    }

    @Override
    public void update(long delta) {
        if (state == STATE_PRE) {
            if (attackFrame > 10) {
                state = STATE_ANI;
            } else {
                attacker.setTop(attacker.getTop() - 10);
                attackFrame++;
            }
        } else if (state == STATE_ANI) {
            if (!animationScreen.update()) {
                state = STATE_AFT;
            }
        } else if (state == STATE_AFT) {
            state = STATE_FIN;
        } else if (state == STATE_FIN) {
            attacker.setTop(attackerPreTopPos);
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
