package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.game.character.battle.MonsterBattle;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.scriptParser.item.ScriptItem;
import billy.rpg.resource.role.RoleMetaData;
import org.apache.commons.lang.ArrayUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author liulei@bshf360.com
 * @since 2017-06-08 15:29
 */
public class BattleUIScreen extends BaseScreen {
    private BattleScreen parentScreen; // the basescreen contains this
    protected java.util.List<HeroBattle> heroBattleList;
    protected java.util.List<MonsterBattle> monsterBattleList;
    private java.util.List<Image> monsterImages;
    protected int monsterIndex;
    protected int heroIndex; // 活动玩家

    protected final int money;
    protected final int exp;

    public int getMonsterIndex() {
        return monsterIndex;
    }

    public BattleScreen getParentScreen() {
        return parentScreen;
    }

    public BattleUIScreen(final int[] metMonsterIds, BattleScreen battleScreen) {
        LOG.debug("met " + metMonsterIds.length + " monsters with["+ ArrayUtils.toString(metMonsterIds)+"]");
        parentScreen = battleScreen;

        Map<Integer, RoleMetaData> monsterMap = GameFrame.getInstance().getGameContainer().getMonsterDataLoader()
                .getMonsterMetaData().getMonsterMap();

        monsterImages = new ArrayList<>();
        for (int i = 0; i < metMonsterIds.length; i++) {
            Image image = monsterMap.get(metMonsterIds[i]).getImage();
            monsterImages.add(image);
        }

        monsterBattleList = new ArrayList<>();
        int tempMoney = 0;
        int tempExp = 0;
        for (int i = 0; i < metMonsterIds.length; i++) {
            RoleMetaData roleMetaData = monsterMap.get(metMonsterIds[i]);
            Image image = roleMetaData.getImage();
            MonsterBattle mb = new MonsterBattle();
            mb.setImage(image);
            mb.setLeft(getLeftInWindow(i));
            mb.setTop(100);
            mb.setWidth(image.getWidth(null));
            mb.setHeight(image.getHeight(null));
            mb.setMonster(roleMetaData);
            tempMoney += roleMetaData.getExp(); // TODO 没有金币呢。。。。
            tempExp += roleMetaData.getExp();
            monsterBattleList.add(mb);
        }
        this.money = tempMoney;
        this.exp = tempExp;

        heroBattleList = new ArrayList<>();
        java.util.List<Integer> heroIds = ScriptItem.getHeroIds();
        for (int i = 0; i < heroIds.size(); i++) {
            HeroBattle e = new HeroBattle();
            e.setLeft(8*32 + i*32);
            e.setTop(10*32);
            e.setWidth(32);
            e.setHeight(32);
            e.setMonster(monsterMap.get(51)); // TODO 没有数据，先使用着妖怪的
            e.setImage(monsterMap.get(51).getImage());

            heroBattleList.add(e);
        }
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

        final Image roleFull1 = GameFrame.getInstance().getGameContainer().getRoleItem().getRoleFull1();
        // TODO 战斗背景图应从*.map或*.s中加载
        Image battleImage = GameFrame.getInstance().getGameContainer().getBattleImageItem().getBattleImage("001-Grassland01.jpg");
        // TODO 先画出黑色背景，因为战斗背景图不是640*480的 (640*320)
        g.setColor(Color.black);
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());
        g.drawImage(battleImage, 0, 0, battleImage.getWidth(null), battleImage.getHeight(null), null);  // draw battleImage
        HeroBattle heroBattle = heroBattleList.get(heroIndex);
        g.drawImage(roleFull1, heroBattle.getLeft(), heroBattle.getTop(), null); // 面向右，打妖怪。

        for (int i = 0; i < monsterBattleList.size(); i++) {
            MonsterBattle monsterBattle = monsterBattleList.get(i);
            Image image = monsterBattle.getImage();
            int left = monsterBattle.getLeft();
            int top = monsterBattle.getTop();
            g.drawImage(image, left, top, null);
        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
    }

    @Override
    public void onKeyUp(int key) {
        LOG.debug("who?");
    }


}
