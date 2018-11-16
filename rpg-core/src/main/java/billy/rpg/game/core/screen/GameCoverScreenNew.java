package billy.rpg.game.core.screen;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import billy.rpg.game.core.IGameCanvas;
import billy.rpg.game.core.platform.graphics.IGameGraphics;
import billy.rpg.game.core.platform.image.IGameImage;
import billy.rpg.game.core.platform.image.IGameImageFactory;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.util.KeyUtil;
import billy.rpg.game.core.util.MP3Player;

public class GameCoverScreenNew extends BaseScreen {
    private int arrowX = 160;
    private int f = 1; // 1:new game;  2:continue;   3: producer
    private Map<Integer, Integer> map = new HashMap<>();
    private IGameImage cloudMain;
    private IGameImage cloud1;
    private IGameImage cloud2;
    private IGameImage cloud3;
    private int left1 = 100;
    private int left2 = 230;
    private int left3 = 280;

    public GameCoverScreenNew() {
        map.put(1, 320); 
        map.put(2, 350);
        map.put(3, 380);
//        String audioPath = CoreUtil.getResourcePath("audio/game_cover.mp3");

        cloudMain = IGameImageFactory.getImage("/image/effect/cloudmain.png");
        cloud1 = IGameImageFactory.getImage("/image/effect/cloud1.png");
        cloud2 = IGameImageFactory.getImage("/image/effect/cloud2.png");
        cloud3 = IGameImageFactory.getImage("/image/effect/cloud3.png");

//        MP3Player.play(audioPath);
//        player.playAsync();
    }
    
    @Override
    public void update(GameContainer gameContainer, long delta) {
        left1 += 1;
        if (left1 >= GameConstant.GAME_WIDTH) {
            left1 = 0;
        }
        left2 += 1;
        if (left2 >= GameConstant.GAME_WIDTH) {
            left2 = 200;
        }
        left3 -= 1;
        if (left3 <= -cloud3.getWidth()) {
            left3 = 500;
        }

    }

    @Override
    public void draw2(GameContainer gameContainer, IGameCanvas gameCanvas) {
        IGameImage paint = IGameImageFactory.createImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);

        IGameGraphics g2 = paint.getGraphics();

        IGameImage gameCover = gameContainer.getGameAboutItem().getGameCover();
        IGameImage gameArrow = gameContainer.getGameAboutItem().getGameArrowRightNew();

        g2.drawImage(gameCover, 0, 0);
        g2.drawRect(150, 315, 160, 90);
        g2.setFont("黑体", Font.BOLD, 24);
        g2.drawString("开始游戏", 190, 340);
        g2.drawString("继续游戏", 190, 370);
        g2.drawString("关于我们", 190, 400);
        g2.drawImage(gameArrow, arrowX, map.get(f));
        g2.drawImage(cloudMain, 0, 80);
        g2.drawImage(cloud1, left1, 30);
        g2.drawImage(cloud2, left2, 180);
        g2.drawImage(cloud3, left3, 330);
        g2.dispose();
        g2.link(paint);
        gameCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (KeyUtil.isEnter(key)) {
            MP3Player.stopAll();
            switch (f) {
                case 1: {
                    logger.debug("you choose `开始游戏`");

                    gameContainer.getGameData().addHeroId(1);
                    gameContainer.getGameData().addHeroId(3);
                    gameContainer.getGameData().setControlId(1);
                    gameContainer.getGameData().addGoods(gameContainer, 2002);
                    gameContainer.getGameData().equip(gameContainer, 1, 2002); // 装备武器
                    gameContainer.getGameData().addGoods(gameContainer, 3001);
                    gameContainer.getGameData().equip(gameContainer, 1, 3001); // 装备衣服
                    gameContainer.getGameData().addGoods(gameContainer, 4001);
                    gameContainer.getGameData().equip(gameContainer, 1, 4001); // 装备鞋子

                    gameContainer.startChapter(1, 1, "4-4");
                    gameContainer.getGameFrame().changeScreen(1);
                }
                break;
                case 2: {
                    logger.debug("you choose `继续游戏`，可是我还没有完成的啊。。。。sorry");
                }
                break;
                case 3: {
                    logger.debug("show producerInfo ");
                    gameContainer.getGameFrame().changeScreen(3);
                }
                break;
            }
            return ;
        } else if (KeyUtil.isUp(key)) {
            toUp();
        } else if (KeyUtil.isDown(key)) {
            toDown();
        }
    }

    
    private void toUp() {
        if (f > 1) {
            f--;
        }
    }
    private void toDown() {
        if (f < 3) {
            f++;
        }
    }



}
