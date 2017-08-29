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
        java.util.List<MonsterBattle> monsterBattleList = getBattleUIScreen().monsterBattleList;
        for (int i = 0; i < monsterBattleList.size(); i++) {
            MonsterBattle monsterBattle = monsterBattleList.get(i);
            if (!monsterBattle.isDied()) {
                monsterIndex = i;
                break;
            }
        }
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
        if (!monsterBattleArrowTo.isDied()) {
            g.drawImage(gameArrowUp,
                    monsterBattleArrowTo.getLeft() + monsterBattleArrowTo.getWidth() / 2 - gameArrowUp.getWidth(null) / 2,
                    arrowY, null);
            g.drawString(monsterBattleArrowTo.getRoleMetaData().getName(),
                    monsterBattleArrowTo.getLeft() +
                            monsterBattleArrowTo.getWidth() / 2 - gameArrowUp.getWidth(null) / 2,
                    monsterBattleArrowTo.getTop() - 50);
        }

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
        if (KeyUtil.isEsc(key)) {
            getBattleUIScreen().getParentScreen().pop();
        } else if (KeyUtil.isEnter(key)) {
            getBattleUIScreen().actionList.add(new BattleAction(BattleAction.FROM_HERO,
                    getBattleUIScreen().heroIndex,
                    monsterIndex,
                    battleOptionScreen.heroActionChoice, 0, 0));
            getBattleUIScreen().getParentScreen().pop();
            getBattleUIScreen().heroIndex++;
            battleOptionScreen.heroActionChoice = BattleAction.ACTION_ATTACK; // 重置成普攻
            if (getBattleUIScreen().heroIndex < getBattleUIScreen().heroBattleList.size()) {

            } else {
                generateMonsterAttackAction();
                getBattleUIScreen().startAttack();
            }

        } else if (KeyUtil.isLeft(key)) {
            int nextMonsterIndex = monsterIndex - 1;
            if (nextMonsterIndex >= 0
                    && !getBattleUIScreen().monsterBattleList.get(nextMonsterIndex).isDied()) {
                monsterIndex = nextMonsterIndex;
            }
        } else if (KeyUtil.isRight(key)) {
            int nextMonsterIndex = monsterIndex + 1;
            if (nextMonsterIndex < getBattleUIScreen().monsterBattleList.size()
                && !getBattleUIScreen().monsterBattleList.get(nextMonsterIndex).isDied()) {
                monsterIndex = nextMonsterIndex;
            }
        }

    }

    /**
     * 生成怪物的攻击方式
     */
    private void generateMonsterAttackAction() {
        for (int i = 0; i < getBattleUIScreen().monsterBattleList.size(); i++) {
            // TODO 暂先随机使用普攻和技能
            int actionAttack = GameConstant.random.nextInt(2);
            BattleAction battleAction = new BattleAction(BattleAction.FROM_MONSTER,
                    i,
                    GameConstant.random.nextInt(getBattleUIScreen().heroBattleList.size()) ,
                    // TODO 暂先随机使用普攻和技能
                    actionAttack + 1, 0, 0);
            getBattleUIScreen().actionList.add(battleAction);
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
