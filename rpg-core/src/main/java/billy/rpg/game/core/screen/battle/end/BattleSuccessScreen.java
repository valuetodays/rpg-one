package billy.rpg.game.core.screen.battle.end;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.constants.ScreenCodeEnum;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.battle.BattleScreen;
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
    private List<PlayerCharacter> newPlayerBattleList = new ArrayList<>();

    public BattleSuccessScreen(GameContainer gameContainer, BattleScreen battleScreen, int money, int exp) {
        this.battleScreen = battleScreen;
        this.money = money;
        this.exp = exp;
        List<PlayerCharacter> playerBattleList = battleScreen.getPlayerBattleList();
        for (int i = 0; i < playerBattleList.size(); i++) {
            PlayerCharacter playerBattle = playerBattleList.get(i);
            PlayerCharacter newPlayerBattle = new PlayerCharacter(gameContainer);
            newPlayerBattle.setDied(playerBattle.isDied());
            newPlayerBattle.setHeight(playerBattle.getHeight());
            newPlayerBattle.setWidth(playerBattle.getWidth());
            newPlayerBattle.setLeft(playerBattle.getLeft());
            newPlayerBattle.setTop(playerBattle.getTop());
            newPlayerBattle.setRoleMetaData(playerBattle.getRoleMetaData().clone());
            newPlayerBattleList.add(newPlayerBattle);
        }


        // 在这里处理战斗前后的数据，并处理是否升级
        // 然后draw()方法只是显示，onKeyUp()方法负责做角色属性的修改
        for (int i = 0; i < newPlayerBattleList.size(); i++) {
            PlayerCharacter playerBattle = newPlayerBattleList.get(i);
            int oriExp = playerBattle.getRoleMetaData().getExp();
            int newExp = oriExp + exp;

            playerBattle.getRoleMetaData().setExp(newExp);
            gameContainer.getGameData().levelUp(gameContainer, playerBattle.getRoleMetaData(), false);
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
        g.setColor(Color.WHITE);
        g.drawString("Victory~~~", 120, 50);
        g.drawString("get money " + money, 120, 70);
        g.drawString("get exp " + exp, 120, 90);
        // 这里只【显示】升级所得经验及金币
        List<PlayerCharacter> playerBattleList = battleScreen.getPlayerBattleList();
        for (int i = 0; i < playerBattleList.size(); i++) {
            RoleMetaData oldRoleMetaData = playerBattleList.get(i).getRoleMetaData();
            RoleMetaData newRoleMetaData = newPlayerBattleList.get(i).getRoleMetaData();
            g.drawString("等级 " + oldRoleMetaData.getLevel() + " --> " + newRoleMetaData.getLevel(),
                    200 + 100*i, 80);
            g.drawString("体力 " + oldRoleMetaData.getHp() + "/" + oldRoleMetaData.getMaxHp()
                    + " --> " + newRoleMetaData.getMaxHp() + "/" + newRoleMetaData.getMaxHp(),
                    200 + 100*i, 100);
            g.drawString("法力 " + oldRoleMetaData.getMp() + "/" + oldRoleMetaData.getMaxMp() + " --> " +
                    newRoleMetaData.getMaxMp() + "/" + newRoleMetaData.getMaxMp(),
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

        for (int i = 0; i < newPlayerBattleList.size(); i++) {
            PlayerCharacter newPlayerBattle = newPlayerBattleList.get(i);
            PlayerCharacter playerBattle = battleScreen.getPlayerBattleList().get(i);
            playerBattle.setRoleMetaData(newPlayerBattle.getRoleMetaData().clone());
        }

        gameContainer.getGameFrame().changeScreen(ScreenCodeEnum.SCREEN_CODE_MAP_SCREEN);
    }
}
