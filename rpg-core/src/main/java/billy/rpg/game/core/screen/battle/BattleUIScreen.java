package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.character.MonsterCharacter;
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
    protected java.util.List<MonsterCharacter> monsterBattleList;
    protected java.util.List<HeroCharacter> heroBattleList;
    private java.util.List<Image> monsterImages;
    protected int heroIndex; // 活动玩家
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
    public int ourCount() {
        return heroBattleList.size();
    }
    /**
     * 敌方数量
     */
    public int enemyCount() {
        return monsterBattleList.size();
    }

    /**
     * 我方存活数量
     */
    public int ourAliveCount() {
        return (int)heroBattleList.stream().filter(e -> !e.isDied()).count();
    }
    /**
     * 敌方存活数量
     */
    public int enemyAliveCount() {
        return (int)monsterBattleList.stream().filter(e -> !e.isDied()).count();
    }

    public BattleUIScreen(GameContainer gameContainer, final int[] metMonsterIds, BattleScreen battleScreen, List<HeroCharacter> heroBattleList) {
        logger.debug("met monsters with["+ ArrayUtils.toString(metMonsterIds)+"]");
        parentScreen = battleScreen;
        this.heroBattleList = heroBattleList;

        monsterImages = new ArrayList<>();
        for (int metMonsterId : metMonsterIds) {
            Image image = gameContainer.getMonsterRoleOf(metMonsterId).getImage();
            monsterImages.add(image);
        }

        monsterBattleList = new ArrayList<>();
        int tempMoney = 0;
        int tempExp = 0;
        for (int i = 0; i < metMonsterIds.length; i++) {
            RoleMetaData roleMetaData = gameContainer.getMonsterRoleOf(metMonsterIds[i]);
            Image image = roleMetaData.getImage();
            MonsterCharacter monsterCharacter = new MonsterCharacter(gameContainer);
            monsterCharacter.setLeft(getLeftInWindow(i));
            monsterCharacter.setTop(100);
            monsterCharacter.setWidth(image.getWidth(null));
            monsterCharacter.setHeight(image.getHeight(null));
            monsterCharacter.setRoleMetaData(roleMetaData);
            tempExp += roleMetaData.getExp();
            tempMoney += roleMetaData.getMoney();

            monsterBattleList.add(monsterCharacter);
        }
        this.exp = tempExp;
        this.money = tempMoney;
    }

    /**
     *
     * @param index 本场妖怪的当前索引数
     */
    private int getLeftInWindow(int index) {
        int n = GameConstant.GAME_WIDTH - 100 * (monsterImages.size()-1);
        for (int i = 0; i < monsterImages.size(); i++) {
            n -= monsterImages.get(i).getWidth(null);
        }
        n/=2; // 此时 n是第一个妖怪的x坐标 ？？

        for (int i = 1; i <= index; i++) {
            n += monsterImages.get(i-1).getWidth(null) + 100;
        }

        return n;
    }

    /**
     * get current active hero
     */
    public HeroCharacter getActiveHero() {
        return heroBattleList.get(getActiveHeroIndex());
    }
    public int getActiveHeroIndex() {
        return heroIndex;
    }
    public void nextHero() {
        if (++heroIndex >= heroBattleList.size()) {
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
        for (int i = 0; i < heroBattleList.size(); i++) {
            HeroCharacter heroBattle = heroBattleList.get(i);
            // 将当前活动玩家高亮出来
            if (i == heroIndex) {
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
        for (MonsterCharacter monsterBattle : monsterBattleList) {
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


    public void markHeroAsAttacker(boolean fromHero) {
        this.fromHero = fromHero;
    }
    public void nextRound() {
        this.round++;
    }

    public void roundEnd() {
        this.fighting = false;
        this.getParentScreen().pop();
        this.heroIndex = 0; // 将当前活动的heroIndex置为首个
        this.actionList.clear(); // 清空播放动画
        this.nextRound();
    }
}
