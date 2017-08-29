package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.character.battle.FightableCharacter;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.AnimationScreen;
import billy.rpg.game.screen.BaseScreen;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 一次战斗(技能)动画
 */
public class BattleSkillActionScreen extends BaseScreen {
    private static final int STATE_PRE = 1; // 起手动画
    private static final int STATE_ANI = 2; // 魔法动画
    private static final int STATE_AFT = 3; // 伤害动画
    private static final int STATE_FIN = 4; // 结束

    private FightableCharacter attacker;
    private FightableCharacter target;
    private AnimationScreen animationScreen;
    private int state = STATE_PRE;
    private CommonAttackListener commonAttackListener;
    private final int attackerPreTop;
    private final int attackerPreLeft;
    private final int dmg;
    private int dmgFrame;
    private int dmgTop;
    private int dmgLeft;

    /**
     *
     * @param attacker 攻击者
     * @param target 被攻击者
     * @param animationScreen 技能动画
     */
    public BattleSkillActionScreen(FightableCharacter attacker, FightableCharacter target, AnimationScreen animationScreen,
                                   CommonAttackListener commonAttackListener) {
        this.attacker = attacker;
        this.target = target;
        this.animationScreen = animationScreen;
        this.commonAttackListener = commonAttackListener;
        this.attackerPreTop = attacker.getTop();
        this.attackerPreLeft = attacker.getLeft();
        this.dmg = commonAttackListener.doGetAttackDamage();
    }

    @Override
    public void update(long delta) {
        if (state == STATE_PRE) {
            state = STATE_ANI;
        } else if (state == STATE_ANI) {
            if (animationScreen != null) {
                if (!animationScreen.update()) {
                    state = STATE_AFT;
                }
            } else {
                state = STATE_AFT;
            }

        } else if (state == STATE_AFT) {
            if (dmgFrame > 10) {
                state = STATE_FIN;
                commonAttackListener.doAttack();
            } else {
                int targetCenterX = target.getLeft() + target.getWidth()/2;
                int targetY       = target.getTop();
                dmgLeft = targetCenterX;
                dmgTop = targetY - dmgFrame*3;
                dmgFrame++;
            }


        } else if (state == STATE_FIN) {
            commonAttackListener.onFinished();
        }
    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        if (state == STATE_PRE) {

        } else if (state == STATE_ANI) {

            if (animationScreen != null) {
                animationScreen.draw(gameCanvas);
            }
        } else if (state == STATE_AFT) {
            BufferedImage paint = new BufferedImage(
                    GameConstant.GAME_WIDTH,
                    GameConstant.GAME_HEIGHT,
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = paint.getGraphics();
            g.setFont(GameConstant.FONT_DAMAGE);
            g.setColor(Color.red);
            g.drawString("-" + dmg, dmgLeft, dmgTop);
            //LOG.debug("damage & pos: -" + dmg + " " + dmgLeft + " " + dmgTop);
            g.dispose();
            gameCanvas.drawBitmap(paint, 0, 0);

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
