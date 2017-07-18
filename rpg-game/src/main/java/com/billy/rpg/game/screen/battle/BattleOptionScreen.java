package com.billy.rpg.game.screen.battle;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.screen.BaseScreen;
import com.billy.rpg.game.util.KeyUtil;

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
    protected int heroIndex; // 活动玩家的索引，暂取0

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

    private boolean chooseMonster;
    private int heroActionChoice = 1;
    @Override
    public void onKeyUp(int key) {
        LOG.debug("who?");
        if (KeyUtil.isEsc(key)) {
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
            switch (heroActionChoice) {
                case 1: { // 普攻
                    //DialogScreen dialogScreen = new DialogScreen("`y`妖怪`/y`看打。");
                    //GameFrame.getInstance().pushScreen(dialogScreen);
                    ChooseMonsterScreen chooseMonsterScreen = new ChooseMonsterScreen(getBattleUIScreen(), heroActionChoice, heroIndex);
                    getBattleUIScreen().getParentScreen().push(chooseMonsterScreen);
                }
                break;
                case 2: { // 技能
                    LOG.debug("暂没有技能");
                }
                break;
                case 3:
                    LOG.debug("暂没有物品");
                    break;
                case 4:
                    LOG.debug("众妖怪：菜鸡别跑！");
                    // TODO 金币减少100*妖怪数量。
                    // TODO 此时要是逃跑成功，应该跳到地图界面
                    GameFrame.getInstance().changeScreen(1);
                    break;
                default:
                    LOG.debug("cannot be here.");
                    break;
            }

        }
    }


    @Override
    public boolean isPopup() {
        return true;
    }
}
