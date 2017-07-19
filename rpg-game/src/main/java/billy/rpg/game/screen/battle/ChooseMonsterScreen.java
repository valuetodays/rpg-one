package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.battle.MonsterBattle;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;
import org.apache.commons.collections.CollectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 选择普攻或技能对妖怪进行攻击时的选项及攻击
 */
public class ChooseMonsterScreen extends BaseScreen {
    private static int arrowY = 280; // 怪物指示器的y坐标

    private BattleUIScreen battleUIScreen;
    private int monsterIndex;  // 活动妖怪，当攻击妖怪时要显示
    private BattleOptionScreen battleOptionScreen;

    private java.util.List<String> msg;


    public ChooseMonsterScreen(BattleUIScreen battleUIScreen, BattleOptionScreen battleOptionScreen) {
        this.battleUIScreen = battleUIScreen;
        this.battleOptionScreen = battleOptionScreen;
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
        Image gameArrowUp = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowUp();
        MonsterBattle monsterBattleArrowTo = getBattleUIScreen().monsterBattleList.get(monsterIndex);
        g.drawImage(gameArrowUp,
                monsterBattleArrowTo.getLeft() + monsterBattleArrowTo.getWidth() / 2 - gameArrowUp.getWidth(null) / 2,
                arrowY, null);
        g.drawString(monsterBattleArrowTo.getRoleMetaData().getName(),
                monsterBattleArrowTo.getLeft() +
                        monsterBattleArrowTo.getWidth() / 2 - gameArrowUp.getWidth(null) / 2,
                monsterBattleArrowTo.getTop() - 50);

        // 显示战斗信息
        g.setColor(Color.magenta);
        if (CollectionUtils.isNotEmpty(msg)) {
            int size = msg.size();
            int startIndex = 0;
            int endIndex = size;
            if (size > 8) {
                startIndex = size - 8;
            }

            for (int i = startIndex; i < endIndex; i++) {
                g.drawString(msg.get(i), 300, 340 + (i - startIndex) * 18);
            }
        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        LOG.debug("who?");
        if (KeyUtil.isEsc(key)) {
            getBattleUIScreen().getParentScreen().pop();
        } else if (KeyUtil.isEnter(key)) {
            getBattleUIScreen().actionList.add(new BattleAction(getBattleUIScreen().heroIndex, monsterIndex,
                    battleOptionScreen.heroActionChoice, 0, 0));
            getBattleUIScreen().getParentScreen().pop();
            getBattleUIScreen().heroIndex++;
            battleOptionScreen.heroActionChoice = 1;
            if (getBattleUIScreen().heroIndex < getBattleUIScreen().heroBattleList.size()) {
//                BattleOptionScreen battleOptionScreen = new BattleOptionScreen(battleUIScreen);
//                getBattleUIScreen().getParentScreen().push(battleOptionScreen);
            } else {
                getBattleUIScreen().startAttack();
            }

            /*
            MonsterBattle chosenMonsterBattle = getBattleUIScreen().monsterBattleList.get(monsterIndex);
            AnimationScreen bs = new AnimationScreen(2, chosenMonsterBattle.getLeft()-chosenMonsterBattle.getWidth()/2,
                    chosenMonsterBattle.getTop(), getBattleUIScreen().getParentScreen());
            getBattleUIScreen().getParentScreen().push(new BattleActionScreen(getBattleUIScreen().heroBattleList.get(heroIndex), getBattleUIScreen().monsterBattleList.get(monsterIndex), bs, new AttackAnimationFinishedListener() {
                @Override
                public void onFinished() {
                    doFight();
                }
            }));
            DialogScreen dialogScreen = new DialogScreen("sixsixsix，使用选项" + heroActionChoice + "对第" + monsterIndex +
                    "只妖怪，打掉了1000血，");*/
            //GameFrame.getInstance().pushScreen(dialogScreen);
        } else if (KeyUtil.isLeft(key)) {
            monsterIndex--;
            if (monsterIndex < 0) {
                monsterIndex = getBattleUIScreen().monsterBattleList.size()-1;
            }
        } else if (KeyUtil.isRight(key)) {
            monsterIndex++;
            if (monsterIndex > getBattleUIScreen().monsterBattleList.size()-1) {
                monsterIndex = 0;
            }
        }

    }

    @Override
    public boolean isPopup() {
        return true;
    }



    private void appendMsg(String text) {
        if (msg == null) {
            msg = new ArrayList<>();
        }

        while (text.length() > 18) {
            msg.add(text.substring(0, 18));
            text = text.substring(18);
        }

        msg.add(text);
    }


}
