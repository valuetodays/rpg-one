package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.callback.CommonAttackCallback;
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
    private java.util.List<Fightable> targets;
    private int targetIndex;
    private int state = STATE_PRE;
    private int attackFrame; // 最多有12帧？？
    private CommonAttackCallback commonAttackListener;
    private final int attackerPreTop;
    private final int attackerPreLeft;
    private final java.util.List<Integer> dmgs;
    private int dmgFrame;
    private int dmgTop;
    private int dmgLeft;

    /**
     *
     * @param attacker 攻击者
     * @param targets 被攻击者(们)
     */
    public BattleCommonActionScreen(Fightable attacker, java.util.List<Fightable> targets, int targetIndex,
                                    CommonAttackCallback al) {
        this.attacker = attacker;
        this.targets = targets;
        this.targetIndex = targetIndex;
        this.commonAttackListener = al;
        this.attackerPreTop = attacker.getTop();
        this.attackerPreLeft = attacker.getLeft();
        this.dmgs = commonAttackListener.doPrepareForAction();
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
        if (state == STATE_PRE) {
            if (attackFrame > 12) {
                state = STATE_ANI;
            } else {
                // 取攻击者和目标的矩形的中心，攻击即是让两个中心重合
                if (targetIndex == -1) { // 全体攻击时，攻击者攻向屏幕中间即可
                    attacker.setLeft(attacker.getLeft() - (150 - attackerPreLeft) / 10);
                    attacker.setTop(attacker.getTop() + (120 - attackerPreTop) / 10);
                    attacker.setAttackFrame(attackFrame++);
                } else {
                    Fightable target = targets.get(targetIndex);
                    int targetX = target.getLeft() - (attacker.getWidth() / 2 - target.getWidth() / 2);
                    int targetY = target.getTop() + (attacker.getHeight() / 2 - target.getHeight() / 2);
                    attacker.setLeft(attacker.getLeft() + (targetX - attackerPreLeft) / 10);
                    attacker.setTop(attacker.getTop() + (targetY - attackerPreTop) / 10);
                    attacker.setAttackFrame(attackFrame++);
                }
            }
        } else if (state == STATE_ANI) {
                state = STATE_AFT;
        } else if (state == STATE_AFT) {
            if (dmgFrame > 10) {
                state = STATE_FIN;
                commonAttackListener.doAction(dmgs);
            } else {
                if (targetIndex == -1) {
                    Fightable target = targets.get(0);
                    int targetCenterX = target.getLeft() + target.getWidth() / 2;
                    int targetY = target.getTop();
                    dmgLeft = targetCenterX;
                    dmgTop = targetY - dmgFrame * 3;
                    dmgFrame++;
                } else {
                    Fightable target = targets.get(targetIndex);
                    int targetCenterX = target.getLeft() + target.getWidth() / 2;
                    int targetY = target.getTop();
                    dmgLeft = targetCenterX;
                    dmgTop = targetY - dmgFrame * 3;
                    dmgFrame++;
                }
            }


        } else if (state == STATE_FIN) {
            if (attackFrame == 0) {
                attacker.setLeft(attackerPreLeft);
                attacker.setTop(attackerPreTop);
                commonAttackListener.onFinished();
            } else {
                if (targetIndex == -1) {
                    attacker.setLeft(attacker.getLeft() - (150 - attackerPreLeft) / 10);
                    attacker.setTop(attacker.getTop() - (120 - attackerPreTop) / 10);
                    attacker.setAttackFrame(attackFrame--);
                } else {
                    Fightable target = targets.get(targetIndex);
                    int targetX = target.getLeft() - (attacker.getWidth() / 2 - target.getWidth() / 2);
                    int targetY = target.getTop() + (attacker.getHeight() / 2 - target.getHeight() / 2);
                    attacker.setLeft(attacker.getLeft() - (targetX - attackerPreLeft) / 10);
                    attacker.setTop(attacker.getTop() - (targetY - attackerPreTop) / 10);
                    attacker.setAttackFrame(attackFrame--);
                }
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
            g.setFont(gameContainer.getGameConfig().getDamageFont());
            g.setColor(gameContainer.getGameConfig().getDamageCommonFontColor());
            for (int i = 0; i < dmgs.size(); i++) { // 群攻显示扣除的血量
                Integer dmg = dmgs.get(i);
                g.drawString("-" + dmg, dmgLeft + 100 * i, dmgTop); //
            }

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
