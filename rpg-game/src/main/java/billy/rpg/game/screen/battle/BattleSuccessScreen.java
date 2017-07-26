package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.resource.level.LevelData;
import billy.rpg.resource.level.LevelMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 显示升级成长数据
 *
 * 注意点是：draw()里只显示数据，操作数据是在onKeyUp()里，为什么不放在draw()里呢，因为draw()会被反复执行
 *
 *
 * @author liulei@bshf360.com
 * @since 2017-07-13 09:58
 */
public class BattleSuccessScreen extends BaseScreen {
    private int money;
    private int exp;
    private List<HeroBattle> heroBattleList;

    public BattleSuccessScreen(List<HeroBattle> heroBattleList, int money, int exp) {
        this.heroBattleList = heroBattleList;
        this.money = money;
        this.exp = exp;
        // TODO 在这里处理战斗前后的数据，并处理是否升级
        // 然后draw()方法只是显示，onKeyUp()方法负责做角色属性的修改
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

        // TODO 应该加载一个背景图
        g.setColor(Color.black);
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());
        g.setColor(Color.WHITE);
        g.drawString("Victory~~~", 20, 50);
        g.drawString("get money " + money, 20, 70);
        g.drawString("get exp " + exp, 20, 90);
        // 这里只【显示】升级所得经验及金币
        for (int i = 0; i < heroBattleList.size(); i++) {
            HeroBattle heroBattle = heroBattleList.get(i);
            int oriMoney = heroBattle.getRoleMetaData().getMoney();
            int oriExp = heroBattle.getRoleMetaData().getExp();
            int newMoney = oriMoney + money;
            int newExp = oriExp + exp;
            g.drawString("money: " + oriMoney + "  -->  " + newMoney, 10 + 100*i, 120);
            g.drawString("exp: " + oriExp + "  -->  " + newExp, 10 + 100*i, 140);
            int level = heroBattle.getRoleMetaData().getLevel();
            int levelChain = heroBattle.getRoleMetaData().getLevelChain();
            LevelMetaData levelMetaData = GameFrame.getInstance().getGameContainer().getLevelMetaDataOf(levelChain);
            if (level > levelMetaData.getMaxLevel()) {
                // TODO 满级了，不要经验了
            } else {
                LevelData levelData = levelMetaData.getLevelDataList().get(level - 1);
                final int expInLevel = levelData.getExp();
                if (newExp > expInLevel) {
                    // TODO 升级
                    LOG.debug("level up!!");
                    heroBattle.getRoleMetaData().setLevel(level + 1);
                    heroBattle.getRoleMetaData().setExp(newExp - expInLevel);
                    heroBattle.getRoleMetaData().setMaxHp(levelData.getMaxHp()); // TODO 暂不考虑吃加生命上限的药和装备加生命的情况
                    heroBattle.getRoleMetaData().setMaxMp(levelData.getMaxMp());
                } else {
                    // TODO 没升级
                }
            }

        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        for (int i = 0; i < heroBattleList.size(); i++) {
            HeroBattle heroBattle = heroBattleList.get(i);
            int oriMoney = heroBattle.getRoleMetaData().getMoney();
            int oriExp = heroBattle.getRoleMetaData().getExp();
            int newMoney = oriMoney + money;
            int newExp = oriExp + exp;
            heroBattle.getRoleMetaData().setMoney(newMoney);
            heroBattle.getRoleMetaData().setExp(newExp); // 该方法放在draw()中会导致，money和exp一直在增加。。。。。
        }
        GameFrame.getInstance().changeScreen(1);
    }
}
