package com.billy.rpg.game.screen;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.character.battle.MonsterBattle;
import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.util.KeyUtil;
import org.apache.commons.lang.ArrayUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 *
 * @author liulei@bshf360.com
 * @since 2017-06-08 15:29
 */
public class BattleScreen extends BaseScreen {
    private int heroX, heroY;
    private java.util.List<MonsterBattle> monsterBattleList;
    private int arrowY = 300;
    private int arrowX = 0;

    public BattleScreen(final int[] metMonsterIds) {
        monsterBattleList = new java.util.ArrayList<>();
        Map<Integer, Image> monsterMap = GameFrame.getInstance().getGameContainer().getMonsterImageLoader().getMonsterMetaData().getMonsterMap();
        int leftOffset = 0;
        for (int i = 0; i < metMonsterIds.length; i++) {
            Image image = monsterMap.get(metMonsterIds[i]);
            leftOffset += i * 50;
            MonsterBattle mb = new MonsterBattle();
            mb.setImage(image);
            mb.setLeft(leftOffset);
            mb.setTop(150);
            mb.setWidth(image.getWidth(null));
            mb.setHeight(image.getHeight(null));
            monsterBattleList.add(mb);
            leftOffset += image.getWidth(null);
        }

        LOG.debug("met " + metMonsterIds.length + " monsters with["+ ArrayUtils.toString(metMonsterIds)+"]");
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
        g.setColor(Color.black); // TODO 先画出黑色背景，因为战斗背景图不是640*480的
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());
        g.drawImage(battleImage, 0, 0, battleImage.getWidth(null), battleImage.getHeight(null), null);  // draw battleImage
        int posX = heroX;
        int posY = heroY;
        g.drawImage(roleFull1, posX*32, posY*32, posX*32 + 32, posY*32 + 32,
                1*32, 2*32,
                1*32 + 32, 2*32 + 32, null); // 面向右，打妖怪。

        for (int i = 0; i < monsterBattleList.size(); i++) {
            MonsterBattle monsterBattle = monsterBattleList.get(i);
            Image image = monsterBattle.getImage();
            int left = monsterBattle.getLeft();
            int top = monsterBattle.getTop();
            g.drawImage(image, left, top, null);
        }

        Image gameArrow = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrow();
        MonsterBattle monsterBattleArrowTo = monsterBattleList.get(arrowX);
        g.drawImage(gameArrow, monsterBattleArrowTo.getLeft(), arrowY, null);

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
            arrowX = Math.max(0, arrowX - 1);
        } else if (KeyUtil.isRight(key)) {
            heroX++;
            arrowX = Math.min(monsterBattleList.size()-1, arrowX+1);
        }
    }
}
