package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.util.CoreUtil;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.game.util.MP3Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
        String audioPath = CoreUtil.getAudioPath("audio/game_cover.mp3");
        try {
            URL classpath = getClass().getResource("/");
            System.out.println(classpath.getPath());
            cloudMain = ImageIO.read(new File(classpath.getPath() + "/image/effect/cloudmain.png"));
            cloud1 = ImageIO.read(new File(classpath.getPath() + "/image/effect/cloud1.png"));
            cloud2 = ImageIO.read(new File(classpath.getPath() + "/image/effect/cloud2.png"));
            cloud3 = ImageIO.read(new File(classpath.getPath() + "/image/effect/cloud3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MP3Player.play(audioPath);
//        player.playAsync();
    }
    
    @Override
    public void update(long delta) {
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
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g2 = paint.getGraphics();

        Image gameCover = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameCover();
        Image gameArrow = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowRight();
        Image gameBalloon = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameBalloon();
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
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEnter(key)) {
            MP3Player.stopAll();
            switch (f) {
                case 1: {
                    LOG.debug("you choose `开始游戏`");

                    GameFrame.getInstance().getGameContainer().startChapter(1, 1, "4-4");
                    GameFrame.getInstance().changeScreen(1);
                }
                break;
                case 2: {
                    LOG.debug("you choose `继续游戏`，可是我还没有完成的啊。。。。sorry");
                }
                break;
                case 3: {
                    LOG.debug("show producerInfo ");
                    GameFrame.getInstance().changeScreen(3);
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
