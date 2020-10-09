package billy.rpg.standalone;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-15 13:35
 */
public class ImageHolder {
    private BufferedImage imageSample;
    private BufferedImage imageOverlap;
    private BufferedImage imageMap;
    private BufferedImage imageSprite;
    private BufferedImage imageCgDataCursor;
    private BufferedImage imageCgDataFighter;
    private BufferedImage imageCgDataMap1;
    private BufferedImage imageCgDataPuyo;


    private void init0() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/images/sample.png");
            imageSample = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream("/images/overlap.png");
            imageOverlap = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream("/images/map.png");
            imageMap = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream("/images/sprite.png");
            imageSprite = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream("/images/cgdata/cursor.png");
            imageCgDataCursor = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream("/images/cgdata/fighter.png");
            imageCgDataFighter = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream("/images/cgdata/map1.png");
            imageCgDataMap1 = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream("/images/cgdata/puyo.png");
            imageCgDataPuyo = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        init0();
    }

    public Image getImageSample() {
        return imageSample;
    }

    public BufferedImage getImageOverlap() {
        return imageOverlap;
    }

    public BufferedImage getImageMap() {
        return imageMap;
    }

    public BufferedImage getImageSprite() {
        return imageSprite;
    }

    public BufferedImage getImageCgDataCursor() {
        return imageCgDataCursor;
    }

    public BufferedImage getImageCgDataFighter() {
        return imageCgDataFighter;
    }

    public BufferedImage getImageCgDataMap1() {
        return imageCgDataMap1;
    }

    public BufferedImage getImageCgDataPuyo() {
        return imageCgDataPuyo;
    }
}
