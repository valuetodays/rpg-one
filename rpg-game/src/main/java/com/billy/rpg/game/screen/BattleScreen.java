package com.billy.rpg.game.screen;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.character.battle.HeroBattle;
import com.billy.rpg.game.character.battle.MonsterBattle;
import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.scriptParser.item.ScriptItem;
import com.billy.rpg.game.util.KeyUtil;
import com.billy.rpg.resource.role.RoleMetaData;
import org.apache.commons.collections.CollectionUtils;
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
public class BattleScreen extends BaseScreen {
    private int heroX = 8, heroY = 10;
    private java.util.List<HeroBattle> heroBattleList;
    private java.util.List<MonsterBattle> monsterBattleList;
    private java.util.List<Image> monsterImages;
    private static int arrowY = 280;
    private int monsterIndex; // 活动妖怪，当攻击妖怪时要显示
    private int heroIndex; // 活动玩家
    private boolean chooseMonster;
    private int heroActionChoice = 1; // 普攻、技能等  1~4
    private java.util.List<BaseScreen> screenStack; //
    private java.util.List<String> msg;
    private int money;
    private int exp;


    public BattleScreen(final int[] metMonsterIds) {
        LOG.debug("met " + metMonsterIds.length + " monsters with["+ ArrayUtils.toString(metMonsterIds)+"]");

        monsterBattleList = new java.util.ArrayList<>();
        Map<Integer, RoleMetaData> monsterMap = GameFrame.getInstance().getGameContainer().getMonsterDataLoader()
                .getMonsterMetaData().getMonsterMap();

        monsterImages = new java.util.ArrayList<>();
        for (int i = 0; i < metMonsterIds.length; i++) {
            Image image = monsterMap.get(metMonsterIds[i]).getImage();
            monsterImages.add(image);
        }

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
            money += roleMetaData.getExp(); // TODO 没有金币呢。。。。
            exp += roleMetaData.getExp();
            monsterBattleList.add(mb);
        }

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

        // 得到缓冲区的画笔
        Graphics g = paint.getGraphics();

