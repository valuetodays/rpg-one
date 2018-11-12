package test.billy.rpg.merger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 使用ps将rpg maker vx的动画图片切片后保存，发现背景有些不正常，就索性使用代码来处理，处理结果很令人满意。
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-12 09:37:27
 */
public class RpgMakerPngImageSplitter {

    public static void process(InputStream inputStream, String targetDirectory) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        int SPLIT_WIDTH  = 192;  // rmvx 的动画图片的宽高均是192
        int SPLIT_HEIGHT = 192;

        int xNumber = image.getWidth() / SPLIT_WIDTH; // 横方向的图片
        int yNumber = image.getHeight() / SPLIT_HEIGHT; // 纵方向的图片数

        for (int i = 0; i < xNumber*yNumber; i++) {
            int offsetX = (i) % xNumber;
            int offsetY = (i) / xNumber;
            BufferedImage subimage = image.getSubimage(offsetX * SPLIT_WIDTH, offsetY * SPLIT_HEIGHT, SPLIT_WIDTH, SPLIT_HEIGHT);
            ImageIO.write(subimage, "PNG", new File(targetDirectory + "/subimage/" + i + ".png"));
        }
    }

    public static void main(String[] args) throws IOException {
        InputStream resourceAsStream = RpgMakerPngImageSplitter.class.getResourceAsStream("/animation/10001/Sword4.png");
        String targetPath = "D:/tmp/";
        process(resourceAsStream, targetPath);
    }
}
