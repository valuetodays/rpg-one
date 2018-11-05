package billy.rpg.game.imagetransform;

import billy.rpg.game.constants.GameConstant;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 雾化效果
 * 原理: 在图像中引入一定的随机值, 打乱图像中的像素值
 *
 * @author liulei@bshf360.com
 * @since 2018-01-13 18:07
 */
public class ImageTransformFrame3 extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private BufferedImage imageOrig;
    private BufferedImage imageTarget;
    private String imagePath = "D:/code-git/rpg-one/rpg-game/src/test/resources/10.png";

    public static void main(String[] args) {
        ImageTransformFrame3 showGutPanelTest = new ImageTransformFrame3();
    }

    public ImageTransformFrame3() {
        this.setTitle("雾化效果");// 设置 标题
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
        int width = imageOrig.getWidth(null);
        int height = imageOrig.getHeight(null);
        imageTarget = new BufferedImage(
                width, height,
                BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int k = GameConstant.random.nextInt(123456);
                //像素块大小
                int dx = x + k % 19;
                int dy = y + k % 19;
                if (dx >= width)
                    dx = width - 1;
                if (dy >= height)
                    dy = height - 1;
                int pixel = imageOrig.getRGB(dx, dy);
                int a = (pixel & 0xff000000) >> 24;
                int r = ((pixel & 0xff0000) >> 16);
                int g = ((pixel & 0xff00) >> 8);
                int b = (pixel & 0xff);

                imageTarget.setRGB(x, y, ColorUtil.color2int(r, g, b, a));
            }
        }
        repaint();
    }

}
