package billy.rpg.standalone;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-15 09:54
 */
public class DemoPanel extends JPanel {
    private ImageHolder imageHolder = new ImageHolder();
    private java.util.List<Sprite> spriteList = new ArrayList<>();

    public DemoPanel() {
        setPreferredSize(new Dimension(640, 480));
        imageHolder.init();

        spriteList.add(new Sprite(new Point(100, 100), imageHolder.getImageSprite()));
        spriteList.add(new Sprite(new Point(150, 100), imageHolder.getImageSprite()));
        spriteList.add(new Sprite(new Point(100, 150), imageHolder.getImageSprite()));
        spriteList.add(new Sprite(new Point(150, 150), imageHolder.getImageSprite()));
    }


    public void paint(Graphics g) {
        super.paint(g);
        draw0(g);
        // g.drawString("Hello, World!", 50, 0);

//        g.drawImage(imageHolder.getImageSample(), 0, 0, null);
//        g.drawImage(imageHolder.getImageOverlap(), 0, 0, null);

        g.dispose();
    }

    private void draw0(Graphics g) {
        BufferedImage imageMap1 = imageHolder.getImageCgDataMap1();
        g.drawImage(imageMap1, 0, 0, null);
        for (Sprite sprite : spriteList) {
            //sprite.draw(g);
        }
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("...");
        jFrame.setLocation(200, 100);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        DemoPanel panel = new DemoPanel();
        jFrame.getContentPane().add(panel);
        jFrame.pack();
    }

}
