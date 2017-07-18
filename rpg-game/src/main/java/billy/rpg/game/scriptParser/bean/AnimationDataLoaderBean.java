package billy.rpg.game.scriptParser.bean;

import billy.rpg.resource.animation.FrameData;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-10 11:19
 */
public class AnimationDataLoaderBean extends DataLoaderBean {
    private int number; // 编号
    private int version; // 版本
    private int frameCount; // 帧数
    private int imageCount; // 图片数量
    private FrameData[] frameData;
    private List<BufferedImage> images;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public FrameData[] getFrameData() {
        return frameData;
    }

    public void setFrameData(FrameData[] frameData) {
        this.frameData = frameData;
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public void setImages(List<BufferedImage> images) {
        this.images = images;
    }
}
