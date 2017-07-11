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
    private int heroX = 8, heroY = 10;
    private java.util.List<MonsterBattle> monsterBattleList;
    private java.util.List<Image> monsterImages;
    private int arrowY = 280;
    private int monsterIndex; // 当攻击妖怪时要显示
    private boolean chooseMonster;
    private int heroChoice = 1; // 普攻、技能等  1~4

    public BattleScreen(final int[] metMonsterIds) {
        LOG.debug("met " + metMonsterIds.length + " monsters with["+ ArrayUtils.toString(metMonsterIds)+"]");

        monsterBattleList = new java.util.ArrayList<>();
        Map<Integer, Image> monsterMap = GameFrame.getInstance().getGameContainer().getMonsterImageLoader().getMonsterMetaData().getMonsterMap();

        monsterImages = new java.util.ArrayList<>();
        for (int i = 0; i < metMonsterIds.length; i++) {
            Image image = monsterMap.get(metMonsterIds[i]);
            monsterImages.add(image);
        }

        for (int i = 0; i < metMonsterIds.length; i++) {
            Image image = monsterMap.get(metMonsterIds[i]);
            MonsterBattle mb = new MonsterBattle();
            mb.setImage(image);
            mb.setLeft(getLeftInWindow(i));
            mb.setTop(100);
            mb.setWidth(image.getWidth(null));
            mb.setHeight(image.getHeight(null));
            monsterBattleList.add(mb);
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

        if (chooseMonster) {
            Image gameArrowUp = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowUp();
            MonsterBattle monsterBattleArrowTo = monsterBattleList.get(monsterIndex);
            g.drawImage(gameArrowUp,
                    monsterBattleArrowTo.getLeft() + monsterBattleArrowTo.getWidth() / 2 - gameArrowUp.getWidth(null) / 2,
                    arrowY, null);
        }

        g.setColor(Color.WHITE);
        g.drawRoundRect(0, 320, 200, 160, 4, 4);
        g.drawRoundRect(3, 323, 194, 154, 4, 4);
        g.setFont(GameConstant.FONT_BATTLE);
        g.setColor(Color.YELLOW);
        g.drawString("普攻", 50, 350);
        g.drawString("技能", 50, 375);
        g.drawString("物品", 50, 400);
        g.drawString("逃跑", 50, 425);

        // 显示用户选项
        Image gameArrowRight = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowRight();
        g.drawImage(gameArrowRight, 30, (heroChoice-1) * 25 + 333, null);

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
            if (!chooseMonster) {
                heroChoice--;
                if (heroChoice < 1) {
                    heroChoice = 4;
                }
            }
        } else if (KeyUtil.isDown(key)) {
            if (!chooseMonster) {
                heroChoice++;
                if (heroChoice > 4) {
                    heroChoice = 1;
                }
            }
        } else if (KeyUtil.isLeft(key)) {
            if (chooseMonster) {
                monsterIndex = Math.max(0, monsterIndex - 1);
            }
        } else if (KeyUtil.isRight(key)) {
            if (chooseMonster) {
                monsterIndex = Math.min(monsterBattleList.size()-1, monsterIndex+1);
            }
        } else if (KeyUtil.isEnter(key)) {
            switch (heroChoice) {
                case 1:
                    chooseMonster = true;
                    break;
                case 2:
                    chooseMonster = true;
                    LOG.debug("暂没有技能");
                    break;
                case 3:
                    LOG.debug("暂没有物品");
                    break;
                case 4:
                    LOG.debug("众妖怪：菜鸡别跑！");
                    // TODO 金币减少100*妖怪数量。
                    GameFrame.getInstance().popScreen();
                    break;
                default:
                    LOG.debug("cannot be here.");
                    break;
            }
        }
    }
}
