package billy.rpg.game.sprite;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class HeroSpriteFrameTest extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;

    private HeroSprite heroSprite;

    public HeroSpriteFrameTest() {
        this.setTitle("英雄精灵动画测试");
        this.setLocation(100, 200);
        this.setSize(900, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        initData();
    }

    private void initData() {
        heroSprite = new HeroSprite();
        List<HeroSprite.Key> keyList = new ArrayList<>();
        int x = 100;
        int y = 100;
        BufferedImage image;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Heal5.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
        int show = 10;
        int nShow = 5;
        for (int i = 0; i < 13; i++) {
            int ix = i % 5;
            int iy = i / 5;
            BufferedImage iimage = image.getSubimage(ix * 192, iy * 192, 192, 192);
            HeroSprite.Key key = new HeroSprite.Key(x, y, iimage, show, nShow);
            keyList.add(key);
        }
        heroSprite.setKeyList(keyList);

        // TODO 使用锁，使用heroSprite的keyList有值进才往下走
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    currentFrame++;
                    System.out.println(currentFrame);
                    if (currentFrame >= heroSprite.getKeyList().size()) {
                        currentFrame = 0;
                    }
                    repaint();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "update sprite");
        thread.start();
    }

    private Thread thread;
    private int currentFrame = 0;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("宋体", Font.BOLD, 24));
        g.drawString(getTitle(), 100, 0);
        int offsetX = 100;
        int offsetY = 30;
        HeroSprite.Key key = heroSprite.getKeyList().get(currentFrame);
        g.drawImage(key.getImage(), offsetX + key.getX(), offsetY + key.getY(), null);
    }

    public static void main(String[] args) {
        new HeroSpriteFrameTest();
    }
}