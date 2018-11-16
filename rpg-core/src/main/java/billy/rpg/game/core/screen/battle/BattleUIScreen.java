package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.GameCanvas;
import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.character.MonsterCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
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

    public BattleScreen getParentScreen() {
        return parentScreen;
    }

    public BattleUIScreen(GameContainer gameContainer, final int[] metMonsterIds, BattleScreen battleScreen, List<HeroCharacter> heroBattleList) {
        logger.debug("met " + metMonsterIds.length + " monsters with["+ ArrayUtils.toString(metMonsterIds)+"]");
        parentScreen = battleScreen;
        this.heroBattleList = heroBattleList;

        monsterImages = new ArrayList<>();
        for (int i = 0; i < metMonsterIds.length; i++) {
            Image image = gameContainer.getMonsterRoleOf(metMonsterIds[i]).getImage();
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

    @Override
    public void update(GameContainer gameContainer, long delta) {

    }

    @Override
    public void draw(GameContainer gameContainer, GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        g.setColor(Color.black);
//        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());

        drawMonster(gameContainer, g);

        drawHero(g);

        g.dispose();
        gameCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    private void drawHero(Graphics g) {
        // 将当前活动玩家高亮出来
        for (int i = 0; i < heroBattleList.size(); i++) {
            HeroCharacter heroBattle = heroBattleList.get(i);
            if (i == heroIndex) {
                RoleMetaData roleMetaData = heroBattle.getRoleMetaData();
                g.setColor(Color.yellow);
                g.fillRect(heroBattle.getLeft()-5, heroBattle.getTop(), roleMetaData.getImage().getWidth() + 10,
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
            int battleImageWidth = battleImage.getWidth(null);
            int battleImageHeight = battleImage.getHeight(null);
            int acctackFrame = heroBattle.getAcctackFrame();
            // 站立
            if (acctackFrame == 0) {
                g.drawImage(battleImage,
                    heroBattle.getLeft() + 20, heroBattle.getTop() + 30,
                    heroBattle.getLeft() + 20 + battleImageWidth,
                    heroBattle.getTop()  + 30 + battleImageHeight /12,
                    // 站立是第一帧
                    0, 0,
                        battleImageWidth, battleImageHeight /12,
                    null);
            } else if (acctackFrame > 0 && acctackFrame < 12) { // 普攻移动
                int frameNum = acctackFrame / 4 + 1;
                g.drawImage(battleImage,
                        heroBattle.getLeft() + 20, heroBattle.getTop() + 30,
                        heroBattle.getLeft() + 20 + battleImageWidth,
                        heroBattle.getTop()  + 30 + battleImageHeight /12,
                        // 站立是第一帧
                        0, frameNum * battleImageHeight/12,
                        battleImageWidth, frameNum * battleImageHeight/12 + battleImageHeight /12,
                        null);
            }
        }
    }

    public void drawMonster(GameContainer gameContainer, Graphics g) {
        Image battleImage = gameContainer.getBattleImageItem().getBattleImage
                ("001-Grassland01.jpg");
        // TODO 战斗背景图应从*.map或*.s中加载
        // TODO 先画出黑色背景，因为战斗背景图不是640*480的 (640*320)
        g.drawImage(battleImage, 0, 0, battleImage.getWidth(null), battleImage.getHeight(null), null);

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




}
