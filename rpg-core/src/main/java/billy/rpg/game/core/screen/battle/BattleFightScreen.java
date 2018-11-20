package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.character.MonsterCharacter;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.constants.ScreenCodeEnum;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.equip.clothes.ClothesEquip;
import billy.rpg.game.core.equip.weapon.WeaponEquip;
import billy.rpg.game.core.listener.CommonAttackListener;
import billy.rpg.game.core.screen.AnimationScreen;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.battle.end.BattleDefeatScreen;
import billy.rpg.game.core.screen.battle.end.BattleSuccessScreen;
import billy.rpg.resource.skill.SkillMetaData;

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
    private int battleActionCurIndex = 0;

    public BattleUIScreen getBattleUIScreen() {
        return battleUIScreen;
    }

    public BattleFightScreen(BattleUIScreen battleUIScreen, List<BattleAction> actionList) {
        this.battleUIScreen = battleUIScreen;
        this.actionList = actionList;
    }


    @Override
    public void update(GameContainer gameContainer, long delta) {
        if (battleActionPreIndex != battleActionCurIndex) {
            logger.debug("doAttack..," +battleActionPreIndex + "," + battleActionCurIndex);
            battleActionPreIndex = battleActionCurIndex;
            logger.debug("doAttack at " + battleActionPreIndex + "," + battleActionCurIndex);
            if (battleActionCurIndex < actionList.size()) {
                update0(gameContainer);
            } else {
                logger.debug("一回合终于打完了");
                getBattleUIScreen().fighting = false;
                getBattleUIScreen().getParentScreen().pop();
                getBattleUIScreen().heroIndex = 0; // TODO 提取成方法？ 将当前活动的heroIndex置为首个
                getBattleUIScreen().actionList.clear(); // 清空播放动画
            }
        }
    }

    private void update0(GameContainer gameContainer) {
        BattleAction battleAction = actionList.get(battleActionCurIndex);
        boolean fromHero = battleAction.fromHero;
        if (!fromHero) {
            monsterAction(gameContainer, battleAction);
        } else {
            heroAction(gameContainer, battleAction);
        }
    }

    /**
     * 妖怪行动的显示
     */
    private void monsterAction(GameContainer gameContainer, BattleAction battleAction) {
        // 攻击者已阵亡的话，就不能攻击了，此时要换下个攻击者
        int attackerId = battleAction.attackerId;
        if (getBattleUIScreen().monsterBattleList.get(attackerId).isDied()) {
            nextAction();
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
                final int finalTargetIndex = targetIndex;
                getBattleUIScreen().getParentScreen().push(
                    new BattleCommonActionScreen(
                            getBattleUIScreen().monsterBattleList.get(attackerId),
                            getBattleUIScreen().heroBattleList.get(finalTargetIndex),
                            new CommonAttackListener() {
                                @Override
                                public int doGetAttackDamage() {
                                    return getCommonAttackDamage(BattleAction.FROM_MONSTER, attackerId,
                                            finalTargetIndex);
                                }
                                @Override
                                public void doAttack(int dmg) {
                                    doCauseDamage(BattleAction.FROM_MONSTER, attackerId, finalTargetIndex, dmg);
                                }
                                @Override
                                public void onFinished() {
                                    getBattleUIScreen().getParentScreen().pop();
                                    nextAction();
                                    checkWinOrLose(gameContainer);
                                }
                            }));
            }
            break;
            case BattleAction.ACTION_SKILL: { // 技能攻击
                // 技能攻击时，攻击者不应向目标行动
                logger.debug("使用技能攻击");
                Fightable targetFightableCharacter = getBattleUIScreen().heroBattleList.get(targetIndex);
                AnimationScreen bs = new AnimationScreen(gameContainer, 2,
                        targetFightableCharacter.getLeft() - targetFightableCharacter.getWidth() / 2,
                        targetFightableCharacter.getTop(), getBattleUIScreen().getParentScreen());
                final int finalTargetIndex = targetIndex;
                getBattleUIScreen().getParentScreen().push(
                    new BattleSkillActionScreen(
                        getBattleUIScreen().monsterBattleList.get(attackerId),
                        getBattleUIScreen().heroBattleList.get(finalTargetIndex),
                        bs,
                        new CommonAttackListener() {
                            @Override
                            public int doGetAttackDamage() {
                                return getSkillAttackDamage(gameContainer, high);
                            }
                            @Override
                            public void doAttack(int dmg) {
                                doCauseDamage(BattleAction.FROM_MONSTER, attackerId, finalTargetIndex, dmg);
                            }
                            @Override
                            public void onFinished() {
                                int consume = gameContainer.getSkillMetaDataOf(high).getConsume();
                                int mp = getBattleUIScreen().monsterBattleList.get(attackerId).getRoleMetaData().getMp();
                                mp-=consume;
                                getBattleUIScreen().monsterBattleList.get(attackerId).getRoleMetaData().setMp(mp);
                                getBattleUIScreen().getParentScreen().pop();
                                nextAction();
                                checkWinOrLose(gameContainer);
                            }
                        }));
            }
            break;
            case BattleAction.ACTION_GOODS: {
                // TODO
                logger.debug("妖怪不会使用物品");
            }
            break;
            case BattleAction.ACTION_FLEE: {
                // TODO
                logger.debug("非特殊妖怪不会逃跑");
            }
            break;
            default:
                logger.debug("cannot be here.");
                break;
        }
    }

    private void nextAction() {
        battleActionCurIndex++;
    }

    /**
     * 玩家行动的显示
     */
    private void heroAction(GameContainer gameContainer, BattleAction battleAction) {
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
                final int finalTargetIndex = targetIndex;
                getBattleUIScreen().getParentScreen().push(
                    new BattleCommonActionScreen(
                        getBattleUIScreen().heroBattleList.get(attackerId),
                        getBattleUIScreen().monsterBattleList.get(finalTargetIndex),
                        new CommonAttackListener() {
                            @Override
                            public int doGetAttackDamage() {
                                return getCommonAttackDamage(BattleAction.FROM_HERO, attackerId, finalTargetIndex);
                            }
                            @Override
                            public void doAttack(int dmg) {
                                doCauseDamage(BattleAction.FROM_HERO, attackerId, finalTargetIndex, dmg);
                            }
                            @Override
                            public void onFinished() {
                                getBattleUIScreen().getParentScreen().pop();
                                nextAction();
                                checkWinOrLose(gameContainer);
                            }
                        }));
            }
            break;
            case BattleAction.ACTION_SKILL: { // 技能
                logger.debug("使用技能攻击妖怪");
                Fightable chosenMonsterBattle = getBattleUIScreen().monsterBattleList.get(targetIndex);
                final SkillMetaData smd = gameContainer.getSkillMetaDataOf(high);
                AnimationScreen bs = new AnimationScreen(gameContainer, smd.getAnimationId(),
                        chosenMonsterBattle.getLeft() - chosenMonsterBattle.getWidth() / 2,
                        chosenMonsterBattle.getTop(), getBattleUIScreen().getParentScreen());
                final int finalTargetIndex = targetIndex;
                getBattleUIScreen().getParentScreen().push(
                        new BattleSkillActionScreen(
                                getBattleUIScreen().heroBattleList.get(attackerId),
                                getBattleUIScreen().monsterBattleList.get(finalTargetIndex),
                                bs,
                                new CommonAttackListener() {
                                    @Override
                                    public int doGetAttackDamage() {
                                        return getSkillAttackDamage(gameContainer, high);
                                    }
                                    @Override
                                    public void doAttack(int dmg) {
                                        doCauseDamage(BattleAction.FROM_HERO, attackerId, finalTargetIndex, dmg);
                                    }
                                    @Override
                                    public void onFinished() {
                                        int consume = gameContainer.getSkillMetaDataOf(high).getConsume();
                                        int mp = getBattleUIScreen().heroBattleList.get(attackerId).getRoleMetaData().getMp();
                                        mp-=consume;
                                        getBattleUIScreen().heroBattleList.get(attackerId).getRoleMetaData().setMp(mp);
                                        getBattleUIScreen().getParentScreen().pop();
                                        nextAction();
                                        checkWinOrLose(gameContainer);
                                    }
                                }));
            }
            break;
            case BattleAction.ACTION_GOODS: {
                logger.debug("暂没有物品");
            }
            break;
            case BattleAction.ACTION_FLEE: {
                logger.debug("众妖怪：菜鸡别跑！");
                // TODO 金币减少100*妖怪数量。
                // TODO 此时要是逃跑成功，应该跳到地图界面
                gameContainer.getGameFrame().changeScreen(ScreenCodeEnum.SCREEN_CODE_MAP_SCREEN);
            }
            break;
            default:
                logger.debug("cannot be here: " + actionType);
                break;
        }
    }

    /**
     * 计算技能伤害，TODO 暂时只处理固定伤害
     * @param skillId skillId
     */
    private int getSkillAttackDamage(GameContainer gameContainer, int skillId) {
        SkillMetaData skill = gameContainer.getSkillMetaDataOf(skillId);
        return skill.getBaseDamage();
    }

    private int getCommonAttackDamage(boolean fromHero, int attackerId, int targetIndex) {
        int attack = 0;
        int defend = 0;
        if (fromHero) {
            HeroCharacter heroCharacter = getBattleUIScreen().heroBattleList.get(attackerId);
            // 把装备的攻击力也计算进去
            attack = heroCharacter.getRoleMetaData().getAttack() + ((WeaponEquip)(heroCharacter.getEquipables().getWeapon().getEquip())).getAttack();

            MonsterCharacter target = getBattleUIScreen().monsterBattleList.get(targetIndex);
            defend = target.getRoleMetaData().getDefend() + ((ClothesEquip)(target.getEquipables().getClothes().getEquip())).getDefend();
        } else {
            MonsterCharacter attacker = getBattleUIScreen().monsterBattleList.get(attackerId);
            attack = attacker.getRoleMetaData().getAttack() + ((WeaponEquip)(attacker.getEquipables().getWeapon().getEquip())).getAttack();

            HeroCharacter heroCharacter = getBattleUIScreen().heroBattleList.get(targetIndex);
            // 把装备的防御力也计算
            defend = heroCharacter.getRoleMetaData().getDefend() + ((ClothesEquip)(heroCharacter.getEquipables().getClothes().getEquip())).getDefend();
        }

        float dmgF = 1.0f * (attack*1.0f) * (100f/(defend+100f));
//        float v = 1.0f * attacker.getRoleMetaData().getSpeed() *
//                attacker.getRoleMetaData().getHp() / attacker.getRoleMetaData().getMaxHp();
//        dmgF += GameConstant.random.nextInt((int)(Math.ceil(v)) + 1);
        return (int)dmgF;
    }
    /**
     * 攻击伤害判定
     *
     * @param fromHero true是玩家发起的，false是妖怪发起的
     * @param attackerId 攻击者索引 TODO 这名字不应该用id,应该用index啊
     * @param targetIndex 被攻击者索引
     */
    private void doCauseDamage(boolean fromHero, int attackerId, int targetIndex, int dmg) {
        Fightable target = null;
        String attackerName = null;
        String targetName = null;
        if (fromHero) {
            target = getBattleUIScreen().monsterBattleList.get(targetIndex);
            attackerName = "玩家";
            targetName = "妖怪";
        } else {
            target = getBattleUIScreen().heroBattleList.get(targetIndex);
            attackerName = "妖怪";
            targetName = "玩家";
        }

        logger.debug(attackerName + attackerId + " --> " + targetName + targetIndex);
        int hp = target.getRoleMetaData().getHp();
        hp -= dmg;
        target.getRoleMetaData().setHp(hp);

        String msgText = attackerName + attackerId + "对"+targetName+targetIndex+"造成了"+dmg + "伤害，";
        if (hp <= 0) {
            msgText += targetName + targetIndex + "的小身板扛不住就挂了";
            target.setDied(true);
        }

        msgText += "。";
        logger.debug(msgText);
        // TODO 先不显示战斗信息了
        // appendMsg(msgText);
    }


    /**
     * 胜负判定
     * 先判断玩家是不是挂了
     * 再判断妖怪是不是全挂了
     * TODO 有没有玩家和妖怪同时全挂的情况？
     */
    private void checkWinOrLose(GameContainer gameContainer) {
        boolean heroAllDieFlag = true;
        for (HeroCharacter heroCharacter : getBattleUIScreen().heroBattleList) {
            if (!heroCharacter.isDied()) {
                heroAllDieFlag = false;
            }
        }
        if (heroAllDieFlag) {
            BattleDefeatScreen defeat = new BattleDefeatScreen();
            getBattleUIScreen().getParentScreen().push(defeat);
            return;
        }

        boolean monsterDieAllFlag = true;
        for (MonsterCharacter monsterCharacter : getBattleUIScreen().monsterBattleList) {
            if (!monsterCharacter.isDied()) {
                monsterDieAllFlag = false;
            }
        }

        if (monsterDieAllFlag) {
            BattleSuccessScreen success = new BattleSuccessScreen(gameContainer,
                    getBattleUIScreen().getParentScreen(),
                    getBattleUIScreen().money,
                    getBattleUIScreen().exp);
            getBattleUIScreen().getParentScreen().push(success);
        }

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {

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
