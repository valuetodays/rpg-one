package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.game.character.battle.MonsterBattle;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.AnimationScreen;
import billy.rpg.game.screen.BaseScreen;

import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-19 14:01
 */
public class BattleFightScreen extends BaseScreen {
    private final BattleUIScreen battleUIScreen;
    private final List<BattleAction> actionList;
    private int battleActionPreIndex = -1;
    private int battleActionCurIndex;

    public BattleUIScreen getBattleUIScreen() {
        return battleUIScreen;
    }

    public BattleFightScreen(BattleUIScreen battleUIScreen, List<BattleAction> actionList) {
        this.battleUIScreen = battleUIScreen;
        this.actionList = actionList;
    }

    @Override
    public void update(long delta) {
        if (battleActionPreIndex == -1) {
            if (battleActionCurIndex == 0) {
                battleActionPreIndex = battleActionCurIndex;
                update0();
            }
        } else {
            if (battleActionPreIndex + 1 == battleActionCurIndex) {
                battleActionPreIndex = battleActionCurIndex;
                update0();
            }
        }
    }

    private void update0() {
        if (battleActionCurIndex > actionList.size()-1) {
            LOG.debug("终于打完了");
            getBattleUIScreen().fighting = false;
            getBattleUIScreen().getParentScreen().pop();
            BattleOptionScreen battleOptionScreen = new BattleOptionScreen(getBattleUIScreen());
            getBattleUIScreen().getParentScreen().push(battleOptionScreen);
            return;
        }

        BattleAction battleAction = actionList.get(battleActionCurIndex);
        int actionType = battleAction.actionType;
        int attackerId = battleAction.attackerId;
        int targetIndex = battleAction.targetIndex;
        int high = battleAction.high;
        int low = battleAction.low;
        LOG.debug("battleAction: " + battleAction);

        // TODO 全员行动分配完毕，开始行动
        switch (actionType) {
            case BattleAction.ACTION_ATTACK: { // 普攻
                MonsterBattle chosenMonsterBattle = getBattleUIScreen().monsterBattleList.get(targetIndex);
                AnimationScreen bs = new AnimationScreen(2,
                        chosenMonsterBattle.getLeft() - chosenMonsterBattle.getWidth() / 2,
                        chosenMonsterBattle.getTop(), getBattleUIScreen().getParentScreen());
                getBattleUIScreen().getParentScreen().push(
                        new BattleActionScreen(
                            getBattleUIScreen().heroBattleList.get(attackerId),
                            getBattleUIScreen().monsterBattleList.get(targetIndex),
                            bs,
                            new AttackAnimationFinishedListener() {
                                @Override
                                public void onFinished() {
                                    doAttack(battleAction);
//                                    getBattleUIScreen().getParentScreen().pop();
                                    battleActionCurIndex++;
                                    checkWinOrLose();
                                }
                        }));
            }
            break;
            case BattleAction.ACTION_SKILL: { // 技能
                LOG.debug("使用技能攻击妖怪");
                MonsterBattle chosenMonsterBattle = getBattleUIScreen().monsterBattleList.get(targetIndex);
                AnimationScreen bs = new AnimationScreen(2,
                        chosenMonsterBattle.getLeft() - chosenMonsterBattle.getWidth() / 2,
                        chosenMonsterBattle.getTop(), getBattleUIScreen().getParentScreen());
                getBattleUIScreen().getParentScreen().push(
                        new BattleActionScreen(
                                getBattleUIScreen().heroBattleList.get(attackerId),
                                getBattleUIScreen().monsterBattleList.get(targetIndex),
                                bs,
                                new AttackAnimationFinishedListener() {
                                    @Override
                                    public void onFinished() {
                                        doAttack(battleAction);
//                                    getBattleUIScreen().getParentScreen().pop();
                                        battleActionCurIndex++;
                                        checkWinOrLose();
                                    }
                                }));
            }
            break;
            case BattleAction.ACTION_GOODS: {
                LOG.debug("暂没有物品");
            }
            break;
            case BattleAction.ACTION_FLEE: {
                LOG.debug("众妖怪：菜鸡别跑！");
                // TODO 金币减少100*妖怪数量。
                // TODO 此时要是逃跑成功，应该跳到地图界面
                GameFrame.getInstance().changeScreen(1);
            }
            break;
            default:
                LOG.debug("cannot be here.");
                break;
        }

    }

    private void doAttack(BattleAction battleAction) {
        int attackerId = battleAction.attackerId;
        HeroBattle heroBattle = getBattleUIScreen().heroBattleList.get(attackerId);
        int targetIndex = battleAction.targetIndex;
        MonsterBattle monsterBattle = getBattleUIScreen().monsterBattleList.get(targetIndex);
        int attack = heroBattle.getRoleMetaData().getAttack();
        int defend = monsterBattle.getRoleMetaData().getDefend();

        float dmgF = 1.0f * (attack*1) / (defend/100+1);
        dmgF += GameConstant.random.nextInt((int)(Math.floor(1.0f * heroBattle.getRoleMetaData().getSpeed() *
                heroBattle.getRoleMetaData().getHp() / heroBattle.getRoleMetaData().getMaxHp())));
        int dmg = (int)dmgF;
        int hp = monsterBattle.getRoleMetaData().getHp();
        hp -= dmg;
        monsterBattle.getRoleMetaData().setHp(hp);
        String msgText = "玩家"+ attackerId + "对妖怪"+targetIndex+"造成了"+dmg + "伤害";
        if (hp <= 0) {
            msgText += "，妖怪的小身板扛不住就挂了";
            monsterBattle.setDied(true);
        }

        msgText += "。";
        LOG.debug(msgText);
        // TODO 先不显示战斗信息了
        // appendMsg(msgText);
    }


    private void checkWinOrLose() {
        // TODO 应该先判断玩家是不是挂了
        // TODO 再判断妖怪是不是全怪了
        boolean monsterDieAllFlag = false;
        for (MonsterBattle monsterBattle : getBattleUIScreen().monsterBattleList) {
            if (monsterDieAllFlag) {
                break;
            } else if (monsterBattle.isDied()) {
                monsterDieAllFlag = true;
            }
        }

        if (monsterDieAllFlag) {
            LOG.debug("victory!!! show victory ui");
            BattleSuccessScreen success = new BattleSuccessScreen(
                    getBattleUIScreen().heroBattleList,
                    getBattleUIScreen().money,
                    getBattleUIScreen().exp);
            getBattleUIScreen().getParentScreen().push(success);
        }

    }


    @Override
    public void draw(GameCanvas gameCanvas) {

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
