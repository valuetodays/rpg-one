package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.battle.FightableCharacter;
import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.game.character.battle.MonsterBattle;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.AnimationScreen;
import billy.rpg.game.screen.BaseScreen;

import java.util.List;

/**
 * 战斗行动，轮番播放
 *
 *
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
        if (battleActionPreIndex != battleActionCurIndex) {
            LOG.debug("2..attack..," +battleActionPreIndex + "," + battleActionCurIndex);
            battleActionPreIndex = battleActionCurIndex;
            if (battleActionCurIndex < actionList.size()) {
                LOG.debug("attack at " + battleActionPreIndex + "," + battleActionCurIndex);
                update0();
            } else {
                LOG.debug("一回合终于打完了");
                getBattleUIScreen().fighting = false;
                getBattleUIScreen().getParentScreen().pop();
                getBattleUIScreen().heroIndex = 0; // TODO 提取成方法？ 将当前活动的heroIndex置为首个
                getBattleUIScreen().actionList.clear(); // 清空播放动画
            }
        }
    }

    private void update0() {
        BattleAction battleAction = actionList.get(battleActionCurIndex);
        boolean fromHero = battleAction.fromHero;
        if (!fromHero) {
            monsterAction(battleAction);
        } else {
            heroAction(battleAction);
        }
    }

    /**
     * 妖怪行动的显示
     */
    private void monsterAction(BattleAction battleAction) {
        // 攻击者已阵亡的话，就不能攻击了
        int attackerId = battleAction.attackerId;
        if (getBattleUIScreen().monsterBattleList.get(attackerId).isDied()) {
            return;
        }
        // 当妖怪阵亡时，就从第一个开始选择一个未死亡的进行攻击
        int targetIndex = battleAction.targetIndex;
        if (getBattleUIScreen().heroBattleList.get(targetIndex).isDied()) {
            targetIndex = 0;
            while (getBattleUIScreen().heroBattleList.get(targetIndex).isDied()) {
                targetIndex++;
            }
        }

        int actionType = battleAction.actionType;
        int high = battleAction.high;
        int low = battleAction.low;

        switch (actionType) {
            case BattleAction.ACTION_ATTACK: { // 普攻
                FightableCharacter targetFightableCharacter = getBattleUIScreen().heroBattleList.get(targetIndex);
                AnimationScreen bs = new AnimationScreen(2,
                        targetFightableCharacter.getLeft() - targetFightableCharacter.getWidth() / 2,
                        targetFightableCharacter.getTop(), getBattleUIScreen().getParentScreen());
                final int finalTargetIndex = targetIndex;
                getBattleUIScreen().getParentScreen().push(
                    new BattleActionScreen(
                            getBattleUIScreen().monsterBattleList.get(attackerId),
                            getBattleUIScreen().heroBattleList.get(finalTargetIndex),
                            bs,
                            new AttackAnimationFinishedListener() {
                                @Override
                                public void onFinished() {
                                    doCommonAttack(BattleAction.FROM_MONSTER, attackerId, finalTargetIndex);
                                    getBattleUIScreen().getParentScreen().pop();
                                    battleActionCurIndex++;
                                    checkWinOrLose();
                                }
                            }));
            }
            break;
            case BattleAction.ACTION_SKILL: { // 技能
                LOG.debug("使用技能攻击");
            }
            break;
            case BattleAction.ACTION_GOODS: {
                // TODO
                LOG.debug("妖怪不会使用物品");
            }
            break;
            case BattleAction.ACTION_FLEE: {
                // TODO
                LOG.debug("非特殊妖怪不会逃跑");
            }
            break;
            default:
                LOG.debug("cannot be here.");
                break;
        }
    }

    /**
     * 玩家行动的显示
     */
    private void heroAction(BattleAction battleAction) {
        // 攻击者已阵亡的话，就不能攻击了
        int attackerId = battleAction.attackerId;
        if (getBattleUIScreen().heroBattleList.get(attackerId).isDied()) {
            return;
        }
        // 当妖怪阵亡时，就从第一个开始选择一个未死亡的进行攻击
        int targetIndex = battleAction.targetIndex;
        if (getBattleUIScreen().monsterBattleList.get(targetIndex).isDied()) {
            targetIndex = 0;
            while (getBattleUIScreen().monsterBattleList.get(targetIndex).isDied()) {
                targetIndex++;
            }
        }

        int actionType = battleAction.actionType;
        int high = battleAction.high;
        int low = battleAction.low;

        switch (actionType) {
            case BattleAction.ACTION_ATTACK: { // 普攻
                FightableCharacter chosenMonsterBattle = getBattleUIScreen().monsterBattleList.get(targetIndex);
                AnimationScreen bs = new AnimationScreen(2,
                        chosenMonsterBattle.getLeft() - chosenMonsterBattle.getWidth() / 2,
                        chosenMonsterBattle.getTop(), getBattleUIScreen().getParentScreen());
                final int finalTargetIndex = targetIndex;
                getBattleUIScreen().getParentScreen().push(
                        new BattleActionScreen(
                                getBattleUIScreen().heroBattleList.get(attackerId),
                                getBattleUIScreen().monsterBattleList.get(finalTargetIndex),
                                bs,
                                new AttackAnimationFinishedListener() {
                                    @Override
                                    public void onFinished() {
                                        doCommonAttack(BattleAction.FROM_HERO, attackerId, finalTargetIndex);
                                        getBattleUIScreen().getParentScreen().pop();
                                        battleActionCurIndex++;
                                        checkWinOrLose();
                                    }
                                }));
            }
            break;
            case BattleAction.ACTION_SKILL: { // 技能
                LOG.debug("使用技能攻击妖怪");
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

    /**
     * 普攻
     * @param fromHero true是玩家发起的，false是妖怪发起的
     * @param attackerId 攻击者索引 TODO 这名字不应该用id,应该用index啊
     * @param targetIndex 被攻击者索引
     */
    private void doCommonAttack(boolean fromHero, int attackerId, int targetIndex) {
        FightableCharacter attacker = null;
        FightableCharacter target = null;
        String attackerName = null;
        String targetName = null;
        if (fromHero) {
            attacker = getBattleUIScreen().heroBattleList.get(attackerId);
            target = getBattleUIScreen().monsterBattleList.get(targetIndex);
            attackerName = "玩家";
            targetName = "妖怪";
        } else {
            attacker = getBattleUIScreen().monsterBattleList.get(attackerId);
            target = getBattleUIScreen().heroBattleList.get(targetIndex);
            attackerName = "妖怪";
            targetName = "玩家";
        }
        int attack = attacker.getRoleMetaData().getAttack();
        int defend = target.getRoleMetaData().getDefend();

        LOG.debug(attackerName + attackerId + " --> " + targetName + targetIndex);
        float dmgF = 1.0f * (attack*1) / (defend/100+1);
        float v = 1.0f * attacker.getRoleMetaData().getSpeed() *
                attacker.getRoleMetaData().getHp() / attacker.getRoleMetaData().getMaxHp();
        dmgF += GameConstant.random.nextInt((int)(Math.ceil(v)) + 1);
        int dmg = (int)dmgF;
        int hp = target.getRoleMetaData().getHp();
        hp -= dmg;
        target.getRoleMetaData().setHp(hp);

        String msgText = attackerName + attackerId + "对"+targetName+targetIndex+"造成了"+dmg + "伤害，";
        if (hp <= 0) {
            msgText += targetName + targetIndex + "的小身板扛不住就挂了";
            target.setDied(true);
        }

        msgText += "。";
        LOG.debug(msgText);
        // TODO 先不显示战斗信息了
        // appendMsg(msgText);
    }


    /**
     * 胜负判定
     * 先判断玩家是不是挂了
     * 再判断妖怪是不是全挂了
     */
    private void checkWinOrLose() {
        boolean heroAllDieFlag = true;
        for (HeroBattle heroBattle : getBattleUIScreen().heroBattleList) {
            if (!heroBattle.isDied()) {
                heroAllDieFlag = false;
            }
        }
        if (heroAllDieFlag) {
            LOG.debug("defeat -_-||| show defeat ui");
            BattleDefeatScreen defeat = new BattleDefeatScreen();
            getBattleUIScreen().getParentScreen().push(defeat);
            return;
        }

        boolean monsterDieAllFlag = true;
        for (MonsterBattle monsterBattle : getBattleUIScreen().monsterBattleList) {
            if (!monsterBattle.isDied()) {
                monsterDieAllFlag = false;
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
