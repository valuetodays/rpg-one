package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.listener.CommonAttackListener;
import billy.rpg.game.core.screen.BaseScreen;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 一次战斗(普攻)动画
 */
public class BattleCommonActionScreen extends BaseScreen {
    private static final int STATE_PRE = 1; // 起手动画
    private static final int STATE_ANI = 2; // 魔法动画
    private static final int STATE_AFT = 3; // 伤害动画
    private static final int STATE_FIN = 4; // 结束

    private Fightable attacker;
    private Fightable target;
    private int state = STATE_PRE;
    private int attackFrame; // 最多有12帧？？
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
     */
    public BattleCommonActionScreen(Fightable attacker, Fightable target,
                                    CommonAttackListener al) {
        this.attacker = attacker;
        this.target = target;
        this.commonAttackListener = al;
        this.attackerPreTop = attacker.getTop();
        this.attackerPreLeft = attacker.getLeft();
        this.dmg = commonAttackListener.doGetAttackDamage();
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
        if (state == STATE_PRE) {
            if (attackFrame > 12) {
                state = STATE_ANI;
            } else {
                // 取攻击者和目标的矩形的中心，攻击即是让两个中心重合
                int targetX = target.getLeft() - (attacker.getWidth()/2 - target.getWidth()/2);
                int targetY = target.getTop() + (attacker.getHeight()/2 - target.getHeight()/2);
                attacker.setLeft(attacker.getLeft() + (targetX - attackerPreLeft)/10);
                attacker.setTop(attacker.getTop() + (targetY - attackerPreTop)/10);
                attacker.setAcctackFrame(attackFrame++);
            }
        } else if (state == STATE_ANI) {
                state = STATE_AFT;
        } else if (state == STATE_AFT) {
            if (dmgFrame > 10) {
                state = STATE_FIN;
                commonAttackListener.doAttack(dmg);
            } else {
                int targetCenterX = target.getLeft() + target.getWidth()/2;
                int targetY       = target.getTop();
                dmgLeft = targetCenterX;
                dmgTop = targetY - dmgFrame*3;
                dmgFrame++;
            }


        } else if (state == STATE_FIN) {
            if (attackFrame == 0) {
                attacker.setLeft(attackerPreLeft);
                attacker.setTop(attackerPreTop);
                commonAttackListener.onFinished();
            } else {
                int targetX = target.getLeft() - (attacker.getWidth()/2 - target.getWidth()/2);
                int targetY = target.getTop() + (attacker.getHeight()/2 - target.getHeight()/2);
                attacker.setLeft(attacker.getLeft() - (targetX - attackerPreLeft)/10);
                attacker.setTop(attacker.getTop() - (targetY - attackerPreTop)/10);
                attacker.setAcctackFrame(attackFrame--);
            }
        }
    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        if (state == STATE_PRE) {

        } else if (state == STATE_ANI) {

        } else if (state == STATE_AFT) {
            BufferedImage paint = new BufferedImage(
                    GameConstant.GAME_WIDTH,
                    GameConstant.GAME_HEIGHT,
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = paint.getGraphics();
            g.setFont(GameConstant.FONT_DAMAGE);
            g.setColor(Color.YELLOW);
            g.drawString("-" + dmg, dmgLeft, dmgTop);
            g.dispose();
            desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
        }
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {

    }

    @Override
    public boolean isPopup() {
        return true;
    }
}
