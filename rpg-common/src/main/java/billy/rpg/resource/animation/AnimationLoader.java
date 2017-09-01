package billy.rpg.resource.animation;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.ImageUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * load from *.ani file
 *
 * @author liulei
 * @since 2017-07-10 09:41
 */
public class AnimationLoader {
    private static final Logger LOG = Logger.getLogger(AnimationLoader.class);
    private static final String ANI_MAGIC = ToolsConstant.MAGIC_ANI;
    private static final String CHARSET = ToolsConstant.CHARSET;

    /**
     * load from specified file
     *
     *
     * @param aniFilePath ani filepath
     */
    public static AnimationMetaData load(String aniFilePath) {
        File file = new File(aniFilePath);
        FileInputStream fis = null;
        DataInputStream dis = null;
        AnimationMetaData animationMetaData = new AnimationMetaData();

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] bAniMagic = new byte[ANI_MAGIC.getBytes(CHARSET).length];
            dis.read(bAniMagic, 0 , bAniMagic.length);
            String aniMagicUtf8 = new String(bAniMagic, CHARSET);
            LOG.debug("ani magic `"+aniMagicUtf8+"` read");
            int number = dis.readInt();
            LOG.debug("ani number is " + number);
            animationMetaData.setNumber(number);
            int imageCount = dis.readInt();
            LOG.debug("ani image count is " + imageCount);
            animationMetaData.setImageCount(imageCount);
            List<BufferedImage> images = new ArrayList<>();
            for (int i = 0; i < imageCount; i++) {
                int imageLength = dis.readInt();
                byte[] imageDataRev = new byte[imageLength];
                dis.read(imageDataRev);
                byte[] imageData = ImageUtil.reverseBytes(imageDataRev);
                ByteArrayInputStream in = new ByteArrayInputStream(imageData);
                BufferedImage bufferedImage = ImageIO.read(in);
                IOUtils.closeQuietly(in);
                images.add(bufferedImage);
            }
            animationMetaData.setImages(images);
            int frameCount = dis.readInt();
            LOG.debug("frameCount is " + frameCount);
            animationMetaData.setFrameCount(frameCount);
            FrameData[] frameDatas = new FrameData[frameCount];
            for (int c = 0; c < frameCount; c++) {
                int x = dis.readInt();
                int y = dis.readInt();
                int show = dis.readInt();
                int nshow = dis.readInt();
                int picNumber = dis.readInt();
                frameDatas[c] = new FrameData(picNumber, x, y, show, nshow);
            }
            animationMetaData.setFrameData(frameDatas);
        } catch (IOException e) {
            LOG.debug("IO exception: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }
        LOG.debug("loaded ani file `{"+aniFilePath+"}`.");
        return animationMetaData;
    }

    public static void main(String[] args) {
        AnimationMetaData load = load("z:/2.ani");
        System.out.println(load);
    }
}
