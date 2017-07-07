package com.billy.jee.rpg.resource.animation;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author liulei
 * @since 2017-07-07 09:54
 */
public class AnimationMetaData {
    private FrameData[] frameData;
    private List<BufferedImage> images;

    public AnimationMetaData(List<BufferedImage> images, FrameData[] frameData) {
        this.images = images;
        this.frameData = frameData;
    }

    public List<BufferedImage> getImages() {
        return images;
    }
    public FrameData[] getFrameData() {
        return frameData;
    }

}
