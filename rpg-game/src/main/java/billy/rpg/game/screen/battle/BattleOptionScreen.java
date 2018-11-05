package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.screen.MessageBoxScreen;
import billy.rpg.game.util.KeyUtil;
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

    public enum BattleOption {
        COMMON(0, "普攻"),
        SKILL(1, "技能"),
        GOODS(2, "物品"),
        ESCAPE(3, "逃跑");

        private int orderNum;
        private String desc;

        BattleOption(int orderNum, String desc) {
            this.orderNum = orderNum;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public int getOrderNum() {
            return orderNum;
        }
    }

    int heroActionChoice = BattleOption.COMMON.getOrderNum();
    private BattleUIScreen battleUIScreen;

    public BattleOptionScreen(BattleUIScreen battleUIScreen) {
        this.battleUIScreen = battleUIScreen;
    }

    public BattleUIScreen getBattleUIScreen() {
        return battleUIScreen;
    }

    @Override
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
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
        for (BattleOption battleOption : BattleOption.values()) {
            g.drawString(battleOption.getDesc(), 50, 350 + battleOption.getOrderNum() * 25);
        }

        // 显示用户选项
        Image gameArrowRight = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowRight();
        g.drawImage(gameArrowRight, 30, (heroActionChoice) * 25 + 333, null);

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }


    @Override
    public void onKeyUp(int key) {
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
            if (heroActionChoice == BattleOption.COMMON.getOrderNum()) {  // 普攻
                MonsterSelectScreen chooseMonsterScreen = new MonsterSelectScreen(battleUIScreen, this, -1);
                getBattleUIScreen().getParentScreen().push(chooseMonsterScreen);
            } else if (heroActionChoice == BattleOption.SKILL.getOrderNum()) {  // 技能
                HeroCharacter.HeroFightable activeHero = (HeroCharacter.HeroFightable)getBattleUIScreen().getActiveHero().getFightable();
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

                // TODO mp不足时？
                final BaseScreen bs = new SkillSelectScreen(battleUIScreen, this);
                getBattleUIScreen().getParentScreen().push(bs);
            }

        }
    }


    @Override
    public boolean isPopup() {
        return true;
    }
}
