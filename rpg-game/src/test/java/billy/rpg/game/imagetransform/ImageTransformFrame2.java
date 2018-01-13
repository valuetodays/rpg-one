package billy.rpg.game.imagetransform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 黑白效果
 * 原理: 彩色图像处理成黑白效果通常有3种算法；

 (1).最大值法: 使每个像素点的 R, G, B 值等于原像素点的 RGB (颜色值) 中最大的一个；

 (2).平均值法: 使用每个像素点的 R,G,B值等于原像素点的RGB值的平均值；

 (3).加权平均值法: 对每个像素点的 R, G, B值进行加权

 ---自认为第三种方法做出来的黑白效果图像最 "真实".
 *
 * @author liulei@bshf360.com
 * @since 2018-01-13 18:07
 */
public class ImageTransformFrame2 extends JFrame {
    private BufferedImage imageOrig;
    private BufferedImage imageTarget;
    private String imagePath = "D:/code-git/rpg-one/rpg-game/src/test/resources/10.png";

    public static void main(String[] args) {
        ImageTransformFrame2 showGutPanelTest = new ImageTransformFrame2();
    }

    public ImageTransformFrame2() {
        this.setTitle("黑白效果");// 设置 标题
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
                int pixel = imageOrig.getRGB(x, y);
                int a = (pixel & 0xff000000) >> 24;
                int r = ((pixel & 0xff0000) >> 16);
                int g = ((pixel & 0xff00) >> 8);
                int b = (pixel & 0xff);
                int result = 0;
                //实例程序以加权平均值法产生黑白图像
                int iType =2;
                switch (iType)
                {
                    case 0://平均值法
                        result = ((r + g + b) / 3);
                        break;
                    case 1://最大值法
                        result = r > g ? r : g;
                        result = result > b ? result : b;
                        break;
                    case 2://加权平均值法
                        result = ((int)(0.7 * r) + (int)(0.2 * g) + (int)(0.1 * b));
                        break;
                }
                imageTarget.setRGB(x, y, ColorUtil.color2int(result, result, result, a));
            }
        }
        repaint();
    }

}
