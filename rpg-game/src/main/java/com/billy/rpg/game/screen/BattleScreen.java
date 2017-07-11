package com.billy.rpg.game.screen;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.util.KeyUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author liulei@bshf360.com
 * @since 2017-06-08 15:29
 */
public class BattleScreen extends BaseScreen {
    private int heroX, heroY;


    public BattleScreen() {

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

        // 得到缓冲区的画笔
        Graphics g = paint.getGraphics();

        final Image roleFull1 = GameFrame.getInstance().getGameContainer().getRoleItem().getRoleFull1();
        Image battleImage = GameFrame.getInstance().getGameContainer().getBattleImageItem().getBattleImage("001-Grassland01.jpg");
        g.setColor(Color.black);
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());
        g.drawImage(battleImage, 0, 0, battleImage.getWidth(null), battleImage.getHeight(null), null);  // draw battleImage
        int posX = heroX;
        int posY = heroY;
        g.drawImage(roleFull1, posX*32, posY*32, posX*32 + 32, posY*32 + 32,
                1*32, 2*32,
                1*32 + 32, 2*32 + 32, null); // 面向右，打妖怪。
        // TODO 显示妖怪

        // 将缓冲区的图形绘制到显示面板上
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            GameFrame.getInstance().popScreen();
        } else if (KeyUtil.isHome(key)) {
            BaseScreen bs = new AnimationScreen(2, heroX*32, heroY*32-198/2);
            GameFrame.getInstance().pushScreen(bs);
        } else if (KeyUtil.isUp(key)) {
            heroY--;
        } else if (KeyUtil.isDown(key)) {
            heroY++;
        } else if (KeyUtil.isLeft(key)) {
            heroX--;
        } else if (KeyUtil.isRight(key)) {
            heroX++;
        }
    }
}
