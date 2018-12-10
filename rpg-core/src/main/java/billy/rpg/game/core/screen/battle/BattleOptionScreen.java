package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.MessageBoxScreen;
import billy.rpg.game.core.util.KeyUtil;
import billy.rpg.resource.goods.GoodsMetaData;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *  普攻
    技能
    物品
    状态
    逃跑
 的选项
 * @author liulei@bshf360.com
 * @since 2017-07-17 18:22
 */
public class BattleOptionScreen extends BaseScreen {

    int heroActionChoice = BattleAction.BattleOption.COMMON.getOrder();
    private BattleUIScreen battleUIScreen;

    public BattleOptionScreen(BattleUIScreen battleUIScreen) {
        this.battleUIScreen = battleUIScreen;
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
        g.setColor(Color.WHITE);
        g.drawRoundRect(0, 320, 200, 160, 4, 4);
        g.drawRoundRect(3, 323, 194, 154, 4, 4);
        g.setFont(GameConstant.FONT_BATTLE);
        g.setColor(Color.YELLOW);
        for (BattleAction.BattleOption battleOption : BattleAction.BattleOption.values()) {
            g.drawString(battleOption.getDesc(), 50, 350 + battleOption.getOrder() * 25);
        }

        // 显示用户选项
        Image gameArrowRight = gameContainer.getGameAboutItem().getGameArrowRight();
        g.drawImage(gameArrowRight, 30, (heroActionChoice) * 25 + 333, null);

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }


    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (KeyUtil.isEsc(key)) {
            if (getBattleUIScreen().playerIndex > BattleAction.BattleOption.COMMON.getOrder()) {
                getBattleUIScreen().playerIndex--;
                if (getBattleUIScreen().playerIndex < BattleAction.BattleOption.COMMON.getOrder()) {
                    getBattleUIScreen().playerIndex = BattleAction.BattleOption.COMMON.getOrder();
                }
                getBattleUIScreen().actionList.remove(getBattleUIScreen().playerIndex);
            }
        } else if (KeyUtil.isUp(key)) {
            heroActionChoice--;
            if (heroActionChoice < BattleAction.BattleOption.COMMON.getOrder()) {
                heroActionChoice = BattleAction.BattleOption.values().length-1;
            }
        } else if (KeyUtil.isDown(key)) {
            heroActionChoice++;
            if (heroActionChoice > BattleAction.BattleOption.values().length-1) {
                heroActionChoice = BattleAction.BattleOption.COMMON.getOrder();
            }
        } else if (KeyUtil.isEnter(key)) {
            if (getBattleUIScreen().playerIndex >= getBattleUIScreen().playerBattleList.size()) {
                return;
            }
            logger.debug("heroActionChoice: " + heroActionChoice);
            if (heroActionChoice == BattleAction.BattleOption.COMMON.getOrder()) {  // 普攻
                PlayerCharacter activeHero = getBattleUIScreen().getActivePlayer();
                //
                int range = activeHero.getEquipables().getWeapon().getEquip().getGoods().getRange();
                logger.debug("range: " + range);
                if (range == GoodsMetaData.RANGE_SINGLE) { // 单攻
                    int enemySize = battleUIScreen.enemyAliveCount();
                    if (enemySize == 1) { // 只有一个敌人
                        getBattleUIScreen().actionList.add(new BattleAction(BattleAction.FROM_HERO,
                                getBattleUIScreen().playerIndex,
                                0,
                                heroActionChoice, -1, 0));
                        if (getBattleUIScreen().playerIndex == getBattleUIScreen().playerBattleList.size() - 1) {
                            generateMonsterAttackAction();
                        }
                        getBattleUIScreen().nextHero(); // next hero
                    } else { // 多于一个敌人
                        MonsterSelectScreen chooseMonsterScreen = new MonsterSelectScreen(battleUIScreen, this, -1);
                        getBattleUIScreen().getParentScreen().push(chooseMonsterScreen);
                    }
                } else if (range == GoodsMetaData.RANGE_ALL) { // 群攻
                    // 添加全体攻击效果
                    getBattleUIScreen().actionList.add(new BattleAction(BattleAction.FROM_HERO,
                            getBattleUIScreen().playerIndex,
                            -1,
                            heroActionChoice, 0, 0));
                    if (getBattleUIScreen().playerIndex == getBattleUIScreen().playerBattleList.size() - 1) {
                        generateMonsterAttackAction();
                    }
                    getBattleUIScreen().nextHero(); // next hero
                } else {
                    throw new RuntimeException("unknown range: " + range);
                }
            } else if (heroActionChoice == BattleAction.BattleOption.SKILL.getOrder()) {  // 技能
                PlayerCharacter activeHero = getBattleUIScreen().getActivePlayer();
                String skillIds = activeHero.getRoleMetaData().getSkillIds();
                if (StringUtils.isEmpty(skillIds)) {
                    final BaseScreen bs = new MessageBoxScreen("未习得任何技能，不能施放技能");
                    getBattleUIScreen().getParentScreen().push(bs);
                    return;
                }
                int mp = activeHero.getRoleMetaData().getMp();
                if (mp <= 0) {
                    final BaseScreen bs = new MessageBoxScreen("mp为0，不能施放技能");
                    getBattleUIScreen().getParentScreen().push(bs);
                    return;
                }

                final BaseScreen bs = new SkillSelectScreen(gameContainer, battleUIScreen, this);
                getBattleUIScreen().getParentScreen().push(bs);
            } else if (BattleAction.BattleOption.GOODS.getOrder() == heroActionChoice) {

            } else if (BattleAction.BattleOption.STATE.getOrder() == heroActionChoice) {
                final BattleStateScreen stateScreen = new BattleStateScreen(this, true, getBattleUIScreen().playerIndex);
                getBattleUIScreen().getParentScreen().push(stateScreen);
            } else if (BattleAction.BattleOption.ESCAPE.getOrder() == heroActionChoice) {

            } else {
                throw new RuntimeException("unknown heroActionChoice: " + heroActionChoice);
            }

        }
    }

    /**
     * 生成怪物的攻击方式
     */
    public void generateMonsterAttackAction() {
        for (int i = 0; i < getBattleUIScreen().enemyBattleList.size(); i++) {
            // TODO 暂先随机使用普攻和技能
            int actionAttack = GameConstant.random.nextInt(2);
            int skillId = 0;
            if (BattleAction.BattleOption.SKILL.getOrder() == (actionAttack+1)) {
                skillId = 2;
            }

            BattleAction battleAction = new BattleAction(BattleAction.FROM_MONSTER,
                    i,
                    GameConstant.random.nextInt(getBattleUIScreen().playerBattleList.size()) ,
                    // TODO 暂先随机使用普攻和技能
                    BattleAction.BattleOption.COMMON.getOrder(), skillId, 0);
            getBattleUIScreen().actionList.add(battleAction);
        }
    }


    @Override
    public boolean isPopup() {
        return true;
    }
}
