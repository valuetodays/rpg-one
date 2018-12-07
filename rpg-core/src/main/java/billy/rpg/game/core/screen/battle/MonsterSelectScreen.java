package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.MonsterCharacter;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.BattleImageItem;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.util.KeyUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 选择普攻或技能对妖怪进行攻击时的选项及攻击
 */
public class MonsterSelectScreen extends BaseScreen {
    private static int arrowY = 280; // 怪物指示器的y坐标

    private BattleUIScreen battleUIScreen;
    private BattleOptionScreen battleOptionScreen;
    private final int skillId;

    private int monsterIndex;  // 活动妖怪，当攻击妖怪时要显示
    private java.util.List<String> msg;

    /**
     *
     * @param battleUIScreen battleUI
     * @param battleOptionScreen battleOption
     * @param skillId skillId, skill id
     */
    public MonsterSelectScreen(BattleUIScreen battleUIScreen, BattleOptionScreen battleOptionScreen, int skillId) {
        this.battleUIScreen = battleUIScreen;
        this.battleOptionScreen = battleOptionScreen;
        this.skillId = skillId;
        java.util.List<MonsterCharacter> monsterBattleList = getBattleUIScreen().monsterBattleList;
        for (int i = 0; i < monsterBattleList.size(); i++) {
            Fightable fightable = monsterBattleList.get(i);
            if (!fightable.isDied()) {
                monsterIndex = i;
                break;
            }
        }
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

        String battleImagePath = gameContainer.getActiveScriptItem().getBattleImagePath();
        Image battleImage = gameContainer.getBattleImageItem().getBattleImage(battleImagePath);
        if (battleImage == null) {
            battleImage = gameContainer.getBattleImageItem().getBattleImage(BattleImageItem.DEFAULT_BATTLE);
        }
        g.drawImage(battleImage, 0, 0, null);

        getBattleUIScreen().drawMonster(gameContainer, g);

        Image gameArrowUp = gameContainer.getGameAboutItem().getGameArrowUp();
        MonsterCharacter monsterBattleArrowTo = getBattleUIScreen().monsterBattleList.get(monsterIndex);
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
            getBattleUIScreen().actionList.add(new BattleAction(BattleAction.FROM_HERO,
                    getBattleUIScreen().heroIndex,
                    monsterIndex,
                    battleOptionScreen.heroActionChoice, skillId, 0));
            if (skillId != -1) { // 将技能选择屏幕清除掉
                getBattleUIScreen().getParentScreen().pop();
            }
            getBattleUIScreen().getParentScreen().pop(); // TODO 清除什么？
            getBattleUIScreen().heroIndex++;
            battleOptionScreen.heroActionChoice = BattleAction.BattleOption.COMMON.getOrder(); // 重置成普攻
            if (getBattleUIScreen().heroIndex < getBattleUIScreen().heroBattleList.size()) {

            } else {
                battleOptionScreen.generateMonsterAttackAction();
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
