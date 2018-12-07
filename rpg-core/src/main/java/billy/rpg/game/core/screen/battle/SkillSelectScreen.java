package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.MessageBoxScreen;
import billy.rpg.game.core.util.AssertUtil;
import billy.rpg.game.core.util.KeyUtil;
import billy.rpg.resource.skill.SkillMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * 战斗中选择技界面
 */
public class SkillSelectScreen extends BaseScreen {

    private BattleUIScreen battleUIScreen;
    private BattleOptionScreen battleOptionScreen;
    private final java.util.List<SkillMetaData> skillList;
    private int skillIndex;

    public SkillSelectScreen(GameContainer gameContainer, BattleUIScreen battleUIScreen, BattleOptionScreen battleOptionScreen) {
        this.battleUIScreen = battleUIScreen;
        this.battleOptionScreen = battleOptionScreen;

        // 技能列表
        int index = getBattleUIScreen().getActiveHeroIndex();
        this.skillList = gameContainer.getGameData().getSkillsOf(gameContainer, index);
    }

    public BattleUIScreen getBattleUIScreen() {
        return battleUIScreen;
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        if (getBattleUIScreen().fighting) {
            return;
        }

        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        g.setFont(GameConstant.FONT_BATTLE);
        g.setColor(Color.black);
        g.fillRect(0, 0, paint.getWidth(), 320); // 大小和战斗背景一致为640*320
        g.setColor(Color.YELLOW);
        g.drawRoundRect(10, 10, 620, 200, 5, 5);
        Image gameArrowRight =gameContainer.getGameAboutItem().getGameArrowRight();
        g.drawImage(gameArrowRight, 15, 15 + skillIndex * 20, null);
        for (int i = 0; i < skillList.size(); i++) {
            SkillMetaData smd = skillList.get(i);
            g.drawString(smd.getName() + " [消耗" + smd.getConsume() +"]", 30, 30 + i * 25);
        }
        g.drawString(skillList.get(skillIndex).getDescWithTargetType(), 35, 230);

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (KeyUtil.isEsc(key)) {
            getBattleUIScreen().getParentScreen().pop();
        } else if (KeyUtil.isEnter(key)) {
            int skillId = skillList.get(skillIndex).getNumber();
            SkillMetaData skillMetaData = gameContainer.getSkillMetaDataOf(skillId);
            final int mp = getBattleUIScreen().getActiveHero().getRoleMetaData().getMp();
            final int consume = skillMetaData.getConsume();
            if (mp < consume) {
                final BaseScreen bs = new MessageBoxScreen("mp不足，不能施放技能" + skillMetaData.getName(),
                        getBattleUIScreen().getParentScreen());
                getBattleUIScreen().getParentScreen().push(bs);
                return;
            }
            int type = skillMetaData.getType();
            logger.debug("type: " + type);
            if (SkillMetaData.TYPE_ADD_BUFF_TO_OUR == type) {
                opTypeAddBuffToOur(skillMetaData);
            } else if (SkillMetaData.TYPE_ATTACK == type) {
                opTypeAttack(skillMetaData);
            } else {
                throw new RuntimeException("unknwon skill type: " + type);
            }

        } else if (KeyUtil.isUp(key)) {
            skillIndex--;
            if (skillIndex < 0) {
                skillIndex = skillList.size()-1;
            }
        } else if (KeyUtil.isDown(key)) {
            skillIndex++;
            if (skillIndex > skillList.size()-1) {
                skillIndex = 0;
            }
        }
    }

    /**
     * 处理给我方加buff类型的技能
     * @see SkillMetaData#TYPE_ADD_BUFF_TO_OUR
     * @param skillMetaData skill
     */
    private void opTypeAddBuffToOur(SkillMetaData skillMetaData) {
        int skillId = skillMetaData.getAnimationId();

        int targetType = skillMetaData.getTargetType();
        if (SkillMetaData.TARGET_TYPE_SINGLE == targetType) {
            // TODO 加buff的技能还要考虑是否我方队员已死亡的情况，因为要考虑到复活队员的情况
            if (battleUIScreen.ourAliveCount() == 1) {
                Optional<HeroCharacter> firstAlive = battleUIScreen.heroBattleList.stream().filter(e -> !e.isDied()).findFirst();
                AssertUtil.assertTrue(firstAlive.isPresent(), "all are not alive");
                HeroCharacter heroCharacter = firstAlive.get();
                int heroIndex = battleUIScreen.heroBattleList.indexOf(heroCharacter);
                getBattleUIScreen().actionList.add(new BattleAction(BattleAction.FROM_HERO,
                        getBattleUIScreen().heroIndex,
                        heroIndex,
                        battleOptionScreen.heroActionChoice, skillId, 0));
                if (getBattleUIScreen().heroIndex == getBattleUIScreen().heroBattleList.size() - 1) {
                    battleOptionScreen.generateMonsterAttackAction();
                }
                getBattleUIScreen().getParentScreen().pop(); // 将技能选择屏幕清除掉
                getBattleUIScreen().nextHero(); // next hero
//                    battleUIScreen.heroBattleList.stream().filter(e -> !e.isDied()).forEach(buffObject::doApply);
            } else {
                // TODO 选择，将buff施放到哪位队员身上
            }
        } else if (SkillMetaData.TARGET_TYPE_ALL == targetType) {
//                battleUIScreen.heroBattleList.stream().filter(e -> !e.isDied()).forEach(buffObject::doApply);
        }

    }

    /**
     * 处理攻击类型的技能
     * @see SkillMetaData#TYPE_ATTACK
     * @param skillMetaData skill
     */
    private void opTypeAttack(SkillMetaData skillMetaData) {
        int skillId = skillMetaData.getNumber();
        int targetType = skillMetaData.getTargetType();
        logger.debug("targetType: " + targetType);
        if (SkillMetaData.TARGET_TYPE_SINGLE == targetType) { // 单体技能
            int enemySize = battleUIScreen.enemyAliveCount();
            if (enemySize == 1) { // 只有一个敌人
                getBattleUIScreen().actionList.add(new BattleAction(BattleAction.FROM_HERO,
                        getBattleUIScreen().heroIndex,
                        0,
                        battleOptionScreen.heroActionChoice, skillId, 0));
                if (getBattleUIScreen().heroIndex == getBattleUIScreen().heroBattleList.size() - 1) {
                    battleOptionScreen.generateMonsterAttackAction();
                }
                getBattleUIScreen().getParentScreen().pop(); // 将技能选择屏幕清除掉
                getBattleUIScreen().nextHero(); // next hero
            } else {
                MonsterSelectScreen chooseMonsterScreen = new MonsterSelectScreen(getBattleUIScreen(),
                        battleOptionScreen, skillId);
                getBattleUIScreen().getParentScreen().push(chooseMonsterScreen);
            }
        } else if (targetType == SkillMetaData.TARGET_TYPE_ALL) { // 群体技能
            getBattleUIScreen().actionList.add(new BattleAction(BattleAction.FROM_HERO,
                    getBattleUIScreen().heroIndex,
                    -1,
                    battleOptionScreen.heroActionChoice, skillId, 0));
            if (getBattleUIScreen().heroIndex == getBattleUIScreen().heroBattleList.size() - 1) {
                battleOptionScreen.generateMonsterAttackAction();
            }
            getBattleUIScreen().getParentScreen().pop(); // 将技能选择屏幕清除掉
            getBattleUIScreen().nextHero(); // next hero
        }
    }


    @Override
    public boolean isPopup() {
        return true;
    }



}
