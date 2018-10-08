package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.MonsterCharacter;
import billy.rpg.game.character.fightable.Fightable;
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
            Fightable fightable = monsterBattleList.get(i).getFightable();
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

        Image battleImage = GameFrame.getInstance().getGameContainer().getBattleImageItem().getBattleImage("001-Grassland01.jpg");
        // TODO 战斗背景图应从*.map或*.s中加载
        // TODO 先画出黑色背景，因为战斗背景图不是640*480的 (640*320)
        g.drawImage(battleImage, 0, 0, battleImage.getWidth(null), battleImage.getHeight(null), null);

        getBattleUIScreen().drawMonster(g);

        Image gameArrowUp = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowUp();
        MonsterCharacter monsterCharacter = getBattleUIScreen().monsterBattleList.get(monsterIndex);
        Fightable monsterBattleArrowTo = monsterCharacter.getFightable();
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
                    battleOptionScreen.heroActionChoice, skillId, 0));
            if (skillId != -1) { // 将技能选择屏幕清除掉
                getBattleUIScreen().getParentScreen().pop();
            }
            getBattleUIScreen().getParentScreen().pop(); // TODO 清除什么？
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
                    && !getBattleUIScreen().monsterBattleList.get(nextMonsterIndex).getFightable().isDied()) {
                monsterIndex = nextMonsterIndex;
            }
        } else if (KeyUtil.isRight(key)) {
            int nextMonsterIndex = monsterIndex + 1;
            if (nextMonsterIndex < getBattleUIScreen().monsterBattleList.size()
                && !getBattleUIScreen().monsterBattleList.get(nextMonsterIndex).getFightable().isDied()) {
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
            int skillId = 0;
            if (BattleAction.ACTION_SKILL == (actionAttack+1)) {
                skillId = 2;
            }

            BattleAction battleAction = new BattleAction(BattleAction.FROM_MONSTER,
                    i,
                    GameConstant.random.nextInt(getBattleUIScreen().heroBattleList.size()) ,
                    // TODO 暂先随机使用普攻和技能
                    1, skillId, 0);
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
