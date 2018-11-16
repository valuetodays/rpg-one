package game.sprite;

import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import billy.rpg.resource.role.RoleMetaData;
import billy.rpg.resource.sprite.HeroSprite;

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
        RoleMetaData roleMetaData = new RoleMetaData();
        heroSprite = roleMetaData.getSprite();

        // TODO 使用锁，使用heroSprite的keyList有值进才往下走
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    heroSprite.update(0);
                    repaint();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "update sprite");
        thread.start();
    }

    private Thread thread;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("宋体", Font.BOLD, 24));
        g.drawString(getTitle(), 100, 0);
        int offsetX = 100;
        int offsetY = 30;
        HeroSprite.Key key = heroSprite.getCurrentFrame();
        g.drawImage(key.getImage(), offsetX + key.getX(), offsetY + key.getY(), null);
    }

    public static void main(String[] args) {
        new HeroSpriteFrameTest();
    }
}