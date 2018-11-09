package billy.rpg.common.util;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ImageObserver;
import java.util.Random;

/**
 *
 * @author liulei
 * @since 2017-04-28 17:44
 */
public class ImageUtil {


    /**
     * gray an image
     * @param srcImg src image
     */
    public static BufferedImage toGray(BufferedImage srcImg){
        return new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null).filter(srcImg, null);
    }

    /**
     * icon to bufferedImage
     * @param icon icon
     */
    public static BufferedImage getBufferedImage(ImageIcon icon){
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        ImageObserver observer = icon.getImageObserver();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gc = bufferedImage.createGraphics();
        gc.drawImage(icon.getImage(), 0, 0, observer);
        return bufferedImage;
    }

    private static final Random RANDOM = new Random();

    /**
     *  雾化效果
     * 原理: 在图像中引入一定的随机值, 打乱图像中的像素值
     *
     * https://www.cnblogs.com/gc2013/p/3678212.html
     * @param imageOrig 原图像
     */
    public static BufferedImage fogImage(BufferedImage imageOrig) {
        int width = imageOrig.getWidth(null);
        int height = imageOrig.getHeight(null);
        BufferedImage imageTarget = new BufferedImage(
                width, height,
                BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int k = RANDOM.nextInt(123456);
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

                imageTarget.setRGB(x, y, ColorUtil.INSTANCE.rgba2int(r, g, b, a));
            }
        }

        return imageTarget;
    }

    /**
     * 黑白效果
     * 原理: 彩色图像处理成黑白效果通常有3种算法；
     (1).最大值法: 使每个像素点的 R, G, B 值等于原像素点的 RGB (颜色值) 中最大的一个；
     (2).平均值法: 使用每个像素点的 R,G,B值等于原像素点的RGB值的平均值；
     (3).加权平均值法: 对每个像素点的 R, G, B值进行加权
     ---自认为第三种方法做出来的黑白效果图像最 "真实".
     *
     */
    public static BufferedImage blackWhiteImage(BufferedImage imageOrig) {
        int width = imageOrig.getWidth(null);
        int height = imageOrig.getHeight(null);
        BufferedImage imageTarget = new BufferedImage(
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
                imageTarget.setRGB(x, y, ColorUtil.INSTANCE.rgba2int(result, result, result, a));
            }
        }

        return imageTarget;
    }

    /**
     * 底片效果(反色)
     * 原理: GetPixel方法获得每一点像素的值, 然后再使用SetPixel方法将取反后的颜色值设置到对应的点.
     */
    public static BufferedImage reverseImage(BufferedImage imageOrig) {
        int width = imageOrig.getWidth(null);
        int height = imageOrig.getHeight(null);
        BufferedImage imageTarget = new BufferedImage(
                width, height,
                BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int pixel = imageOrig.getRGB(x, y);
                int a = (pixel & 0xff000000) >> 24;
                int r = 255 - ((pixel & 0xff0000) >> 16);
                int g = 255 - ((pixel & 0xff00) >> 8);
                int b = 255 - (pixel & 0xff);

                imageTarget.setRGB(x, y, ColorUtil.INSTANCE.rgba2int(r, g, b, a));
            }
        }

        return imageTarget;
    }


    /**
     * bit-reverse operation of a byte array
     * @param b the original array
     * @return reversed array
     */
    public static byte[] reverseBytes(byte[] b) {
        if (b == null) {
            return null;
        }

        int len = b.length;
        byte[] newByte = new byte[len];
        System.arraycopy(b, 0,
                newByte, 0, len);
        for (int i = 0; i < len; i++) {
            newByte[i] = (byte)~b[i];
        }

        return newByte;
    }

    public static BufferedImage scaleUpImage(BufferedImage imageOrig, float rate, Object hintValue) {
        if (rate < 1.0) {
            throw new RuntimeException("");
        }
        BufferedImage image = new BufferedImage(
                (int) (imageOrig.getWidth() * rate),
                (int) (imageOrig.getHeight() * rate), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hintValue);
        g2d.drawImage(imageOrig, 0, 0, image.getWidth(), image.getHeight(), null);
        g2d.dispose();
        return image;
    }

    public static BufferedImage scaleDownImage(BufferedImage imageOrig, int rate, Object hintValue) {
            BufferedImage ret = imageOrig;
            int targetWidth = imageOrig.getWidth() / rate;
            int targetHeight = imageOrig.getHeight() / rate;
            int w = imageOrig.getWidth();
            int h = imageOrig.getHeight();
            do {
                w = w / 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
                h = h / 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
                BufferedImage tmp = new BufferedImage(w, h,
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = tmp.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hintValue);
                g2d.drawImage(ret, 0, 0, w, h, null);
                g2d.dispose();
                ret = tmp;
            } while (w != targetWidth || h != targetHeight);
            return ret;

    }
}
