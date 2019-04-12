package game;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 将伏魔记彩色版的tile图片（32*4064） 转成rpg maker xp所用的（256*576）
 * 比较奇怪的是，伏魔的tile图片有1-9但无4，哈哈哈哈
 * @author liulei-home
 * @since 2017-12-21 21:28
 */
public class RearrangeMapImageTest {
    private static int SIZE = 32;

//    @Test
    public void testRearrangeMapImage() throws IOException {
        for (int n = 1; n < 9; n++) {
            rearrangeMapImage(n);
        }
    }
    public void rearrangeMapImage(int num) throws IOException {
        String basePath = "C:/tmp/fmj_map";
        String srcPath = basePath + "/"+num+".png"; // 32*4064 (1*127)
        String dstPath = basePath + "/tile"+num+".png"; // 256*576 (8*18)
        BufferedImage srcImage = null;

        try {
            InputStream is = new FileInputStream(srcPath);
            srcImage = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        int srcHeight = srcImage.getHeight();

        BufferedImage dstBufferedImage = new BufferedImage(
                256,
                576,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics graphics = dstBufferedImage.getGraphics();
        int n = 0;
        for (int i = 0; i < srcHeight/SIZE; i++) {
            //System.out.println(i);
            int startX = n % 8 * 32;
            int startY = n / 8 * 32;
            graphics.drawImage(srcImage,
                    startX, startY, startX+SIZE, startY+SIZE,
                    0, i*SIZE, SIZE, i*SIZE + SIZE, null);
            n++;
        }
       // graphics.dispose();

        ImageIO.write(dstBufferedImage, "PNG", new File(dstPath));
    }


}
