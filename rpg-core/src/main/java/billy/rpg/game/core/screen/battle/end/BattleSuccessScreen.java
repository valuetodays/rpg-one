package billy.rpg.game.core.screen.battle.end;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.constants.ScreenCodeEnum;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.MessageBoxScreen;
import billy.rpg.game.core.screen.battle.BattleScreen;
import billy.rpg.resource.level.LevelData;
import billy.rpg.resource.level.LevelMetaData;
import billy.rpg.resource.role.RoleMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    private BattleScreen battleScreen;
    private List<HeroCharacter> newHeroBatleList = new ArrayList<>();

    public BattleSuccessScreen(GameContainer gameContainer, BattleScreen battleScreen, int money, int exp) {
        this.battleScreen = battleScreen;
        this.money = money;
        this.exp = exp;
        List<HeroCharacter> heroBattleList = battleScreen.getHeroBattleList();
        for (int i = 0; i < heroBattleList.size(); i++) {
            HeroCharacter heroBattle = heroBattleList.get(i);
            HeroCharacter newHeroBattle = new HeroCharacter(gameContainer);
            newHeroBattle.setDied(heroBattle.isDied());
            newHeroBattle.setHeight(heroBattle.getHeight());
            newHeroBattle.setWidth(heroBattle.getWidth());
            newHeroBattle.setLeft(heroBattle.getLeft());
            newHeroBattle.setTop(heroBattle.getTop());
            newHeroBattle.setRoleMetaData(heroBattle.getRoleMetaData().clone());
            newHeroBatleList.add(newHeroBattle);
        }


        // 在这里处理战斗前后的数据，并处理是否升级
        // 然后draw()方法只是显示，onKeyUp()方法负责做角色属性的修改
        for (int i = 0; i < newHeroBatleList.size(); i++) {
            HeroCharacter heroBattle = newHeroBatleList.get(i);
            int oriExp = heroBattle.getRoleMetaData().getExp();
            int newExp = oriExp + exp;
            int level = heroBattle.getRoleMetaData().getLevel();
            int levelChain = heroBattle.getRoleMetaData().getLevelChain();
            LevelMetaData levelMetaData = gameContainer.getLevelMetaDataOf(levelChain);
            if (level >= levelMetaData.getMaxLevel()) {
                // 满级
                String msg = "满级";
                final BaseScreen bs = new MessageBoxScreen(msg);
                gameContainer.getGameFrame().pushScreen(bs);
            } else {
                LevelData levelData = levelMetaData.getLevelDataList().get(level - 1);
                final int expInLevel = levelData.getExp();
                if (newExp > expInLevel) {
                    // 升级
                    logger.debug("level up!!");
                    heroBattle.getRoleMetaData().setExp(newExp - expInLevel);
                    heroBattle.getRoleMetaData().setLevel(level + 1);
                    heroBattle.getRoleMetaData().setMaxHp(levelData.getMaxHp()); // TODO 暂不考虑吃加生命上限的药和装备加生命的情况
                    heroBattle.getRoleMetaData().setHp(heroBattle.getRoleMetaData().getMaxHp()); // 当前hp加到最大
                    heroBattle.getRoleMetaData().setMaxMp(levelData.getMaxMp());
                    heroBattle.getRoleMetaData().setMp(heroBattle.getRoleMetaData().getMaxMp()); // 当前mp加到最大
                    heroBattle.getRoleMetaData().setAttack(levelData.getAttack());
                    heroBattle.getRoleMetaData().setDefend(levelData.getDefend());
                    // heroBattle.getRoleMetaData().setSpeed(); // TODO 升级链里怎么没有速度？
                } else {
                    // 没升级
                    heroBattle.getRoleMetaData().setExp(newExp);
                }
            }

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

        // TODO 应该加载一个背景图
        g.setColor(Color.black);
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());
        g.setColor(Color.WHITE);
        g.drawString("Victory~~~", 120, 50);
        g.drawString("get money " + money, 120, 70);
        g.drawString("get exp " + exp, 120, 90);
        // 这里只【显示】升级所得经验及金币
        List<HeroCharacter> heroBattleList = battleScreen.getHeroBattleList();
        for (int i = 0; i < heroBattleList.size(); i++) {
            RoleMetaData oldRoleMetaData = heroBattleList.get(i).getRoleMetaData();
            RoleMetaData newRoleMetaData = newHeroBatleList.get(i).getRoleMetaData();
            g.drawString("等级 " + oldRoleMetaData.getLevel() + " --> " + newRoleMetaData.getLevel(),
                    200 + 100*i, 80);
            g.drawString("体力 " + oldRoleMetaData.getHp() + "/" + oldRoleMetaData.getMaxHp()
                    + " --> " + newRoleMetaData.getHp() + "/" + newRoleMetaData.getMaxHp(),
                    200 + 100*i, 100);
            g.drawString("法力 " + oldRoleMetaData.getMp() + "/" + oldRoleMetaData.getMaxMp() + " --> " +
                    newRoleMetaData.getMp() + "/" + newRoleMetaData.getMaxMp(),
                    200 + 100*i, 120);
            g.drawString("攻击 " + oldRoleMetaData.getAttack() + " --> " + newRoleMetaData.getAttack(),
                    200 + 100*i, 140);
            g.drawString("防御 " + oldRoleMetaData.getDefend() + " --> " + newRoleMetaData.getDefend(),
                    200 + 100*i, 160);

        }

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) { }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        // 增加金币
        gameContainer.getGameData().addMoney(money);

        for (int i = 0; i < newHeroBatleList.size(); i++) {
            HeroCharacter newHeroBattle = newHeroBatleList.get(i);
            HeroCharacter heroBattle = battleScreen.getHeroBattleList().get(i);
            heroBattle.setRoleMetaData(newHeroBattle.getRoleMetaData().clone());
        }

        gameContainer.getGameFrame().changeScreen(ScreenCodeEnum.SCREEN_CODE_MAP_SCREEN);
    }
}
