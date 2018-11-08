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
 * save ani into file
 *
 * @author liulei
 * @since 2017-07-09 15:13
 */
public class BinaryAnimationSaverLoader implements AnimationSaverLoader {
    private static final Logger LOG = Logger.getLogger(BinaryAnimationSaverLoader.class);
    private static final String ANI_MAGIC = ToolsConstant.MAGIC_ANI;
    private static final String CHARSET = ToolsConstant.CHARSET;


    @Override
    public AnimationMetaData load(String aniFilePath) throws IOException {
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

    /**
     * save ani to specified file
     *
     *
     * @param aniFilePath ani filepath
     * @param animationMetaData data to save
     */
    public void save(String aniFilePath, AnimationMetaData animationMetaData) {
        FrameData[] frameData = animationMetaData.getFrameData();
        List<BufferedImage> images = animationMetaData.getImages();
        int number = animationMetaData.getNumber();
        int frameCount = animationMetaData.getFrameCount();


        File file = new File(aniFilePath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(ANI_MAGIC.getBytes(CHARSET));
            LOG.debug("ANI_MAGIC `" + ANI_MAGIC + "` written as " + CHARSET);
            dos.writeInt(number);
            LOG.debug("number written with " + number);
            dos.writeInt(images.size());
            LOG.debug("image's count written with " + images.size());
            for (int i = 0; i < images.size(); i++) {
                BufferedImage bi  = images.get(i);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(bi, "PNG", out);
                byte[] bytesOfImage = out.toByteArray();
                IOUtils.closeQuietly(out);
                dos.writeInt(bytesOfImage.length);
                byte[] br = ImageUtil.reverseBytes(bytesOfImage);
                dos.write(br);
                LOG.debug("image ["+i+"] written with " + bytesOfImage.length+ " bytes.");
            }
            dos.writeInt(frameCount);
            LOG.debug("frameCount written with " + frameCount);
            for (int i = 0; i < frameCount; i++) {
                FrameData frameDataI = frameData[i];
                dos.writeInt(frameDataI.x);
                dos.writeInt(frameDataI.y);
                dos.writeInt(frameDataI.show);
                dos.writeInt(frameDataI.nShow);
                dos.writeInt(frameDataI.picNumber);
            }
        } catch (IOException e) {
            LOG.debug("IO exception:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        }
        LOG.debug("saved file `{"+aniFilePath+"}`.");
    }
}
