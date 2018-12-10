package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.character.EnemyCharacter;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.BattleImageItem;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.resource.role.RoleMetaData;
import org.apache.commons.lang.ArrayUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liulei@bshf360.com
 * @since 2017-06-08 15:29
 */
public class BattleUIScreen extends BaseScreen {
    private BattleScreen parentScreen; // the basescreen contains this
    protected java.util.List<EnemyCharacter> enemyBattleList;
    protected java.util.List<PlayerCharacter> playerBattleList;
    private java.util.List<Image> enemyImages;
    protected int playerIndex; // 活动玩家
    protected final int exp;
    protected final int money;
    protected List<BattleAction> actionList = new ArrayList<>();
    protected boolean fighting;
    private boolean fromHero;
    private int round = 1;

    public BattleScreen getParentScreen() {
        return parentScreen;
    }

    /**
     * 我方数量
     */
    public int playerCount() {
        return playerBattleList.size();
    }
    /**
     * 敌方数量
     */
    public int enemyCount() {
        return enemyBattleList.size();
    }

    /**
     * 我方存活数量
     */
    public int playerAliveCount() {
        return (int) playerBattleList.stream().filter(e -> !e.isDied()).count();
    }
    /**
     * 敌方存活数量
     */
    public int enemyAliveCount() {
        return (int) enemyBattleList.stream().filter(e -> !e.isDied()).count();
    }

    public BattleUIScreen(GameContainer gameContainer, final int[] metEnemyIds, BattleScreen battleScreen, List<PlayerCharacter> heroBattleList) {
        logger.debug("met enemy with["+ ArrayUtils.toString(metEnemyIds)+"]");
        parentScreen = battleScreen;
        this.playerBattleList = heroBattleList;

        enemyImages = new ArrayList<>();
        for (int enemyId : metEnemyIds) {
            Image image = gameContainer.getMonsterRoleOf(enemyId).getImage();
            enemyImages.add(image);
        }

        enemyBattleList = new ArrayList<>();
        int tempMoney = 0;
        int tempExp = 0;
        for (int i = 0; i < metEnemyIds.length; i++) {
            RoleMetaData roleMetaData = gameContainer.getMonsterRoleOf(metEnemyIds[i]);
            Image image = roleMetaData.getImage();
            EnemyCharacter monsterCharacter = new EnemyCharacter(gameContainer);
            monsterCharacter.setLeft(getLeftInWindow(i));
            monsterCharacter.setTop(100);
            monsterCharacter.setWidth(image.getWidth(null));
            monsterCharacter.setHeight(image.getHeight(null));
            monsterCharacter.setRoleMetaData(roleMetaData);
            tempExp += roleMetaData.getExp();
            tempMoney += roleMetaData.getMoney();

            enemyBattleList.add(monsterCharacter);
        }
        this.exp = tempExp;
        this.money = tempMoney;
    }

    /**
     *
     * @param index 本场妖怪的当前索引数
     */
    private int getLeftInWindow(int index) {
        int n = GameConstant.GAME_WIDTH - 100 * (enemyImages.size()-1);
        for (int i = 0; i < enemyImages.size(); i++) {
            n -= enemyImages.get(i).getWidth(null);
        }
        n/=2; // 此时 n是第一个妖怪的x坐标 ？？

        for (int i = 1; i <= index; i++) {
            n += enemyImages.get(i-1).getWidth(null) + 100;
        }

        return n;
    }

    /**
     * get current active hero
     */
    public PlayerCharacter getActivePlayer() {
        return playerBattleList.get(getActivePlayerIndex());
    }
    public int getActivePlayerIndex() {
        return playerIndex;
    }
    public void nextPlayer() {
        if (++playerIndex >= playerBattleList.size()) {
            startAttack();
        }
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());

        String battleImagePath = gameContainer.getActiveScriptItem().getBattleImagePath();
        Image battleImage = gameContainer.getBattleImageItem().getBattleImage(battleImagePath);
        if (battleImage == null) {
            battleImage = gameContainer.getBattleImageItem().getBattleImage(BattleImageItem.DEFAULT_BATTLE);
        }
        g.drawImage(battleImage, 0, 0, null);
        g.drawString("ROUND: " + round, 450, 20);

        if (fromHero) {
            drawMonster(gameContainer, g);
            drawHero(g);
        } else {
            drawHero(g);
            drawMonster(gameContainer, g);
        }


        g.dispose();
        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    private void drawHero(Graphics g) {
        for (int i = 0; i < playerBattleList.size(); i++) {
            PlayerCharacter heroBattle = playerBattleList.get(i);
            // 将当前活动玩家高亮出来
            if (i == playerIndex) {
                RoleMetaData roleMetaData = heroBattle.getRoleMetaData();
                g.setColor(Color.yellow);
                g.fillRect(heroBattle.getLeft(), heroBattle.getTop(), roleMetaData.getImage().getWidth(),
                        roleMetaData.getImage().getHeight());
            }
            g.setColor(Color.green);
            g.drawString("" + heroBattle.getRoleMetaData().getHp() + "/" + heroBattle.getRoleMetaData().getMaxHp(),
                    heroBattle.getLeft() + heroBattle.getWidth() / 4,
                    heroBattle.getTop() - 40);
            g.drawString("" + heroBattle.getRoleMetaData().getMp() + "/" + heroBattle.getRoleMetaData().getMaxMp(),
                    heroBattle.getLeft() + heroBattle.getWidth() / 4,
                    heroBattle.getTop() - 20);

            Image battleImage = heroBattle.getBattleImage();
            g.drawImage(battleImage, heroBattle.getLeft(), heroBattle.getTop(), null);
        }
    }

    public void drawMonster(GameContainer gameContainer, Graphics g) {
        for (EnemyCharacter monsterBattle : enemyBattleList) {
            Image image = monsterBattle.getRoleMetaData().getImage();
            int left = monsterBattle.getLeft();
            int top = monsterBattle.getTop();
            if (!monsterBattle.isDied()) { // 只画活着的妖怪
                g.setColor(Color.red);
                g.drawString("" + monsterBattle.getRoleMetaData().getHp() + "/" + monsterBattle.getRoleMetaData().getMaxHp(),
                        left + monsterBattle.getWidth() / 4,
                        top + monsterBattle.getHeight() + 20);
                g.drawString("" + monsterBattle.getRoleMetaData().getMp() + "/" + monsterBattle.getRoleMetaData().getMaxMp(),
                        left + monsterBattle.getWidth() / 4,
                        top + monsterBattle.getHeight() + 40);
                g.drawImage(image, left, top, null);
            }
        }
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
    }

    public void startAttack() {
        fighting = true;
        BattleFightScreen bfs = new BattleFightScreen(this, actionList);
        getParentScreen().push(bfs);
    }

    public void markPlayerAsAttacker(boolean fromHero) {
        this.fromHero = fromHero;
    }
    public void nextRound() {
        this.round++;
    }

    public void roundEnd() {
        logger.debug(" round " + round + " elapsed");
        this.playerBattleList.forEach(Fightable::onRoundEnd);
        this.fighting = false;
        this.getParentScreen().pop();
        this.playerIndex = 0; // 将当前活动的heroIndex置为首个
        this.actionList.clear(); // 清空播放动画
        this.nextRound();
    }
}
