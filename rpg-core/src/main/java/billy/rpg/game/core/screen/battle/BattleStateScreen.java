package billy.rpg.game.core.screen.battle;

import billy.rpg.common.exception.UnimplementationException;
import billy.rpg.common.util.TextUtil;
import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.buff.Buff;
import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.equip.weapon.WeaponEquip;
import billy.rpg.game.core.screen.BaseScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * 战斗中的状态面板
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-07 17:55:12
 */
public class BattleStateScreen extends BaseScreen {
    private final BattleOptionScreen battleOptionScreen;
    private final boolean isHero;
    private final int index;

    public BattleStateScreen(BattleOptionScreen battleOptionScreen, boolean isHero, int index) {
        this.battleOptionScreen = battleOptionScreen;
        this.isHero = isHero;
        this.index = index;
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        Fightable fightable = null;
        if (isHero) {
            HeroCharacter heroCharacter = battleOptionScreen.getBattleUIScreen().heroBattleList.get(index);
            fightable = (Fightable)heroCharacter;
        } else {
            throw new UnimplementationException("暂不支持查看敌方角色信息，后续可提供查看敌方角色信息的技能");
        }

        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(50, 20, 400, 300, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString("hp/maxHp:" + fightable.getRoleMetaData().getHp() + "/"+fightable.getRoleMetaData().getMaxHp(), 70, 70);
        g.drawString("mp/maxMp:" + fightable.getRoleMetaData().getMp() + "/"+fightable.getRoleMetaData().getMaxMp(), 70, 90);
        WeaponEquip weaponEquip = (WeaponEquip) fightable.getEquipables().getWeapon().getEquip();
        int attackValueInEquip = weaponEquip.getAttack();
        drawColorStringHorizontal(g, 70, 110, "attack:" + fightable.getRoleMetaData().getAttack(), Color.WHITE,
                 "+" + (fightable.getAttackWithBuff() - fightable.getRoleMetaData().getAttack()) + "", Color.YELLOW,
                 "+" + attackValueInEquip, Color.GREEN
        );
        // TODO 显示防御力、速度及buff

        java.util.List<Buff> buffChainList = fightable.getBuffChainList();
        for (int i = 0; i < buffChainList.size(); i++) {
            Buff buff = buffChainList.get(i);
            g.drawString(buff.toString(), 300, 110 + 20*i);
        }

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }


    private void drawColorStringHorizontal(Graphics g, int x, int y,
                                           String text1, Color color1,
                                           String text2, Color color2,
                                           String text3, Color color3){
        java.util.List<String> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        java.util.List<Color> colors = new ArrayList<>();
        colors.add(color1);
        colors.add(color2);
        colors.add(color3);


        TextUtil.drawColorStringHorizontal(g, texts, colors, x, y);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        battleOptionScreen.getBattleUIScreen().getParentScreen().pop();
    }


    @Override
    public boolean isPopup() {
        return true;
    }
}
