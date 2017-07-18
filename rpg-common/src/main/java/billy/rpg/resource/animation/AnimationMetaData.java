package billy.rpg.resource.animation;

import java.awt.image.BufferedImage;
import java.util.List;



/**
 * <ol>
 *  <li>9 bytes ani magic</li>
 *  <li>4 bytes version</li>
 *  <li>4 bytes number</li>
 *  <li>4 bytes imageCount</li>
 *  <li>4 bytes imageX length</li>
 *  <li>n bytes imageX data</li>
 *  <li>4 bytes frameCount</li>
 *  <li>4 bytes frameNX</li>
 *  <li>4 bytes frameNY</li>
 *  <li>4 bytes frameNShow</li>
 *  <li>4 bytes frameNNShow</li>
 *  <li>4 bytes frameNPicNumber</li>
 * </ol>
 * @author liulei
 * @since 2017-07-07 09:54
 */
public class AnimationMetaData {
    private int number; // 编号
    private int version; // 版本
    private int frameCount; // 帧数
    private int imageCount; // 图片数量
    private FrameData[] frameData;
    private List<BufferedImage> images;

    public AnimationMetaData() {}

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


    public int getNumber() {
        return number;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setFrameData(FrameData[] frameData) {
        this.frameData = frameData;
    }

    public void setImages(List<BufferedImage> images) {
        this.images = images;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
