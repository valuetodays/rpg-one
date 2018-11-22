package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.MessageBoxScreen;
import billy.rpg.game.core.util.KeyUtil;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *  普攻
    技能
    物品
    逃跑
 的选项
 * @author liulei@bshf360.com
 * @since 2017-07-17 18:22
 */
public class BattleOptionScreen extends BaseScreen {

    int heroActionChoice = BattleAction.BattleOption.COMMON.getOrderNum();
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
            g.drawString(battleOption.getDesc(), 50, 350 + battleOption.getOrderNum() * 25);
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
            if (getBattleUIScreen().heroIndex > 0) {
                getBattleUIScreen().heroIndex--;
                if (getBattleUIScreen().heroIndex < 0) {
                    getBattleUIScreen().heroIndex = 0;
                }
                getBattleUIScreen().actionList.remove(getBattleUIScreen().heroIndex);
            }
        } else if (KeyUtil.isUp(key)) {
            heroActionChoice--;
            if (heroActionChoice < 0) {
                heroActionChoice = 3;
            }
        } else if (KeyUtil.isDown(key)) {
            heroActionChoice++;
            if (heroActionChoice > 3) {
                heroActionChoice = 0;
            }
        } else if (KeyUtil.isEnter(key)) {
            if (getBattleUIScreen().heroIndex >= getBattleUIScreen().heroBattleList.size()) {
                return;
            }
            logger.info("heroActionChoice: " + heroActionChoice);
            if (heroActionChoice == BattleAction.BattleOption.COMMON.getOrderNum()) {  // 普攻
                HeroCharacter activeHero = getBattleUIScreen().getActiveHero();
                // TODO 添加 effectType = SINGLE / ALL 来判定全体攻击/单体攻击
                int type = activeHero.getEquipables().getWeapon().getEquip().getGoods().getType();
                logger.debug("type: " + type);
                // TODO 修改此值即可切换单攻(2) / 群攻(其它)
                if (type == 2) {
                    MonsterSelectScreen chooseMonsterScreen = new MonsterSelectScreen(battleUIScreen, this, -1);
                    getBattleUIScreen().getParentScreen().push(chooseMonsterScreen);
                } else {
                    // 添加全体攻击效果
                    getBattleUIScreen().actionList.add(new BattleAction(BattleAction.FROM_HERO,
                            getBattleUIScreen().heroIndex,
                            -1,
                            heroActionChoice, 0, 0));
                    // 只有一个玩家角色
                    if (getBattleUIScreen().heroIndex == getBattleUIScreen().heroBattleList.size() - 1) {
//                        generateMonsterAttackAction()// TODO 生成
                    } else {
                    }
                    getBattleUIScreen().nextHero(); // next hero
                }
            } else if (heroActionChoice == BattleAction.BattleOption.SKILL.getOrderNum()) {  // 技能
                HeroCharacter activeHero = getBattleUIScreen().getActiveHero();
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
            }

        }
    }


    @Override
    public boolean isPopup() {
        return true;
    }
}
