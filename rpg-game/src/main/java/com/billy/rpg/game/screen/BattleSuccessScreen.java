package com.billy.rpg.game.screen;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.character.battle.HeroBattle;
import com.billy.rpg.game.constants.GameConstant;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
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
        g.drawString("Victory~~~", 20, 50);
        g.drawString("get money " + money, 20, 70);
        g.drawString("get exp " + exp, 20, 90);
        for (int i = 0; i < heroBattleList.size(); i++) {
            HeroBattle heroBattle = heroBattleList.get(i);
            int oriMoney = heroBattle.getMonster().getExp(); // TODO
            int oriExp = heroBattle.getMonster().getExp();
            int newMoney = oriMoney + money;
            int newExp = oriExp + exp;
            g.drawString("money: " + oriMoney + "  -->  " + newMoney, 10 + 100*i, 120);
            g.drawString("exp: " + oriExp + "  -->  " + newExp, 10 + 100*i, 140);
        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        GameFrame.getInstance().popScreen();
    }
}
