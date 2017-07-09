package com.billy.rpg.resource.animation;

import com.billy.rpg.common.constant.AnimationEditorConstant;
import com.billy.rpg.common.constant.MapFileConstant;
import com.billy.rpg.common.util.ImageUtil;
import com.billy.rpg.resource.map.MapMetaData;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * save ani into file
 *
 * @author liulei
 * @since 2017-07-09 15:13
 */
public class AnimationSaver {
    private static final Logger LOG = Logger.getLogger(AnimationSaver.class);


    private static final String ANI_MAGIC = AnimationEditorConstant.ANI_MAGIC;

    /**
     * save ani to specified file
     *
     *
     * @param aniFilePath ani filepath
     * @param animationMetaData data to save
     */
    public static void save(String aniFilePath, AnimationMetaData animationMetaData) {
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
            dos.write(ANI_MAGIC.getBytes("utf-8"));
            LOG.debug("ANI_MAGIC `" + ANI_MAGIC + "` written as utf-8");
            dos.writeInt(AnimationEditorConstant.VERSION);
            LOG.debug("version written.");
            dos.writeInt(number);
            LOG.debug("number written.");
            dos.writeInt(images.size());
            LOG.debug("image's count written.");
            for (int i = 0; i < images.size(); i++) {
                BufferedImage bi  = images.get(i);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(bi, "PNG", out);
                byte[] bytesOfImage = out.toByteArray();
                IOUtils.closeQuietly(out);
                dos.writeInt(bytesOfImage.length);
                byte[] br = ImageUtil.reverseBytes(bytesOfImage);
                dos.write(br);
                LOG.debug("image ["+i+"] written with " + bytesOfImage + " bytes.");
            }
            dos.writeInt(frameCount);
            LOG.debug("frameCount written");
            for (int i = 0; i < frameCount; i++) {
                FrameData frameDataI = frameData[i];
                dos.writeInt(frameDataI.x);
                dos.writeInt(frameDataI.y);
                dos.writeInt(frameDataI.show);
                dos.writeInt(frameDataI.nShow);
                dos.writeInt(frameDataI.index);
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
