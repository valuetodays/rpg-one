package com.billy.jee.rpg.resource.animation;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author liulei
 * @since 2017-07-07 09:54
 */
public class AnimationImageLoader {
    private Map<Integer, AnimationMetaData> animationMetaDataMap;

    public Map<Integer, AnimationMetaData> load() {
        animationMetaDataMap = new HashMap<>();
        //
        final String animationPath = "/animation/0/";

        try {

            URL resource = AnimationImageLoader.class.getResource(animationPath);
            File file = new File(resource.getPath());
            File[] listFiles = file.listFiles();
            if (ArrayUtils.isEmpty(listFiles)) {
                throw new RuntimeException("在"+animationPath+"下没有找到数据");
            }

            List<BufferedImage> imageList = new ArrayList<>();
            for (File f : listFiles) {
                InputStream is = AnimationImageLoader.class.getResourceAsStream(animationPath + f.getName());
                BufferedImage img = ImageIO.read(is);
                IOUtils.closeQuietly(is);
                imageList.add(img);
            }

            FrameData[] mFrameData = new FrameData[84];
            mFrameData[0] = new FrameData(0, 40, 0, 7, 1);
            mFrameData[1] = new FrameData(1, 15, 10, 7, 1);
            mFrameData[2] = new FrameData(2, 80, 12, 7, 1);
            mFrameData[3] = new FrameData(1, 38, 0, 7, 6);
            mFrameData[4] = new FrameData(0, 38, 4, 7, 1);
            mFrameData[5] = new FrameData(1, 13, 14, 7, 1);
            mFrameData[6] = new FrameData(2, 78, 16, 7, 1);
            mFrameData[7] = new FrameData(0, 34, 6, 7, 6);
            mFrameData[8] = new FrameData(0, 40, 6, 7, 1);
            mFrameData[9] = new FrameData(1, 15, 16, 7, 1);
            mFrameData[10] = new FrameData(2, 80, 18, 7, 1);
            mFrameData[11] = new FrameData(0, 32, 10, 7, 6);
            mFrameData[12] = new FrameData(1, 43, 9, 7, 1);
            mFrameData[13] = new FrameData(2, 18, 19, 7, 1);
            mFrameData[14] = new FrameData(3, 83, 21, 7, 1);
            mFrameData[15] = new FrameData(1, 36, 12, 7, 6);
            mFrameData[16] = new FrameData(1, 38, 12, 7, 1);
            mFrameData[17] = new FrameData(2, 13, 22, 7, 1);
            mFrameData[18] = new FrameData(3, 78, 24, 7, 1);
            mFrameData[19] = new FrameData(3, 38, 16, 7, 6);
            mFrameData[20] = new FrameData(0, 34, 18, 7, 1);
            mFrameData[21] = new FrameData(3, 9, 28, 7, 1);
            mFrameData[22] = new FrameData(1, 74, 30, 7, 1);
            mFrameData[23] = new FrameData(2, 36, 21, 7, 6);
            mFrameData[24] = new FrameData(0, 32, 22, 7, 1);
            mFrameData[25] = new FrameData(3, 7, 32, 7, 1);
            mFrameData[26] = new FrameData(1, 72, 34, 7, 1);
            mFrameData[27] = new FrameData(5, 32, 28, 7, 6);
            mFrameData[28] = new FrameData(1, 36, 24, 7, 1);
            mFrameData[29] = new FrameData(0, 11, 34, 7, 1);
            mFrameData[30] = new FrameData(0, 76, 36, 7, 1);
            mFrameData[31] = new FrameData(5, 28, 32, 7, 6);
            mFrameData[32] = new FrameData(3, 38, 28, 7, 1);
            mFrameData[33] = new FrameData(0, 13, 38, 7, 1);
            mFrameData[34] = new FrameData(2, 78, 40, 7, 1);
            mFrameData[35] = new FrameData(4, 26, 36, 7, 6);
            mFrameData[36] = new FrameData(2, 36, 33, 7, 1);
            mFrameData[37] = new FrameData(1, 11, 43, 7, 1);
            mFrameData[38] = new FrameData(2, 76, 45, 7, 1);
            mFrameData[39] = new FrameData(4, 28, 40, 7, 6);
            mFrameData[40] = new FrameData(5, 32, 40, 7, 1);
            mFrameData[41] = new FrameData(7, 7, 50, 7, 1);
            mFrameData[42] = new FrameData(6, 72, 52, 7, 1);
            mFrameData[43] = new FrameData(5, 30, 44, 7, 6);
            mFrameData[44] = new FrameData(5, 28, 44, 7, 1);
            mFrameData[45] = new FrameData(6, 3, 54, 7, 1);
            mFrameData[46] = new FrameData(6, 68, 56, 7, 1);
            mFrameData[47] = new FrameData(5, 32, 47, 7, 6);
            mFrameData[48] = new FrameData(4, 26, 48, 7, 1);
            mFrameData[49] = new FrameData(6, 1, 58, 7, 1);
            mFrameData[50] = new FrameData(5, 66, 60, 7, 1);
            mFrameData[51] = new FrameData(5, 36, 50, 7, 6);
            mFrameData[52] = new FrameData(4, 28, 52, 7, 1);
            mFrameData[53] = new FrameData(6, 3, 62, 7, 1);
            mFrameData[54] = new FrameData(5, 68, 64, 7, 1);
            mFrameData[55] = new FrameData(7, 36, 52, 7, 6);
            mFrameData[56] = new FrameData(5, 30, 56, 7, 1);
            mFrameData[57] = new FrameData(4, 5, 66, 7, 1);
            mFrameData[58] = new FrameData(7, 70, 68, 7, 1);
            mFrameData[59] = new FrameData(7, 36, 56, 7, 6);
            mFrameData[60] = new FrameData(5, 32, 59, 7, 1);
            mFrameData[61] = new FrameData(4, 7, 69, 7, 1);
            mFrameData[62] = new FrameData(7, 72, 71, 7, 1);
            mFrameData[63] = new FrameData(7, 34, 60, 7,6);
            mFrameData[64] = new FrameData(5, 36, 62, 7, 1);
            mFrameData[65] = new FrameData(4, 11, 72, 7, 1);
            mFrameData[66] = new FrameData(4, 76, 74, 7, 1);
            mFrameData[67] = new FrameData(5, 36, 64, 7, 6);
            mFrameData[68] = new FrameData(7, 36, 64, 7, 1);
            mFrameData[69] = new FrameData(6, 11, 74, 7, 1);
            mFrameData[70] = new FrameData(4, 76, 76, 7, 1);
            mFrameData[71] = new FrameData(4, 36, 68, 7, 6);
            mFrameData[72] = new FrameData(7, 36, 68, 7, 1);
            mFrameData[73] = new FrameData(4, 11, 78, 7, 1);
            mFrameData[74] = new FrameData(4, 76, 80, 7, 1);
            mFrameData[75] = new FrameData(4, 38, 72, 7, 6);
            mFrameData[76] = new FrameData(7, 34, 72, 7, 1);
            mFrameData[77] = new FrameData(5, 9, 82, 7, 1);
            mFrameData[78] = new FrameData(7, 74, 84, 7, 1);
            mFrameData[79] = new FrameData(5, 38, 80, 7, 6);
            mFrameData[80] = new FrameData(5, 36, 76, 7, 1);
            mFrameData[81] = new FrameData(6, 11, 86, 7, 6);
            mFrameData[82] = new FrameData(4, 36, 80, 7, 6);
            mFrameData[83] = new FrameData(4, 38, 84, 7, 6);
            AnimationMetaData animationMetaData = new AnimationMetaData(imageList, mFrameData);

            animationMetaDataMap.put(0, animationMetaData);
        } catch (IOException e) {
            e.printStackTrace();
            animationMetaDataMap = null;
        }

        if (animationMetaDataMap == null) {
            throw new RuntimeException("加载animationMetaData出错");
        }

        return animationMetaDataMap;
    }

    public Map<Integer, AnimationMetaData> getAnimationMetaDataMap() {
        return animationMetaDataMap;
    }
    public AnimationMetaData getAnimationOf(int n) {
        return animationMetaDataMap.get(n);
    }
}