        final Image roleFull1 = GameFrame.getInstance().getGameContainer().getRoleItem().getRoleFull1();
        // TODO 战斗背景图应从*.map或*.s中加载
        Image battleImage = GameFrame.getInstance().getGameContainer().getBattleImageItem().getBattleImage("001-Grassland01.jpg");
        // TODO 先画出黑色背景，因为战斗背景图不是640*480的 (640*320)
        g.setColor(Color.black);
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
            g.drawString(monsterBattleArrowTo.getMonster().getName(),
                    monsterBattleArrowTo.getLeft() +
                    monsterBattleArrowTo.getWidth() / 2 - gameArrowUp.getWidth(null) / 2,
                    monsterBattleArrowTo.getTop() - 50);
        }
        checkWinOrLose();

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
        g.drawImage(gameArrowRight, 30, (heroActionChoice -1) * 25 + 333, null);

        g.setColor(Color.magenta);
        // 显示战斗信息
        if (CollectionUtils.isNotEmpty(msg)) {
            int size = msg.size();
            int startIndex = 0;
            int endIndex = size;
            if (size > 8) {
                startIndex = size - 8;
            }

            for (int i = startIndex; i < endIndex; i++) {
                g.drawString(msg.get(i), 300, 340 + (i - startIndex) * 18);
            }
        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            if (chooseMonster) {
                chooseMonster = false;
                monsterIndex = 0;
            }
           // GameFrame.getInstance().popScreen();
        } else if (KeyUtil.isHome(key)) {
            BaseScreen bs = new AnimationScreen(2, heroX*32, heroY*32-198/2);
            GameFrame.getInstance().pushScreen(bs);
        } else if (KeyUtil.isUp(key)) {
            if (!chooseMonster) {
                heroActionChoice--;
                if (heroActionChoice < 1) {
                    heroActionChoice = 4;
                }
            }
        } else if (KeyUtil.isDown(key)) {
            if (!chooseMonster) {
                heroActionChoice++;
                if (heroActionChoice > 4) {
                    heroActionChoice = 1;
                }
            }
        } else if (KeyUtil.isLeft(key)) {
            if (chooseMonster) {
                monsterIndex--;
                if (monsterIndex < 0) {
                    monsterIndex = monsterBattleList.size()-1;
                }
            }
        } else if (KeyUtil.isRight(key)) {
            if (chooseMonster) {
                monsterIndex++;
                if (monsterIndex > monsterBattleList.size()-1) {
                    monsterIndex = 0;
                }
            }
        } else if (KeyUtil.isEnter(key)) {
            if (chooseMonster) {
                checkWinOrLose();
                // TODO 当动画还没播放完毕，就显示对话了 ^-^|||
                MonsterBattle chosenMonsterBattle = monsterBattleList.get(monsterIndex);
                BaseScreen bs = new AnimationScreen(2, chosenMonsterBattle.getLeft()-chosenMonsterBattle.getWidth()/2,
                        chosenMonsterBattle.getTop());
                GameFrame.getInstance().pushScreen(bs);
                //CoreUtil.sleep(1000);
                DialogScreen dialogScreen = new DialogScreen("sixsixsix，使用选项" + heroActionChoice + "对第" + monsterIndex +
                        "只妖怪，打掉了1000血，");
                doFight();
                //GameFrame.getInstance().pushScreen(dialogScreen);
            } else {
                switch (heroActionChoice) {
                    case 1: // 普攻
                        chooseMonster = true;
                        //DialogScreen dialogScreen = new DialogScreen("`y`妖怪`/y`看打。");
                        //GameFrame.getInstance().pushScreen(dialogScreen);
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

    private void doFight() {
        switch (heroActionChoice) {
            case 1: // 普攻
                doAttack();
                break;
            case 2: // 技能
                LOG.debug("暂没有技能可供使用");
                break;
            case 3: // 物品
                LOG.debug("暂没有物品可供使用");
                break;
            default:
                LOG.debug("cannot be here.");
                break;
        }
    }

    private void doAttack() {
        HeroBattle heroBattle = heroBattleList.get(heroIndex);
        MonsterBattle monsterBattle = monsterBattleList.get(monsterIndex);
        int attack = heroBattle.getMonster().getAttack();
        int defend = monsterBattle.getMonster().getDefend();

        float dmgF = 1.0f * (attack*1) / (defend/100+1);
        dmgF += GameConstant.random.nextInt((int)(Math.floor(1.0f * heroBattle.getMonster().getSpeed() *
                heroBattle.getMonster().getHp() / heroBattle.getMonster().getMaxHp())));
        int dmg = (int)dmgF;
        int hp = monsterBattle.getMonster().getHp();
        hp -= dmg;
        monsterBattle.getMonster().setHp(hp);
        String msgText = "玩家"+ heroIndex + "对妖怪造成了"+dmg + "伤害";
        if (hp <= 0) {
            msgText += "，妖怪的小身板扛不住就挂了";
            monsterBattleList.remove(monsterIndex);
            monsterIndex = 0;
            checkWinOrLose();
        }
        msgText += "。";
        LOG.debug(msgText);
        appendMsg(msgText);
    }

    private void checkWinOrLose() {
        if (CollectionUtils.isEmpty(monsterBattleList)) {
            LOG.debug("victory!!! show victory ui");
            chooseMonster = false;
            GameFrame.getInstance().popScreen();
            GameFrame.getInstance().pushScreen(new BattleSuccessScreen(heroBattleList, money, exp));
        }

        // TODO 失败
    }

    private void appendMsg(String text) {
        if (msg == null) {
            msg = new ArrayList<>();
        }

        while (text.length() > 18) {
            msg.add(text.substring(0, 18));
            text = text.substring(18);
        }

        msg.add(text);
    }
}
