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
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.battle.end.BattleDefeatScreen;
import billy.rpg.game.core.screen.battle.end.BattleSuccessScreen;
import billy.rpg.resource.skill.SkillMetaData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                getBattleUIScreen().heroIndex = 0; // 将当前活动的heroIndex置为首个
                getBattleUIScreen().actionList.clear(); // 清空播放动画
            }
        }
    }

    private void update0(GameContainer gameContainer) {
        BattleAction battleAction = actionList.get(battleActionCurIndex);
        boolean fromHero = battleAction.fromHero;
        getBattleUIScreen().markHeroAsAttacker(fromHero);
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

        if (actionType == BattleAction.BattleOption.COMMON.getOrderNum()) { // 普攻
            final int finalTargetIndex = targetIndex;
            getBattleUIScreen().getParentScreen().push(
                new BattleCommonActionScreen(
                    getBattleUIScreen().monsterBattleList.get(attackerId),
                    getBattleUIScreen().heroBattleList.stream().map(e -> (Fightable)e).collect(Collectors.toList()),
                        finalTargetIndex,
                    new CommonAttackListener() {
                        @Override
                        public List<Integer> doGetAttackDamage() {
                            return getCommonAttackDamage(BattleAction.FROM_MONSTER, attackerId,
                                    finalTargetIndex);
                        }
                        @Override
                        public void doAttack(List<Integer> dmg) {
                            doCauseDamage(BattleAction.FROM_MONSTER, attackerId, finalTargetIndex, dmg);
                        }
                        @Override
                        public void onFinished() {
                            getBattleUIScreen().getParentScreen().pop();
                            nextAction();
                            checkWinOrLose(gameContainer);
                        }
                    }));
        } else if (actionType == BattleAction.BattleOption.SKILL.getOrderNum()) { // 技能
            // 技能攻击时，攻击者不应向目标行动
            logger.debug("使用技能攻击");
            final int finalTargetIndex = targetIndex;
            getBattleUIScreen().getParentScreen().push(
                new BattleSkillActionScreen(
                    gameContainer, getBattleUIScreen().getParentScreen(), getBattleUIScreen().monsterBattleList.get(attackerId),
                    getBattleUIScreen().heroBattleList.stream().map(e -> (Fightable)e).collect(Collectors.toList()),
                    finalTargetIndex,
                    high,
                    new CommonAttackListener() {
                        @Override
                        public List<Integer> doGetAttackDamage() {
                            return getSkillAttackDamage(gameContainer, BattleAction.FROM_HERO, attackerId, finalTargetIndex, high);
                        }
                        @Override
                        public void doAttack(List<Integer> dmg) {
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
        } else if (actionType == BattleAction.BattleOption.GOODS.getOrderNum()) {
            // TODO
            logger.debug("妖怪不会使用物品");
        } else if (actionType == BattleAction.BattleOption.ESCAPE.getOrderNum()) {
            // TODO
            logger.debug("非特殊妖怪不会逃跑");
        } else {
            logger.debug("cannot be here.");
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
        if (targetIndex != -1) {
            if (getBattleUIScreen().monsterBattleList.get(targetIndex).isDied()) {
                targetIndex = 0;
                while (getBattleUIScreen().monsterBattleList.get(targetIndex).isDied()) {
                    targetIndex++;
                }
            }
        }

        int actionType = battleAction.actionType;
        int high = battleAction.high;
        int low = battleAction.low;

        if (actionType == BattleAction.BattleOption.COMMON.getOrderNum()) { // 普攻
            logger.debug("使用"+BattleAction.BattleOption.COMMON.getDesc()+"攻击");
            final int finalTargetIndex = targetIndex;
            getBattleUIScreen().getParentScreen().push(
                new BattleCommonActionScreen(
                    getBattleUIScreen().heroBattleList.get(attackerId),
                    getBattleUIScreen().monsterBattleList.stream().map(e -> (Fightable)e).collect(Collectors.toList()),
                    finalTargetIndex,
                    new CommonAttackListener() {
                        @Override
                        public List<Integer> doGetAttackDamage() {
                            return getCommonAttackDamage(BattleAction.FROM_HERO, attackerId, finalTargetIndex);
                        }
                        @Override
                        public void doAttack(List<Integer> dmg) {
                            doCauseDamage(BattleAction.FROM_HERO, attackerId, finalTargetIndex, dmg);
                        }
                        @Override
                        public void onFinished() {
                            getBattleUIScreen().getParentScreen().pop();
                            nextAction();
                            checkWinOrLose(gameContainer);
                        }
                    }));
        } else if (actionType == BattleAction.BattleOption.SKILL.getOrderNum()) { // 技能
            logger.debug("使用"+BattleAction.BattleOption.SKILL.getDesc()+"攻击");
            final int finalTargetIndex = targetIndex;
            getBattleUIScreen().getParentScreen().push(
                    new BattleSkillActionScreen(gameContainer,
                            getBattleUIScreen().getParentScreen(),
                            getBattleUIScreen().heroBattleList.get(attackerId),
                            getBattleUIScreen().monsterBattleList.stream().map(e -> (Fightable)e).collect(Collectors.toList()),
                            finalTargetIndex,
                            high,
                            new CommonAttackListener() {
                                @Override
                                public List<Integer> doGetAttackDamage() {
                                    return getSkillAttackDamage(gameContainer, BattleAction.FROM_HERO, attackerId, finalTargetIndex, high);
                                }
                                @Override
                                public void doAttack(List<Integer> dmg) {
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
        } else if (actionType == BattleAction.BattleOption.GOODS.getOrderNum()) {
            logger.debug("暂没有物品");
        } else if (actionType == BattleAction.BattleOption.ESCAPE.getOrderNum()) {
            logger.debug("众妖怪：菜鸡别跑！");
            // TODO 金币减少100*妖怪数量。
            // TODO 此时要是逃跑成功，应该跳到地图界面
            gameContainer.getGameFrame().changeScreen(ScreenCodeEnum.SCREEN_CODE_MAP_SCREEN);
        } else {
            logger.debug("cannot be here: " + actionType);
        }
    }

    /**
     * 计算技能伤害，TODO 暂时只处理固定伤害
     * @param targetIndex
     * @param skillId skillId
     */
    private List<Integer> getSkillAttackDamage(GameContainer gameContainer, boolean fromHero, int attackerId, int targetIndex, int skillId) {
        SkillMetaData skill = gameContainer.getSkillMetaDataOf(skillId);
        if (targetIndex == -1) {
            return getBattleUIScreen().monsterBattleList.stream().map(target -> {
                return skill.getBaseDamage();
            }).collect(Collectors.toList());
        } else {
            return Collections.singletonList(skill.getBaseDamage());
        }
    }

    private List<Integer> getCommonAttackDamage(boolean fromHero, int attackerId, int targetIndex) {
        int attack = 0;
        int defend = 0;
        List<Integer> damages = new ArrayList<>();
        if (targetIndex != -1) {
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
            damages.add((int)dmgF);
            return damages;
        } else {
            if (fromHero) {
                HeroCharacter heroCharacter = getBattleUIScreen().heroBattleList.get(attackerId);
                // 把装备的攻击力也计算进去
                int attackerAttack = heroCharacter.getRoleMetaData().getAttack() + ((WeaponEquip)(heroCharacter.getEquipables().getWeapon().getEquip())).getAttack();

                return getBattleUIScreen().monsterBattleList.stream().map(target -> {
                    int targetDefend = target.getRoleMetaData().getDefend() + ((ClothesEquip) (target.getEquipables().getClothes().getEquip())).getDefend();
                    float dmgF = 1.0f * (attackerAttack*1.0f) * (100f/(targetDefend+100f));
                    return (int)dmgF;
                }).collect(Collectors.toList());
            } else {
                MonsterCharacter attacker = getBattleUIScreen().monsterBattleList.get(attackerId);
                int attackerAttack = attacker.getRoleMetaData().getAttack() + ((WeaponEquip)(attacker.getEquipables().getWeapon().getEquip())).getAttack();

                return getBattleUIScreen().heroBattleList.stream().map(heroCharacter -> {
                    // 把装备的防御力也计算
                    int targetDefend = heroCharacter.getRoleMetaData().getDefend() + ((ClothesEquip)(heroCharacter.getEquipables().getClothes().getEquip())).getDefend();
                    float dmgF = 1.0f * (attackerAttack*1.0f) * (100f/(targetDefend+100f));
                    return (int)dmgF;
                }).collect(Collectors.toList());
            }
        }
    }
    /**
     * 攻击伤害判定
     *
     * @param fromHero true是玩家发起的，false是妖怪发起的
     * @param attackerId 攻击者索引 TODO 这名字不应该用id,应该用index啊
     * @param targetIndex 被攻击者索引
     */
    private void doCauseDamage(boolean fromHero, int attackerId, int targetIndex, List<Integer> dmgs) {
        if (targetIndex != -1) {
            int dmg = dmgs.get(0); // 不是群体攻击，则只一个值
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

            int hp = target.getRoleMetaData().getHp();
            hp -= dmg;
            target.getRoleMetaData().setHp(hp);

            String msgText = attackerName + attackerId + " 对 " + targetName + targetIndex + "造成了" + dmg + "伤害，";
            if (hp <= 0) {
                msgText += targetName + targetIndex + "的小身板扛不住就挂了";
                target.setDied(true);
            }

            msgText += "。";
            logger.debug(msgText);
            // TODO 先不显示战斗信息了
            // appendMsg(msgText);
        } else {
            String attackerName = null;
            String targetName = null;
            if (fromHero) {
                attackerName = "玩家";
                for (int i = 0; i < getBattleUIScreen().monsterBattleList.size(); i++) {
                    MonsterCharacter targetMonster = getBattleUIScreen().monsterBattleList.get(i);
                    targetName = "妖怪" + i;
                    int hp = targetMonster.getRoleMetaData().getHp();
                    hp -= dmgs.get(i);
                    targetMonster.getRoleMetaData().setHp(hp);
                    String msgText = attackerName + attackerId + " 对 " + targetName + targetIndex + "造成了" + dmgs.get(i) + "伤害，";
                    if (hp <= 0) {
                        msgText += targetName + targetIndex + "的小身板扛不住就挂了";
                        targetMonster.setDied(true);
                    }
                    msgText += "。";
                    logger.debug(msgText);
                }

            } else {
                attackerName = "妖怪";
                for (int i = 0; i < getBattleUIScreen().heroBattleList.size(); i++) {
                    HeroCharacter targetHero = getBattleUIScreen().heroBattleList.get(i);
                    targetName = "玩家" + i;
                    int hp = targetHero.getRoleMetaData().getHp();
                    hp -= dmgs.get(i);
                    targetHero.getRoleMetaData().setHp(hp);
                    String msgText = attackerName + attackerId + " 对 " + targetName + targetIndex + "造成了" + dmgs.get(i) + "伤害，";
                    if (hp <= 0) {
                        msgText += targetName + targetIndex + "的小身板扛不住就挂了";
                        targetHero.setDied(true);
                    }
                    msgText += "。";
                    logger.debug(msgText);
                }
            }
            // TODO 先不显示战斗信息了
            // appendMsg(msgText);
        }
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
