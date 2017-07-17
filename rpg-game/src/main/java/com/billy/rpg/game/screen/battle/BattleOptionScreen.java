package com.billy.rpg.game.screen.battle;

import com.billy.rpg.game.GameCanvas;
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
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {

        if (KeyUtil.isEsc(key)) {

            // GameFrame.getInstance().popScreen();
        } else if (KeyUtil.isHome(key)) {
            //BaseScreen bs = new AnimationScreen(2, heroX*32, heroY*32-198/2);
            //GameFrame.getInstance().pushScreen(bs);
        } else if (KeyUtil.isUp(key)) {
            if (!getBattleUIScreen().chooseMonster) {
                getBattleUIScreen().heroActionChoice--;
                if (getBattleUIScreen().heroActionChoice < 1) {
                    getBattleUIScreen().heroActionChoice = 4;
                }
            }
        } else if (KeyUtil.isDown(key)) {
            if (!getBattleUIScreen().chooseMonster) {
                getBattleUIScreen().heroActionChoice++;
                if (getBattleUIScreen().heroActionChoice > 4) {
                    getBattleUIScreen().heroActionChoice = 1;
                }
            }
        } else if (KeyUtil.isLeft(key)) {
            if (getBattleUIScreen().chooseMonster) {
                getBattleUIScreen().monsterIndex--;
                if (getBattleUIScreen().monsterIndex < 0) {
                    getBattleUIScreen().monsterIndex = getBattleUIScreen().monsterBattleList.size()-1;
                }
            }
        } else if (KeyUtil.isRight(key)) {
            if (getBattleUIScreen().chooseMonster) {
                getBattleUIScreen().monsterIndex++;
                if (getBattleUIScreen().monsterIndex > getBattleUIScreen().monsterBattleList.size()-1) {
                    getBattleUIScreen().monsterIndex = 0;
                }
            }
        } else if (KeyUtil.isEnter(key)) {
            /*if (chooseMonster) { // fix 两次回车会导致上一次的攻击动画还未玩毕就开始新的攻击动画 解决方案在com.billy.rpg.game.screen.BattleScreen
                // .onKeyUp()
                checkWinOrLose();
                MonsterBattle chosenMonsterBattle = monsterBattleList.get(monsterIndex);
                AnimationScreen bs = new AnimationScreen(2, chosenMonsterBattle.getLeft()-chosenMonsterBattle.getWidth()/2,
                        chosenMonsterBattle.getTop(), parentScreen);
//                GameFrame.getInstance().pushScreen(bs);
                parentScreen.push(new BattleActionScreen(heroBattleList.get(heroIndex), monsterBattleList.get(monsterIndex), bs, new AttackAnimationFinishedListener(){
                    @Override
                    public void onFinished() {
                        doFight();
                    }

                }));
//                parentScreen.push(bs);
                //CoreUtil.sleep(1000);
                DialogScreen dialogScreen = new DialogScreen("sixsixsix，使用选项" + heroActionChoice + "对第" + monsterIndex +
                        "只妖怪，打掉了1000血，");
                // doFight();
                //GameFrame.getInstance().pushScreen(dialogScreen);
            } else {
                switch (heroActionChoice) {
                    case 1: // 普攻
                        chooseMonster = true;
                        //DialogScreen dialogScreen = new DialogScreen("`y`妖怪`/y`看打。");
                        //GameFrame.getInstance().pushScreen(dialogScreen);
                        break;
                    case 2:
                        chooseMonster = true;
                        LOG.debug("暂没有技能");
                        break;
                    case 3:
                        LOG.debug("暂没有物品");
                        break;
                    case 4:
                        LOG.debug("众妖怪：菜鸡别跑！");
                        // TODO 金币减少100*妖怪数量。
                        GameFrame.getInstance().popScreen();
                        break;
                    default:
                        LOG.debug("cannot be here.");
                        break;
                }
            }*/
        }
    }


    @Override
    public boolean isPopup() {
        return true;
    }
}
