package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.buff.Buff;
import billy.rpg.game.core.buff.util.BuffUtil;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.listener.CommonAttackListener;
import billy.rpg.game.core.screen.AnimationScreen;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.resource.skill.SkillMetaData;

import java.util.List;

/**
 * 一次战斗(技能)动画
 */
public class BattleSkillActionScreen extends BaseScreen {
    private static final int STATE_PRE = 1; // 起手动画
    private static final int STATE_ANI = 2; // 魔法动画
    private static final int STATE_AFT = 3; // 伤害动画
    private static final int STATE_FIN = 4; // 结束

    private Fightable attacker;
    private java.util.List<Fightable> targets;
    private int state = STATE_PRE;
    private CommonAttackListener commonAttackListener;
    private final int attackerPreTop;
    private final int attackerPreLeft;
    private final java.util.List<Integer> dmgs;
    private int dmgFrame;
    private int dmgTop;
    private int dmgLeft;
    private int targetIndex;
    private AnimationScreen animationScreen;
    private final SkillMetaData skillMetaData;

    /**
     *  @param gameContainer
     * @param attacker 攻击者
     * @param targets 被攻击者(们)
     * @param skillId 技能id
     */
    public BattleSkillActionScreen(GameContainer gameContainer, BattleScreen battleScreen, Fightable attacker, List<Fightable> targets, int targetIndex, int skillId,
                                   CommonAttackListener commonAttackListener) {
        this.attacker = attacker;
        this.targets = targets;
        this.targetIndex = targetIndex;
        this.commonAttackListener = commonAttackListener;
        this.attackerPreTop = attacker.getTop();
        this.attackerPreLeft = attacker.getLeft();
        this.dmgs = commonAttackListener.doPrepareForAction();

        skillMetaData = gameContainer.getSkillMetaDataOf(skillId);
        animationScreen = new BattleSkillActionDeterminer().determineAnimationScreen(gameContainer, skillMetaData, targetIndex, battleScreen, targets);
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
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
                int type = skillMetaData.getType();
                if (SkillMetaData.TYPE_ATTACK == type) {
                    commonAttackListener.doAction(dmgs);
                } else if (SkillMetaData.TYPE_ADD_BUFF_TO_OUR == type) {
                    Buff buff = BuffUtil.skillToBuff(skillMetaData);
                    if (targetIndex == -1) {
                        targets.stream().filter(e -> !e.isDied()).forEach(buff::doApply);
                    } else {
                        Fightable chosenHeroBattle = targets.get(targetIndex);
                        buff.doApply(chosenHeroBattle);
                    }
                }
            } else {
                int type = skillMetaData.getType();
                if (SkillMetaData.TYPE_ATTACK == type) {
                    if (targetIndex == -1) { // 全体攻击时，攻击者攻向屏幕中间即可
                        dmgLeft = 640 / 3;
                        dmgTop = 120 - dmgFrame * 3;
                        dmgFrame++;
                    } else {
                        Fightable target = targets.get(targetIndex);
                        int targetCenterX = target.getLeft() + target.getWidth() / 2;
                        int targetY = target.getTop();
                        dmgLeft = targetCenterX;
                        dmgTop = targetY - dmgFrame * 3;
                        dmgFrame++;
                    }
                } else if (SkillMetaData.TYPE_ADD_BUFF_TO_OUR == type) {
                    if (targetIndex == -1) { // 全体攻击时，攻击者攻向屏幕中间即可
                        dmgLeft = 640 / 3;
                        dmgTop = 120 - dmgFrame * 3;
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
            }


        } else if (state == STATE_FIN) {
            commonAttackListener.doAction(dmgs);
            commonAttackListener.onFinished();
        }
    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        if (state == STATE_PRE) {

        } else if (state == STATE_ANI) {
            if (animationScreen != null) {
                animationScreen.draw(gameContainer, desktopCanvas);
            }
        } else if (state == STATE_AFT) {
            new BattleSkillActionDeterminer().onDraw(gameContainer, desktopCanvas, skillMetaData, dmgs, dmgLeft, dmgTop);
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
