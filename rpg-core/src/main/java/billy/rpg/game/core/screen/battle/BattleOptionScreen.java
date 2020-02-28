package billy.rpg.game.core.screen.battle;

import billy.rpg.common.exception.UnFinishException;
import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.MessageBoxScreen;
import billy.rpg.game.core.util.KeyUtil;
import billy.rpg.resource.goods.GoodsMetaData;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import org.apache.commons.lang.StringUtils;

/**
 * 战斗菜单：
    普攻
    技能
    物品
    状态
    逃跑
 的选项
 * @author liulei@bshf360.com
 * @since 2017-07-17 18:22
 * @see BattleAction.BattleOption
 */
public class BattleOptionScreen extends BaseScreen {

    int playerActionChoice = BattleAction.BattleOption.COMMON.getOrder();
    private BattleUIScreen battleUIScreen;

    BattleOptionScreen(BattleUIScreen battleUIScreen) {
        this.battleUIScreen = battleUIScreen;
    }

    BattleUIScreen getBattleUIScreen() {
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
        g.drawImage(gameArrowRight, 30, (playerActionChoice) * 25 + 333, null);

        g.dispose();
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
                getBattleUIScreen().battleActionList.remove(getBattleUIScreen().playerIndex);
            }
        } else if (KeyUtil.isUp(key)) {
            playerActionChoice--;
            if (playerActionChoice < BattleAction.BattleOption.COMMON.getOrder()) {
                playerActionChoice = BattleAction.BattleOption.values().length-1;
            }
        } else if (KeyUtil.isDown(key)) {
            playerActionChoice++;
            if (playerActionChoice > BattleAction.BattleOption.values().length-1) {
                playerActionChoice = BattleAction.BattleOption.COMMON.getOrder();
            }
        } else if (KeyUtil.isEnter(key)) {
            if (getBattleUIScreen().playerIndex >= getBattleUIScreen().playerBattleList.size()) {
                return;
            }
            doPlayerBattleOption(gameContainer);
        }
    }

    /**
     * 处理玩家选中的战斗菜单
     *
     *  @see BattleAction.BattleOption
     */
    private void doPlayerBattleOption(GameContainer gameContainer) {
        logger.debug("playerActionChoice: " + playerActionChoice);

        if (playerActionChoice == BattleAction.BattleOption.COMMON.getOrder()) {  // 普攻
            doPlayerBattleOptionWithCommon(gameContainer);
        } else if (playerActionChoice == BattleAction.BattleOption.SKILL.getOrder()) {  // 技能
            doPlayerBattleOptionWithSkill(gameContainer);
        } else if (BattleAction.BattleOption.GOODS.getOrder() == playerActionChoice) {
            throw new UnFinishException();
        } else if (BattleAction.BattleOption.STATE.getOrder() == playerActionChoice) {
            final BattleStateScreen stateScreen = new BattleStateScreen(this, true, getBattleUIScreen().playerIndex);
            getBattleUIScreen().getParentScreen().push(stateScreen);
        } else if (BattleAction.BattleOption.ESCAPE.getOrder() == playerActionChoice) {
            throw new UnFinishException();
        } else {
            throw new RuntimeException("unknown playerActionChoice: " + playerActionChoice);
        }
    }

    /**
     * 战斗菜单-普通攻击
     *
     * 普通攻击的处理情况如下：
     *   当前角色的武器是单体攻击还是群体攻击 (当单体时需要选择要攻击哪一个敌方，群体时则不需要)
     *   存活的敌方数量 (多个时需要让玩家选择攻击哪一个敌方)
     * @see BattleAction.BattleOption#COMMON
     */
    private void doPlayerBattleOptionWithCommon(GameContainer gameContainer) {
        PlayerCharacter activeHero = getBattleUIScreen().getActivePlayer();
        //
        int range = activeHero.getEquipables().getWeapon().getEquip().getGoods().getRange();
        logger.debug("range: " + range);
        if (range == GoodsMetaData.RANGE_SINGLE) { // 单攻
            int enemySize = battleUIScreen.enemyAliveCount();
            if (enemySize == 1) { // 只有一个敌人
                getBattleUIScreen().battleActionList.add(new BattleAction(BattleAction.FROM_PLAYER,
                        getBattleUIScreen().playerIndex,
                        0,
                        playerActionChoice, -1, 0));
                if (getBattleUIScreen().playerIndex == getBattleUIScreen().playerBattleList.size() - 1) {
                    generateMonsterAttackAction();
                }
                getBattleUIScreen().nextPlayer(); // next hero
            } else { // 多于一个敌人
                MonsterSelectScreen chooseMonsterScreen = new MonsterSelectScreen(battleUIScreen, this, -1);
                getBattleUIScreen().getParentScreen().push(chooseMonsterScreen);
            }
        } else if (range == GoodsMetaData.RANGE_ALL) { // 群攻
            // 添加全体攻击效果
            getBattleUIScreen().battleActionList.add(new BattleAction(BattleAction.FROM_PLAYER,
                    getBattleUIScreen().playerIndex,
                    -1,
                    playerActionChoice, 0, 0));
            if (getBattleUIScreen().playerIndex == getBattleUIScreen().playerBattleList.size() - 1) {
                generateMonsterAttackAction();
            }
            getBattleUIScreen().nextPlayer(); // next hero
        } else {
            throw new RuntimeException("unknown range: " + range);
        }
    }

    /**
     * 战斗菜单-技能攻击
     * @see BattleAction.BattleOption#SKILL
     */
    private void doPlayerBattleOptionWithSkill(GameContainer gameContainer) {
        PlayerCharacter activeHero = getBattleUIScreen().getActivePlayer();
        String skillIds = activeHero.getRoleMetaData().getSkillIds();
        if (StringUtils.isEmpty(skillIds)) {
            getBattleUIScreen().getParentScreen().push(MessageBoxScreen.of("未习得任何技能，不能施放技能"));
            return;
        }
        int mp = activeHero.getRoleMetaData().getMp();
        if (mp <= 0) {
            getBattleUIScreen().getParentScreen().push(MessageBoxScreen.of("mp为0，不能施放技能"));
            return;
        }

        final BaseScreen bs = new SkillSelectScreen(gameContainer, battleUIScreen, this);
        getBattleUIScreen().getParentScreen().push(bs);
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

            BattleAction battleAction = new BattleAction(BattleAction.FROM_ENEMY,
                    i,
                    GameConstant.random.nextInt(getBattleUIScreen().playerBattleList.size()) ,
                    // TODO 暂先随机使用普攻和技能
                    BattleAction.BattleOption.COMMON.getOrder(), skillId, 0);
            getBattleUIScreen().battleActionList.add(battleAction);
        }
    }


    @Override
    public boolean isPopup() {
        return true;
    }
}
