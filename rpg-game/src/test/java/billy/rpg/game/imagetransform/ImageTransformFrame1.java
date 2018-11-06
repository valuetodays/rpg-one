package billy.rpg.game.imagetransform;

import billy.rpg.common.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 底片效果(反色)
 *
 * @author liulei@bshf360.com
 * @since 2018-01-13 16:57
 */
public class ImageTransformFrame1 extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private BufferedImage imageOrig;
    private BufferedImage imageTarget;
    private String imagePath = "D:/code-git/rpg-one/rpg-game/src/test/resources/10.png";

    public static void main(String[] args) {
        ImageTransformFrame1 showGutPanelTest = new ImageTransformFrame1();
    }

    public ImageTransformFrame1() {
        this.setTitle("底片效果");// 设置 标题
        this.setLocation(100, 200);
        this.setSize(600, 400);// 设置宽高
        this.setLocationRelativeTo(null);// 自动适配到屏幕中间
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭模式
        this.setResizable(false);
        this.setVisible(true);// 设置可见
        try {
            imageOrig = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        transForm();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("宋体", Font.BOLD, 24));
        g.drawString(getTitle(), 10, 10);
        g.drawImage(imageOrig, 30, 30, null);
        g.drawImage(imageTarget, 230, 30, null);
    }

    private void transForm() {
        imageTarget = ImageUtil.reverseImage(imageOrig);
        repaint();
    }

}
