package billy.rpg.resource.box;

import billy.rpg.common.util.AssetsUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author liulei
 * @since 2017-05-16 19:11
 */
public class BoxImageLoader {
    private BoxMetaData boxMetaData;

    public BoxMetaData loadBoxes() {
        boxMetaData = new BoxMetaData();
        //
        final String boxPath = "/assets/Images/character/box/";

        try {
            String resourcePath = AssetsUtil.getResourcePath(boxPath + "box_closed.png");
            Image closeImg = ImageIO.read(new File(resourcePath));
            boxMetaData.setClosedImage(closeImg);

            resourcePath = AssetsUtil.getResourcePath(boxPath + "box_open.png");
            Image openImg = ImageIO.read(new File(resourcePath));
            boxMetaData.setOpenImage(openImg);
        } catch (IOException e) {
            e.printStackTrace();
            boxMetaData = null;
        }

        if (boxMetaData == null) {
            throw new RuntimeException("加载box出错");
        }

        return boxMetaData;
    }

    public BufferedImage getImageOf(int tileNum) {
        if (tileNum == getOpenImageNum()) {
            return (BufferedImage) boxMetaData.getOpenImage();
        } else if (tileNum == getClosedImageNum()) {
            return (BufferedImage) boxMetaData.getClosedImage();
        }

        throw new RuntimeException("box编号错误");
    }

    public static int getOpenImageNum() {
        return 0xee; // 238
    }
    public static int getClosedImageNum() {
        return 0xed; // 237
    }

    public String getOpenImageName() {
        return "box_open.png";
    }
    public String getClosedImageName() {
        return "box_closed.png";
    }

    public BoxMetaData getBoxMetaData() {
        return boxMetaData;
    }


}
