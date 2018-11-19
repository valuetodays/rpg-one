package billy.rpg.game.core.screen;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.util.CoreUtil;
import billy.rpg.game.core.util.KeyUtil;
import billy.rpg.game.core.util.MP3Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameCoverScreen extends BaseScreen {
    private int arrowX = 160;
    private int f = 1; // 1:new game;  2:continue;   3: producer
    private Map<Integer, Integer> map = new HashMap<>();
    private Image cloudMain;
    private Image cloud1;
    private Image cloud2;
    private Image cloud3;
    private int left1 = 100;
    private int left2 = 230;
    private int left3 = 280;

    public GameCoverScreen() {
        map.put(1, 320); 
        map.put(2, 350);
        map.put(3, 380);
        String audioPath = CoreUtil.getResourcePath("audio/game_cover.mp3");
        try {
            cloudMain = ImageIO.read(new File(CoreUtil.getResourcePath("/image/effect/cloudmain.png")));
            cloud1 = ImageIO.read(new File(CoreUtil.getResourcePath("/image/effect/cloud1.png")));
            cloud2 = ImageIO.read(new File(CoreUtil.getResourcePath("/image/effect/cloud2.png")));
            cloud3 = ImageIO.read(new File(CoreUtil.getResourcePath("/image/effect/cloud3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MP3Player.play(audioPath);
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
        if (left3 <= -cloud3.getWidth(null)) {
            left3 = 500;
        }

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g2 = paint.getGraphics();

        Image gameCover = gameContainer.getGameAboutItem().getGameCover();
        Image gameArrow = gameContainer.getGameAboutItem().getGameArrowRight();
        Image gameBalloon = gameContainer.getGameAboutItem().getGameBalloon();
        g2.drawImage(gameCover, 0, 0, gameCover.getWidth(null), gameCover.getHeight(null), null);
        g2.drawRect(150, 315, 160, 90);
        g2.setFont(new Font("黑体", Font.BOLD, 24));
        g2.drawString("开始游戏", 190, 340);
        g2.drawString("继续游戏", 190, 370);
        g2.drawString("关于我们", 190, 400);
        g2.drawImage(gameArrow, arrowX, map.get(f), gameArrow.getWidth(null), gameArrow.getHeight(null), null);
//        g2.drawImage(gameArrow, arrowX, arrowY+28, gameArrow.getWidth(null), gameArrow.getHeight(null), null);
        g2.drawImage(gameBalloon, 220, 130, 220+32, 130+32,
        		1*32, 0, 1*32+32, 0*32+32,  
        		null);
        g2.drawImage(cloudMain, 0, 80, null);
        g2.drawImage(cloud1, left1, 30, null);
        g2.drawImage(cloud2, left2, 180, null);
        g2.drawImage(cloud3, left3, 330, null);
        g2.dispose();
        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
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
