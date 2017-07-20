package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;

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
        g.drawString("普攻", 50, 350);
        g.drawString("技能", 50, 375);
        g.drawString("物品", 50, 400);
        g.drawString("逃跑", 50, 425);

        // 显示用户选项
        Image gameArrowRight = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowRight();
        g.drawImage(gameArrowRight, 30, (heroActionChoice -1) * 25 + 333, null);

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    private boolean chooseMonster;   // TODO 该变量有存在的必要？
    protected int heroActionChoice = 1;
    @Override
    public void onKeyUp(int key) {
        LOG.debug("who?");
        if (KeyUtil.isEsc(key)) {
            if (getBattleUIScreen().heroIndex > 0) {
                getBattleUIScreen().heroIndex--;
                if (getBattleUIScreen().heroIndex <= 0) {
                    getBattleUIScreen().heroIndex = 0;
                }
                getBattleUIScreen().actionList.remove(getBattleUIScreen().heroIndex);
            }

        } else if (KeyUtil.isUp(key)) {
            if (!chooseMonster) {
                heroActionChoice--;
                if (heroActionChoice < 1) {
                    heroActionChoice = 4;
                }
            }
        } else if (KeyUtil.isDown(key)) {
            if (!chooseMonster) {
                heroActionChoice++;
                if (heroActionChoice > 4) {
                    heroActionChoice = 1;
                }
            }
        } else if (KeyUtil.isEnter(key)) {
            if (getBattleUIScreen().heroIndex >= getBattleUIScreen().heroBattleList.size()) {

            } else {
                ChooseMonsterScreen chooseMonsterScreen = new ChooseMonsterScreen(battleUIScreen, this);
                getBattleUIScreen().getParentScreen().push(chooseMonsterScreen);
            }

        }
    }


    @Override
    public boolean isPopup() {
        return true;
    }
}
