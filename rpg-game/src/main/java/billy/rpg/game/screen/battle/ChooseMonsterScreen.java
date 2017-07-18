package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.game.character.battle.MonsterBattle;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.AnimationScreen;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.screen.DialogScreen;
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
    private final int heroActionChoice;
    private final int heroIndex;

    private java.util.List<String> msg;


    public ChooseMonsterScreen(BattleUIScreen battleUIScreen, int heroActionChoice, int heroIndex) {
        this.battleUIScreen = battleUIScreen;
        this.heroActionChoice = heroActionChoice;
        this.heroIndex = heroIndex;
    }


    public BattleUIScreen getBattleUIScreen() {
        return battleUIScreen;
    }


    @Override
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        checkWinOrLose();

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
                    "只妖怪，打掉了1000血，");
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

    private void doFight() {
        getBattleUIScreen().getParentScreen().pop();
        switch (heroActionChoice) {
            case 1: // 普攻
                doAttack();
                break;
            case 2: // 技能
                LOG.debug("暂没有技能可供使用");
                break;
            case 3: // 物品
                LOG.debug("暂没有物品可供使用");
                break;
            default:
                LOG.debug("哇噻！你是怎么找到这儿的？");
                break;
        }
    }


    private void doAttack() {
        HeroBattle heroBattle = getBattleUIScreen().heroBattleList.get(heroIndex);
        MonsterBattle monsterBattle = getBattleUIScreen().monsterBattleList.get(monsterIndex);
        int attack = heroBattle.getRoleMetaData().getAttack();
        int defend = monsterBattle.getRoleMetaData().getDefend();

        float dmgF = 1.0f * (attack*1) / (defend/100+1);
        dmgF += GameConstant.random.nextInt((int)(Math.floor(1.0f * heroBattle.getRoleMetaData().getSpeed() *
                heroBattle.getRoleMetaData().getHp() / heroBattle.getRoleMetaData().getMaxHp())));
        int dmg = (int)dmgF;
        int hp = monsterBattle.getRoleMetaData().getHp();
        hp -= dmg;
        monsterBattle.getRoleMetaData().setHp(hp);
        String msgText = "玩家"+ heroIndex + "对妖怪"+monsterIndex+"造成了"+dmg + "伤害";
        if (hp <= 0) {
            msgText += "，妖怪的小身板扛不住就挂了";
            getBattleUIScreen().monsterBattleList.remove(monsterIndex);
            checkWinOrLose();
            monsterIndex = 0;
        }
        msgText += "。";
        LOG.debug(msgText);
        appendMsg(msgText);
    }




    private void checkWinOrLose() {
        // TODO 应该先判断玩家是不是挂了
        if (CollectionUtils.isEmpty(getBattleUIScreen().monsterBattleList)) {
            LOG.debug("victory!!! show victory ui");
            BattleSuccessScreen success = new BattleSuccessScreen(
                    getBattleUIScreen().heroBattleList,
                    getBattleUIScreen().money,
                    getBattleUIScreen().exp);
            getBattleUIScreen().getParentScreen().push(success);
        }

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
